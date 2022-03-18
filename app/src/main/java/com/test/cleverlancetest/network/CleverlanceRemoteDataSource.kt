package com.test.cleverlancetest.network

import com.test.cleverlancetest.data.SignInResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CleverlanceRemoteDataSource @Inject constructor(
    private val cleverlanceService: CleverlanceApi
) {

    suspend fun fetchEncodedImageString(
        userPasswordHash: String,
        username: String
    ): Resource<SignInResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = cleverlanceService.fetchEncodedImageString(
                    userPasswordHash,
                    username
                )
                Resource.success(
                    response
                )
            } catch (exception: Exception) {
                Resource.error(null, exception.message.toString())
            }
        }
    }

}