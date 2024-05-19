import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tugas_besar_pam.R

class UlasanAdapter(private val ulasanList: List<String>) : RecyclerView.Adapter<UlasanAdapter.UlasanViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UlasanViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_ulasan, parent, false)
        return UlasanViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UlasanViewHolder, position: Int) {
        val currentUlasan = ulasanList[position]
        holder.textViewUlasan.text = currentUlasan
    }

    override fun getItemCount() = ulasanList.size

    class UlasanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewUlasan: TextView = itemView.findViewById(R.id.textViewUlasan)
    }
}
