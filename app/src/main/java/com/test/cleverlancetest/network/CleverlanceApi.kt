package com.test.cleverlancetest.network

import com.test.cleverlancetest.data.SignInResponse
import retrofit2.http.*

interface CleverlanceApi {

    @Headers(
        "Host: mobility.cleverlance.com"
    )
    @FormUrlEncoded
    @POST("/download/bootcamp/image.php")
    suspend fun fetchEncodedImageString(@Header ("Authorization") passwordHash: String, @Field("username") userBody: String) : SignInResponse

}
