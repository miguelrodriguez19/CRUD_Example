package com.utad.crudexample

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.utad.crudexample.api.ApiRest
import com.utad.crudexample.databinding.FragmentAllUsersBinding
import com.utad.crudexample.users.UsersAdapter
import com.utad.crudexample.users.UsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AllUsersFragment : Fragment() {

    val TAG = "AllUsersFragment"
    var usersList: ArrayList<UsersResponse.User> = ArrayList()
    private var usersAdapter: UsersAdapter? = null
    private var swiperefresh: SwipeRefreshLayout? = null
    private var _binding: FragmentAllUsersBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.Users)
        val btnCreate = binding.fabCreateUser

        ApiRest.initService()
        getAllUsers()
        val rvAgents = view.findViewById<RecyclerView>(R.id.rvUsers)
        swiperefresh = view.findViewById<SwipeRefreshLayout>(R.id.swiperefresh)

        // Muestra las imagenes sin cortarse (estilo tiktok)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rvAgents)

        //Mostrar como cuadricula
        val mLayoutManager = GridLayoutManager(context, 2)
        rvAgents.layoutManager = mLayoutManager

        //Creamos el adapter y lo vinculamos con el recycler
        usersAdapter = UsersAdapter(usersList){
            val action = AllUsersFragmentDirections.actionAllUsersFragmentToDetailUserFragment(it)
            findNavController().navigate(action)
        }
        rvAgents.adapter = usersAdapter

        swiperefresh?.setOnRefreshListener {
            getAllUsers()
        }

        btnCreate.setOnClickListener{
            val user = UsersResponse.User(null, "Name", "Birthdate")
            val action = AllUsersFragmentDirections.actionAllUsersFragmentToEditUsersFragment(user)
            findNavController().navigate(action)
        }
    }

    private fun getAllUsers() {
        val call = ApiRest.service.getAllUsers()
        call.enqueue(object : Callback<UsersResponse> {
            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.i(TAG, body.toString())
                    usersList.clear()
                    usersList.addAll(body)
                    // Imprimir aqui el listado con logs
                    //usersAdapter?.notifyDataSetChanged()
                    usersAdapter?.notifyItemRangeChanged(0, usersList.size)
                } else {
                    response.errorBody()?.string()?.let { Log.e(TAG, it) }
                }
                swiperefresh?.isRefreshing = false
            }
            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                t.message?.let { Log.e(TAG, it) }
                swiperefresh?.isRefreshing = false
            }
        })
    }
}