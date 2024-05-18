package com.example.tugas_besar_pam

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tugas_besar_pam.databinding.ItemListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

class RestaurantAdapter(
    private val restaurantList: List<Restaurant>,
    private val apiService: FourSquareApiService
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    inner class RestaurantViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurant: Restaurant) {
            binding.titleTempat.text = restaurant.name
            binding.jenisMasakan.text = restaurant.category
            binding.jarak.text = formatDistance(restaurant.distance)

            // Fetch the photo
            apiService.getPhotos(restaurant.id, "fsq3ID+5NCwgxtrnfqyBktIcdxYI0AEyck+BNSA5EcQZb6w=").enqueue(object : Callback<List<Photo>> {
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
        }

        private fun formatDistance(distance: Int): String {
            return if (distance < 1000) {
                "$distance m"
            } else {
                val km = distance / 1000.0
                val df = DecimalFormat("#.#")
                "${df.format(km)} km"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(restaurantList[position])
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }
}
