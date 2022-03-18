package com.test.cleverlancetest.repository

import com.test.cleverlancetest.network.CleverlanceRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CleverlanceRepository @Inject constructor(
    private val remoteDataSource: CleverlanceRemoteDataSource
) {

    suspend fun getImageEncodedString(userPasswordHash: String, username: String) =
        remoteDataSource.fetchEncodedImageString(userPasswordHash, username)

}