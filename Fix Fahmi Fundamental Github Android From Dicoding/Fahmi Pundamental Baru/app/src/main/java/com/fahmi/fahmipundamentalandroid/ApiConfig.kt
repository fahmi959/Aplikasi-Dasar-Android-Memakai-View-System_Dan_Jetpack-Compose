package com.fahmi.fahmipundamentalandroid

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiConfig {
    companion object{
        fun getApiService(): ApiService {
            // JIKA PAKAI loggingInterceptor ini MAKA HEADER API SERVICE SATU PERSATU
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            // JIKA PAKAI authInterceptor MAKA HEADER pada API SERVICE KESELURUHAN
            val authInterceptor = Interceptor { chain ->
                val req = chain.request()
                val requestHeaders = req.newBuilder()
                    .addHeader("Authorization", "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")
                    .build()
                chain.proceed(requestHeaders)
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}