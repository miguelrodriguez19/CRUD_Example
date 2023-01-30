package com.utad.crudexample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.utad.crudexample.databinding.FragmentEditUsersBinding

class EditUsersFragment : Fragment() {

    val args: EditUsersFragmentArgs by navArgs()
    private var _binding: FragmentEditUsersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var etBirthdate: EditText
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etBirthdate = binding.etUserBirthDate
        val etName = binding.etUserName
        val btnEdit = binding.fabSaveUser
        val btnDelete = binding.fabCancelUser
        val user = args.user

        (activity as? AppCompatActivity)?.supportActionBar?.title = user.name ?: "<!>unnamed"

        etName.setText(user.name ?: "<!>unnamed")
        etBirthdate.setText(user.birthdate)

        etBirthdate.setOnClickListener {
            showDatePickerDialog()
        }

        btnEdit.setOnClickListener {
            // Save
            findNavController().popBackStack(R.id.allUsersFragment, true)
        }

        btnDelete.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(requireActivity().supportFragmentManager, "datePicker")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        etBirthdate.setText("$day/${month + 1}/$year")
    }
}