package com.example.tugas_besar_pam

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

    @GET("places/{fsq_id}/photos")
    suspend fun getPlacePhotos(
        @Path("fsq_id") fsqId: String
    ): Response<List<PhotoResponse>>
}

data class SearchResponse(val results: List<Venue>)
data class Venue(val name: String, val geocodes: Geocodes, val categories: List<Category>)
data class Geocodes(val main: Main)
data class Main(val latitude: Double, val longitude: Double)
data class Category(val name: String)
data class PhotoResponse(val prefix: String, val suffix: String)