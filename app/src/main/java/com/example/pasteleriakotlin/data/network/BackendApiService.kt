package com.example.pasteleriakotlin.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST



data class LoginRequest(
    val nombre: String,
    val contrasena: String
)

data class UsuarioDto(
    val id: Long?,
    val nombre: String,
    val rol: String?
)

data class LoginResponse(
    val token: String,
    val usuario: UsuarioDto
)



interface BackendApiService {

    @POST("api/v1/usuarios/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse
}



object BackendApiClient {

    private val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()

    private const val BASE_URL = "http://10.0.2.2:8080/"

    val api: BackendApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BackendApiService::class.java)
    }
}
