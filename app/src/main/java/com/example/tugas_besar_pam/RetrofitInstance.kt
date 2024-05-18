package com.example.tugas_besar_pam

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: FourSquareApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.foursquare.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FourSquareApiService::class.java)
    }
}
