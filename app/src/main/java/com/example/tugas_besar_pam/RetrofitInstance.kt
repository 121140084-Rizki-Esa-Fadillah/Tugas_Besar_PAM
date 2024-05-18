package com.example.tugas_besar_pam

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // Perbaiki baseUrl di RetrofitInstance
    private const val BASE_URL = "https://api.foursquare.com/v3/"

    val api: FourSquareApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FourSquareApiService::class.java)
    }
}
