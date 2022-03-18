package com.test.cleverlancetest

import com.test.cleverlancetest.data.SignInResponse
import com.test.cleverlancetest.network.CleverlanceApi
import com.test.cleverlancetest.network.CleverlanceRemoteDataSource
import com.test.cleverlancetest.network.Status
import com.test.cleverlancetest.repository.CleverlanceRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
class NetworkDataTest {
    private val mockServer = MockWebServer()
    private val url = "http://127.0.0.1:8000"

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(url)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CleverlanceApi::class.java)

    private val cleverlanceRemoteDataSource = CleverlanceRemoteDataSource(api)

    private val repository = CleverlanceRepository(cleverlanceRemoteDataSource)

    @Before
    fun setup() {
        mockServer.start(8000)
    }

    @After
    fun destroy() {
        mockServer.shutdown()
    }

    @Test
    fun `fetch data with correct login`() {
        mockServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("{\"image\":\"test\"}")
        )

        runBlocking {
            val response = repository.getImageEncodedString("rightPassword", "rightUsername")
            val actual = response.data
            val expected =
                SignInResponse(
                    image = "test"
                )

            assertEquals(Status.SUCCESS, response.status)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `fail fetch data with wrong login`() {
        mockServer.enqueue(
            MockResponse()
                .setResponseCode(401)
                .setBody("{\"error\":\"errorMessage\"}")
        )

        runBlocking {
            val response = repository.getImageEncodedString("wrongPassword", "wrongUsername")

            assertEquals(response.status, Status.ERROR)
        }
    }


}