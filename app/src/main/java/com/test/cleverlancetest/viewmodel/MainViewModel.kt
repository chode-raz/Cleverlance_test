package com.test.cleverlancetest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.cleverlancetest.network.Status
import com.test.cleverlancetest.repository.CleverlanceRepository
import com.test.cleverlancetest.utils.EncodingUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: CleverlanceRepository
) : ViewModel() {

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    private val _image = MutableLiveData<ByteArray>()
    val image: LiveData<ByteArray>
        get() = _image

    fun downloadImage(password: String, username: String) {
        viewModelScope.launch {
            _loading.value = true

            val inputPassword = password.lowercase()
            val inputUsername = username.lowercase()

            val hashedPassword = EncodingUtils.hashSHA1(inputPassword)

            val resource = repository.getImageEncodedString(hashedPassword, inputUsername)

            when (resource.status) {
                Status.SUCCESS -> {
                    _loading.value = false
                    resource.data?.let { response ->
                        _image.value = EncodingUtils.decodeImageString(response.image)
                    }
                }
                Status.ERROR -> {
                    _loading.value = false
                    _errorMessage.value = resource.message
                }
            }
        }
    }

}