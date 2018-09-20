package com.example.tay.nearby.model.remote.entity

import com.google.gson.annotations.SerializedName

data class Geometry(@SerializedName("location") val location: Location)
