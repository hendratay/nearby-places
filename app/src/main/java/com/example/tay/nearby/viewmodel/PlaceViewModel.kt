package com.example.tay.nearby.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.tay.nearby.model.remote.entity.Place
import com.example.tay.nearby.model.remote.entity.PlaceDetail
import com.example.tay.nearby.model.repository.PlaceRepository
import com.example.tay.nearby.model.repository.PlaceRepositoryImplementation

class PlaceViewModel: ViewModel() {

    private val mPlaceRepository: PlaceRepository
    private lateinit var placeResponse: LiveData<List<Place>>
    private lateinit var placeDetailResponse: LiveData<PlaceDetail>

    init {
        mPlaceRepository = PlaceRepositoryImplementation()
    }

    fun loadPlace(location: String, radius: String, type: String) {
        placeResponse = mPlaceRepository.loadPlace(location, radius, type)
    }

    fun loadPlaceDetail(placeId: String) {
        placeDetailResponse = mPlaceRepository.loadPlaceDetail(placeId)
    }

    fun getPlace(): LiveData<List<Place>> {
        return placeResponse
    }

    fun getPlaceDetail(): LiveData<PlaceDetail> {
        return placeDetailResponse
    }

}
