package com.example.tugas_besar_pam

data class PlaceDetails(
    val name: String,
    val location: Location,
    val rating: Double?,
    val hours: Hours
)

data class Location(
    val address: String,
    val country: String,
    val cross_street: String?,
    val formatted_address: String,
    val locality: String,
    val region: String
)

data class Hours(
    val display: String,
    val is_local_holiday: Boolean,
    val open_now: Boolean,
    val regular: List<RegularHours>
)

data class RegularHours(
    val close: String,
    val day: Int,
    val open: String
)


