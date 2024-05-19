package com.example.tugas_besar_pam

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tugas_besar_pam.databinding.ListSlideBinding

class ImageAdapter(private val items: List<ImageData>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    inner class ImageViewHolder(itemView: ListSlideBinding) : RecyclerView.ViewHolder(itemView.root) {
        private val binding = itemView

        fun bind(data: ImageData) {
            with(binding) {
                Glide.with(itemView).load(data.imgUrl).into(slideImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(ListSlideBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
