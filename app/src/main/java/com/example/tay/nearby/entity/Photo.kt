package com.example.tay.nearby.entity

import com.google.gson.annotations.SerializedName

data class Photo(@SerializedName("width") val width: Int,
                 @SerializedName("height") val height: Int,
                 @SerializedName("photo_reference") val photoreference: String?)
