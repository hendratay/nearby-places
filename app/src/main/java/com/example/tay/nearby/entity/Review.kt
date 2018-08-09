package com.example.tay.nearby.entity

import com.google.gson.annotations.SerializedName

data class Review(@SerializedName("author_name") val reviewName: String?,
                  @SerializedName("profile_photo_url") val reviewPhoto: String?,
                  @SerializedName("rating") val reviewRating: Float,
                  @SerializedName("text") val reviewText: String?,
                  @SerializedName("time") val reviewTime: Long,
                  @SerializedName("relative_time_description") val timeDescription: String?)
