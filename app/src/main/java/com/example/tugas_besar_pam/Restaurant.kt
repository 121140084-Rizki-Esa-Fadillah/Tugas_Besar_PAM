package com.example.tugas_besar_pam

data class Restaurant(
    val id: String,
    val name: String,
    val category: String,
    val distance: Int,
    val imageUrl: String
)

data class Photo(
    val prefix: String,
    val suffix: String,
    val width: Int,
    val height: Int
) {
    val url: String
        get() = "$prefix${width}x${height}$suffix"
}
