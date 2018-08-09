package com.example.tay.nearby.repository

import android.arch.lifecycle.LiveData
import com.example.tay.nearby.entity.Place
import com.example.tay.nearby.entity.PlaceDetail

interface PlaceRepository {

    fun loadPlaces(location: String, radius: String, type: String, pageToken: String, key: String): LiveData<List<Place>>

    fun loadPlaceDetail(placeId: String, key: String): LiveData<PlaceDetail>

}
