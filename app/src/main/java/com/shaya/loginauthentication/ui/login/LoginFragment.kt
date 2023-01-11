package com.shaya.loginauthentication.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.shaya.loginauthentication.R
import com.shaya.loginauthentication.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogin.setOnClickListener {
            val username = binding.editTextUsername.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()

            if (username.isEmpty()) {
                binding.editTextUsername.error = getString(R.string.error_field_required)
                return@setOnClickListener
            }

            if (username.length != 10) {
                binding.editTextUsername.error = getString(R.string.valid_username)
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.editTextPassword.error = getString(R.string.error_field_required)
                return@setOnClickListener
            }

            if (password.length != 7) {
                binding.editTextPassword.error = getString(R.string.valid_password)
                return@setOnClickListener
            }

            if (password.contains("[A-Z]".toRegex()) && password.contains("[a-z]".toRegex()) && password.contains(
                    "[0-9]".toRegex()
                ) && password.contains("[!\"#$%&'()*+,-./:;\\\\<=>?@\\[\\]^_`{|}~]".toRegex())
            ) {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRecyclerViewFragment())

            } else {
                binding.editTextPassword.error = getString(R.string.valid_passwordd)
            }
        }
    }
}