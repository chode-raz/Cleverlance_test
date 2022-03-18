package com.test.cleverlancetest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar
import com.test.cleverlancetest.R
import com.test.cleverlancetest.databinding.FragmentSignInBinding
import com.test.cleverlancetest.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private val viewModel by viewModels<MainViewModel>()

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.image.observe(viewLifecycleOwner) {
            Glide.with(this).load(it).into(binding.imageView)
        }

        viewModel.loading.observe(viewLifecycleOwner) { loadingFlag ->
            if (loadingFlag) {
                binding.downloadButton.visibility = View.INVISIBLE
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.downloadButton.visibility = View.VISIBLE
                binding.progressBar.visibility = View.INVISIBLE
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            showErrorMessage(message)
        }

        binding.downloadButton.setOnClickListener {
            val username = binding.nameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            if (isInputNotEmpty(username, getString(R.string.username_hint)) &&
                isInputNotEmpty(password, getString(R.string.password_hint))
            ) {
                viewModel.downloadImage(password, username)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showErrorMessage(message: String?) {
        if (message.isNullOrBlank()) {
            Snackbar.make(
                binding.root, R.string.general_error_message, LENGTH_SHORT
            ).show()
        } else {
            Snackbar.make(binding.root, message, LENGTH_SHORT).show()
        }
    }

    private fun isInputNotEmpty(input: String, fieldName: String): Boolean {
        if (input.isEmpty()) {
            showErrorMessage(getString(R.string.empty_input_error_message, fieldName))
            return false
        }
        return true
    }
}