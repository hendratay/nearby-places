package com.example.tay.nearby.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.tay.nearby.entity.Place
import com.example.tay.nearby.entity.PlaceDetail
import com.example.tay.nearby.repository.PlaceRepository
import com.example.tay.nearby.repository.PlaceRepositoryImplementation

class ListPlaceViewModel: ViewModel() {

    private val mPlaceRepository: PlaceRepository
    lateinit var apiResponse: LiveData<List<Place>>
    lateinit var detailResponse: LiveData<PlaceDetail>

    init {
        mPlaceRepository = PlaceRepositoryImplementation()
    }

    fun loadPlace(location: String, radius: String, type: String, pageToken: String, key: String) {
        apiResponse = mPlaceRepository.loadPlaces(location, radius, type, pageToken, key)
    }

    fun loadPlaceDetail(placeId: String, key: String) {
        detailResponse = mPlaceRepository.loadPlaceDetail(placeId, key)
    }

}
