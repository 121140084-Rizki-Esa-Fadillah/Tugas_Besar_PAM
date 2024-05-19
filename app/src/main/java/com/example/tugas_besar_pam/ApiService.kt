package com.example.tugas_besar_pam

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface FourSquareApiService {
    @GET("places/search")
    suspend fun searchPlaces(
        @Query("ll") latLong: String,
        @Query("query") query: String,
        @Header("Authorization") authorization: String
    ): Response<SearchResponse>

    @GET("v3/places/{id}/photos")
    fun getPhotos(
        @Path("id") fsqId: String,
        @Header("Authorization") authorization: String
    ): Call<List<Photo>>

    @GET("v3/places/{fsq_id}")
    fun getPlaceDetails(
        @Path("fsq_id") fsqId: String,
        @Header("Authorization") authorization: String
    ): Call<PlaceDetails>
}

data class SearchResponse(val results: List<Venue>)
data class Venue(val fsq_id: String, val name: String, val geocodes: Geocodes, val categories: List<Category>)
data class Geocodes(val main: Main)
data class Main(val latitude: Double, val longitude: Double)
data class Category(val name: String)
