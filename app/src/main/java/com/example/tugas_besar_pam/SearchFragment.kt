package com.example.tugas_besar_pam

import android.content.Intent
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
    private lateinit var apiService: FourSquareApiService

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

        // Inisialisasi FourSquareApiService
        apiService = RetrofitInstance.api

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
                .addHeader("Authorization", "fsq3ID+5NCwgxtrnfqyBktIcdxYI0AEyck+BNSA5EcQZb6w=") // Ganti dengan token authorization yang sesuai
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
            val imageUrl = ""

            val restaurant = Restaurant(fsqId, name, category, distance, imageUrl)
            restaurants.add(restaurant)
        }
        return restaurants
    }

    private fun updateRecyclerView(restaurants: List<Restaurant>) {
        val adapter = RestaurantAdapter(restaurants, this::onRestaurantClicked, apiService)
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

    private fun onRestaurantClicked(restaurant: Restaurant) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("FSQ_ID", restaurant.id)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
