package com.shaya.loginauthentication.ui.database

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.shaya.loginauthentication.R
import com.shaya.loginauthentication.data.Author
import com.shaya.loginauthentication.databinding.FragmentAddAuthorDialogBinding
import com.shaya.loginauthentication.ui.AuthorsViewModel

class AddAuthorDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentAddAuthorDialogBinding
    private lateinit var viewModel: AuthorsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this)[AuthorsViewModel::class.java]
        binding = FragmentAddAuthorDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.result.observe(viewLifecycleOwner, Observer {
            val message = if (it == null) {
                getString(R.string.author_added)
            } else {
                getString(R.string.error, it.message)
            }
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        })

        binding.buttonAdd.setOnClickListener {
            val name = binding.editAuthorName.text.toString().trim()
            val age = binding.editAuthorAge.text.toString().trim()
            val city = binding.editAuthorCity.text.toString().trim()

            if (name.isEmpty()) {
                binding.editAuthorName.error = getString(R.string.error_field_required)
                return@setOnClickListener
            }
            if (age.isEmpty()) {
                binding.editAuthorAge.error = getString(R.string.error_field_required)
                return@setOnClickListener
            }

            if (city.isEmpty()) {
                binding.editAuthorCity.error = getString(R.string.error_field_required)
                return@setOnClickListener
            }

            val author = Author()
            author.name = name
            author.age = age.toInt()
            author.city = city
            viewModel.addAuthor(author)
            dismiss()
        }
    }
}