package com.example.tay.nearby.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.tay.nearby.remote.entity.Place
import com.example.tay.nearby.remote.entity.PlaceDetail
import com.example.tay.nearby.repository.PlaceRepository
import com.example.tay.nearby.repository.PlaceRepositoryImplementation

class PlaceViewModel: ViewModel() {

    private val mPlaceRepository: PlaceRepository
    private lateinit var placeResponse: LiveData<List<Place>>
    private lateinit var placeDetailResponse: LiveData<PlaceDetail>

    init {
        mPlaceRepository = PlaceRepositoryImplementation()
    }

    fun loadPlace(location: String, radius: String, type: String, pageToken: String, key: String) {
        placeResponse = mPlaceRepository.loadPlace(location, radius, type, pageToken, key)
    }

    fun loadPlaceDetail(placeId: String, key: String) {
        placeDetailResponse = mPlaceRepository.loadPlaceDetail(placeId, key)
    }

    fun getPlace(): LiveData<List<Place>> {
        return placeResponse
    }

    fun getPlaceDetail(): LiveData<PlaceDetail> {
        return placeDetailResponse
    }

}
