package com.example.tay.nearby.model

import com.google.gson.annotations.SerializedName

data class Place(@SerializedName("geometry") val geometry: Geometry,
                 @SerializedName("name") val name: String,
                 @SerializedName("opening_hours") val openingHours: OpeningHours,
                 @SerializedName("photos") val photo: List<Photo>,
                 @SerializedName("place_id") val placeId: String?,
                 @SerializedName("prize_level") val prizeLevel: Int?,
                 @SerializedName("rating") val rating: Float?)
