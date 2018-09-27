package com.example.tay.nearby.model

import com.google.gson.annotations.SerializedName

data class PlaceDetailResponse(@SerializedName("result") val placeDetailResponse: PlaceDetail,
                               @SerializedName("status") val status: String)
