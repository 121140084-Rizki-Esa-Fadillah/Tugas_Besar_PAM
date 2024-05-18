package com.example.tugas_besar_pam

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugas_besar_pam.databinding.FragmentSearchBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: androidx.appcompat.widget.SearchView
    private var currentLocation: Location? = null
    private var currentQuery: String? = null
    private var isRelevanSelected = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = binding.recyclerView
        searchView = binding.searchView

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        currentLocation = arguments?.getParcelable("location")

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    currentQuery = it
                    searchRestaurants(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.btnNearest.setOnClickListener {
            onNearestClicked()
        }

        binding.btnRelevan.setOnClickListener {
            onRelevanClicked()
        }

        return view
    }

    private fun searchRestaurants(query: String? = currentQuery, filter: String? = null) {
        if (currentLocation == null) {
            Snackbar.make(binding.root, "Location not available", Snackbar.LENGTH_SHORT).show()
            return
        }

        val latitude = currentLocation!!.latitude
        val longitude = currentLocation!!.longitude

        val baseUrl = "https://api.foursquare.com/v3/places/search"
        val sortedUrl = if (filter != null) "&sort=$filter" else ""

        lifecycleScope.launch {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("$baseUrl?query=$query&ll=$latitude,$longitude&categories=13065$sortedUrl")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "fsq3ID+5NCwgxtrnfqyBktIcdxYI0AEyck+BNSA5EcQZb6w=")
                .build()

            try {
                val response = withContext(Dispatchers.IO) { client.newCall(request).execute() }
                val responseData = response.body?.string()
                if (response.isSuccessful && responseData != null) {
                    val restaurants = parseRestaurants(responseData)
                    updateRecyclerView(restaurants)
                } else {
                    Log.e("SearchFragment", "Error: ${response.message}")
                }
            } catch (e: IOException) {
                Log.e("SearchFragment", "Error: ${e.message}")
            }
        }
    }

    private suspend fun getPhotoUrl(fsqId: String): String {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.foursquare.com/v3/places/$fsqId/photos")
            .addHeader("Accept", "application/json")
            .addHeader("Authorization", "fsq3ID+5NCwgxtrnfqyBktIcdxYI0AEyck+BNSA5EcQZb6w=")
            .build()

        return withContext(Dispatchers.IO) {
            val response = client.newCall(request).execute()
            val responseData = response.body?.string()
            if (response.isSuccessful && responseData != null) {
                val photos = JSONArray(responseData)
                if (photos.length() > 0) {
                    val photo = photos.getJSONObject(0)
                    val prefix = photo.getString("prefix")
                    val suffix = photo.getString("suffix")
                    return@withContext "$prefix${suffix}"
                }
            }
            return@withContext ""
        }
    }

    private suspend fun parseRestaurants(responseData: String): List<Restaurant> {
        val restaurants = mutableListOf<Restaurant>()
        val jsonObject = JSONObject(responseData)
        val results = jsonObject.getJSONArray("results")
        for (i in 0 until results.length()) {
            val result = results.getJSONObject(i)
            val fsqId = result.getString("fsq_id")
            val name = result.getString("name")
            val distance = result.optInt("distance", 0)
            val category = result.getJSONArray("categories").getJSONObject(0).getString("name")

            // Mendapatkan URL gambar
            val imageUrl = getPhotoUrl(fsqId)

            val restaurant = Restaurant(fsqId, name, category, distance, imageUrl)
            restaurants.add(restaurant)
        }
        return restaurants
    }

    private fun updateRecyclerView(restaurants: List<Restaurant>) {
        val apiService = RetrofitInstance.api
        val adapter = RestaurantAdapter(restaurants, apiService)
        recyclerView.adapter = adapter
    }

    private fun onNearestClicked() {
        if (isRelevanSelected) {
            isRelevanSelected = false
            updateButtonBackground()
        }
        searchRestaurants(filter = "DISTANCE")
    }

    private fun onRelevanClicked() {
        if (!isRelevanSelected) {
            isRelevanSelected = true
            updateButtonBackground()
        }
        searchRestaurants(filter = "RELEVANCE")
    }

    private fun updateButtonBackground() {
        if (isRelevanSelected) {
            binding.btnRelevan.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.primary)
            binding.btnNearest.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.alt_primary)
        } else {
            binding.btnRelevan.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.alt_primary)
            binding.btnNearest.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.primary)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
