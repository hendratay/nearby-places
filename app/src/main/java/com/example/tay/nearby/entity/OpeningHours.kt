package com.example.tay.nearby.entity

import com.google.gson.annotations.SerializedName

data class OpeningHours(@SerializedName("open_now") val openNow: Boolean = false,
                        @SerializedName("weekday_text") val weekdayText: Array<String>?)
