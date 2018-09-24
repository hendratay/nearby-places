package com.example.tay.nearby.model.repository

import android.arch.lifecycle.LiveData
import com.example.tay.nearby.model.remote.entity.Place
import com.example.tay.nearby.model.remote.entity.PlaceDetail

interface PlaceRepository {

    fun loadPlace(location: String, radius: String, type: String): LiveData<List<Place>>

    fun loadPlaceDetail(placeId: String): LiveData<PlaceDetail>

}
