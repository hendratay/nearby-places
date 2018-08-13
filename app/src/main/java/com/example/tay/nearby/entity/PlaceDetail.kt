package com.example.tay.nearby.entity

import com.google.gson.annotations.SerializedName

data class PlaceDetail(@SerializedName("formatted_address") val address: String,
                       @SerializedName("formatted_phone_number") val phoneNumber: String,
                       @SerializedName("name") val name: String,
                       @SerializedName("geometry") val geometry: Geometry,
                       @SerializedName("website") val website: String,
                       @SerializedName("reviews") val review: List<Review>,
                       @SerializedName("photos") val photos: List<Photo>)
