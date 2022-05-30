package com.kixfobby.softwarelab.network

import com.kixfobby.softwarelab.BuildConfig
import retrofit2.http.POST
import com.kixfobby.softwarelab.model.registration.RegistrationRequest
import com.kixfobby.softwarelab.model.registration.RegistrationResponse
import com.kixfobby.softwarelab.model.login.LoginRequest
import com.kixfobby.softwarelab.model.login.LoginResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import java.util.concurrent.TimeUnit

interface ApiInterface {

    @Headers("Content-Type: application/json")
    @POST("/api/authaccount/registration")
    fun register(@Body request: RegistrationRequest?): Call<RegistrationResponse?>?

    @Headers("Content-Type: application/json")
    @POST("/api/authaccount/login")
    fun login(@Body request: LoginRequest?): Call<LoginResponse?>?

    companion object {
        private const val BaseURL = "http://restapi.adequateshop.com"
        fun getClient(): ApiInterface {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val builder = OkHttpClient.Builder()
            builder.connectTimeout(30, TimeUnit.SECONDS)
            builder.writeTimeout(30, TimeUnit.SECONDS)
            builder.readTimeout(30, TimeUnit.SECONDS)
            if (BuildConfig.DEBUG) {
                builder.addInterceptor(logging)
            }
            builder.cache(null)
            val okHttpClient = builder.build()
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}