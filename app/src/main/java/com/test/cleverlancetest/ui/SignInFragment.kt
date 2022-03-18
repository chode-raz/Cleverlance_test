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
            showToast(message)
        }

        binding.downloadButton.setOnClickListener {
            val username = binding.nameEditText.text.toString()
            val password = binding.passwordEditText.text.toString().lowercase()
            viewModel.downloadImage(password, username)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToast(message: String?) {
        if (message.isNullOrBlank()) {
            Snackbar.make(
                binding.root, R.string.general_download_error_message, LENGTH_SHORT
            ).show()
        } else {
            Snackbar.make(binding.root, message, LENGTH_SHORT).show()
        }
    }

}