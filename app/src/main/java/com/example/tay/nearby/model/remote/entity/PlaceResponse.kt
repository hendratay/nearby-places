package com.example.tay.nearby.model.remote.entity

import com.google.gson.annotations.SerializedName

data class PlaceResponse(@SerializedName("results") val places: List<Place>,
                         @SerializedName("status") val status: String,
                         @SerializedName("next_page_token") val pageToken: String?)
