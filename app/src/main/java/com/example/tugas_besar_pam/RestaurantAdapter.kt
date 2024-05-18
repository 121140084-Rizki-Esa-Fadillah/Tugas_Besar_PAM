import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tugas_besar_pam.R
import com.example.tugas_besar_pam.Restaurant
import com.example.tugas_besar_pam.databinding.ItemListBinding

class RestaurantAdapter(private val restaurantList: List<Restaurant>) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    inner class RestaurantViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurant: Restaurant) {
            binding.titleTempat.text = restaurant.name
            binding.ratingTempat.text = "Popularity: ${restaurant.popularity}"
            binding.jarak.text = "Jarak: ${restaurant.distance}m"
            binding.jenisMasakan.text = restaurant.category
            restaurant.imageUrl?.let { imageUrl ->
                Glide.with(binding.image1.context).load(imageUrl).into(binding.image1)
            } ?: binding.image1.setImageResource(R.drawable.logo_eatera)
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
