package com.example.tugas_besar_pam

data class Photo(
    val prefix: String,
    val suffix: String,
    val width: Int,
    val height: Int
) {
    val url: String
        get() = "$prefix${width}x${height}$suffix"
}