package com.example.tugas_besar_pam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugas_besar_pam.databinding.FragmentFavoriteBinding
import com.example.tugas_besar_pam.FavoriteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var favoriteDao: FavoriteDao
    private lateinit var apiService: FourSquareApiService
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Inisialisasi FavoriteDao dan FourSquareApiService
        favoriteDao = FavoriteDatabase(requireContext()).favoriteDao()
        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        apiService = RetrofitInstance.api

        // Load data dari database
        loadFavorites()

        return view
    }

    private fun loadFavorites() {
        lifecycleScope.launch {
            val favorites = withContext(Dispatchers.IO) { favoriteDao.getAllFavorites() }
            updateRecyclerView(favorites)
        }
    }

    private fun updateRecyclerView(favorites: List<Favorite>) {
        val adapter = FavoriteAdapter(favorites, this::onFavoriteClicked, this::onDeleteClicked, apiService)
        recyclerView.adapter = adapter
    }

    private fun onFavoriteClicked(favorite: Favorite) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("FSQ_ID", favorite.id)
        startActivity(intent)
    }

    private fun onDeleteClicked(favorite: Favorite) {
        favoriteViewModel.deleteFavorite(favorite)
        lifecycleScope.launch {
            loadFavorites()
            // Show a toast message on the main thread after deletion
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), "Favorite successfully deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
