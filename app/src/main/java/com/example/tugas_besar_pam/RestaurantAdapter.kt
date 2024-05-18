package com.example.tugas_besar_pam

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tugas_besar_pam.R
import com.example.tugas_besar_pam.Restaurant
import com.example.tugas_besar_pam.databinding.ItemListBinding
import java.text.DecimalFormat

class RestaurantAdapter(private val restaurantList: List<Restaurant>) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    inner class RestaurantViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurant: Restaurant) {
            binding.titleTempat.text = restaurant.name
            binding.jenisMasakan.text = restaurant.category.toString()
            binding.jarak.text = formatDistance(restaurant.distance)
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

    private fun formatDistance(distance: Int): String {
        return if (distance < 1000) {
            "$distance m"
        } else {
            val km = distance / 1000.0 // Konversi ke kilometer dengan desimal
            val df = DecimalFormat("#.#") // Format dengan satu angka di belakang koma
            "${df.format(km)} km"
        }
    }
}
