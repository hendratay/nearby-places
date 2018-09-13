package com.example.tay.nearby.repository

import android.arch.lifecycle.LiveData
import com.example.tay.nearby.remote.entity.Place
import com.example.tay.nearby.remote.entity.PlaceDetail

interface PlaceRepository {

    fun loadPlace(location: String, radius: String, type: String, pageToken: String, key: String): LiveData<List<Place>>

    fun loadPlaceDetail(placeId: String, key: String): LiveData<PlaceDetail>

}
