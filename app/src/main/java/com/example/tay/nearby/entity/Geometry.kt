package com.example.tay.nearby.entity

import com.google.gson.annotations.SerializedName

data class Geometry(@SerializedName("location") val location: Location?)
