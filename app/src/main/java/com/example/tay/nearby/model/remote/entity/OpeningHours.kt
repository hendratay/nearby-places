package com.example.tay.nearby.model.remote.entity

import com.google.gson.annotations.SerializedName

data class OpeningHours(@SerializedName("open_now") val openNow: Boolean)
