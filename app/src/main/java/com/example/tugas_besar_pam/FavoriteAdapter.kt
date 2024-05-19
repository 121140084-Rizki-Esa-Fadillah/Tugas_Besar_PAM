package com.example.tugas_besar_pam

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tugas_besar_pam.databinding.ItemFavoriteBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

class FavoriteAdapter(
    private val favoriteList: List<Favorite>,
    private val onFavoriteClicked: (Favorite) -> Unit,
    private val onDeleteClicked: (Favorite) -> Unit,
    private val apiService: FourSquareApiService // Menambahkan FourSquareApiService sebagai parameter
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            binding.titleTempat.text = favorite.name
            binding.jenisMasakan.text = favorite.category

            // Fetch the photo
            apiService.getPhotos(favorite.id, "fsq3ID+5NCwgxtrnfqyBktIcdxYI0AEyck+BNSA5EcQZb6w=").enqueue(object : Callback<List<Photo>> {
                override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {
                    val photos = response.body()
                    if (!photos.isNullOrEmpty()) {
                        val photoUrl = photos[0].url
                        Glide.with(binding.image1.context)
                            .load(photoUrl)
                            .placeholder(R.drawable.logo_eatera)
                            .error(R.drawable.onboarding_map)
                            .into(binding.image1)
                    } else {
                        binding.image1.setImageResource(R.drawable.onboarding_map)
                    }
                }

                override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                    binding.image1.setImageResource(R.drawable.onboarding_map)
                }
            })

            // Handle restaurant item click
            binding.root.setOnClickListener {
                onFavoriteClicked(favorite)
            }

            binding.iconDelete.setOnClickListener {
                onDeleteClicked(favorite)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favoriteList[position])
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }
}
