package com.example.tugas_besar_pam

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey
    val id: String,
    val name: String,
    val category: String,
    val imageUrl: String
)