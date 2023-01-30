package com.utad.crudexample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.utad.crudexample.DetailUserFragmentArgs
import com.utad.crudexample.databinding.FragmentDetailUserBinding

class DetailUserFragment : Fragment() {
    val args: DetailUserFragmentArgs by navArgs()
    private var _binding: FragmentDetailUserBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvBirthdate = binding.tvUserBirthDate
        val tvName = binding.tvUserName
        val btnEdit = binding.fabEditUser
        val btnDelete = binding.fabDeleteUser
        val user = args.user

        (activity as? AppCompatActivity)?.supportActionBar?.title = user.name ?: "<!>unnamed"
        tvName.text = user.name ?: "<!>unnamed"
        tvBirthdate.text = user.birthdate

        btnEdit.setOnClickListener{
            val action = DetailUserFragmentDirections.actionDetailUserFragmentToEditUsersFragment(user)
            findNavController().navigate(action)
        }

        btnDelete.setOnClickListener{
            Snackbar.make(binding.root, "User deleted succesfully", Snackbar.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }

    }
}