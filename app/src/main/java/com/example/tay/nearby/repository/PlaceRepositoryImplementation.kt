package com.example.tay.nearby.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.tay.nearby.App
import com.example.tay.nearby.R
import com.example.tay.nearby.api.GooglePlacesWebService
import com.example.tay.nearby.entity.PlaceResponse
import com.example.tay.nearby.entity.PlaceDetailResponse
import com.example.tay.nearby.entity.PlaceDetail
import com.example.tay.nearby.entity.Place
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlaceRepositoryImplementation: PlaceRepository {

    companion object {
        const val BASE_URL = "https://maps.googleapis.com/"
    }

    private val retrofit: Retrofit
    private val mGooglePlacesWebService: GooglePlacesWebService

    init {
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val cache = Cache(App.context.cacheDir, cacheSize.toLong())
        val okHttpClient = OkHttpClient.Builder()
                .cache(cache)
                .build()
        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        mGooglePlacesWebService = retrofit.create(GooglePlacesWebService::class.java)
    }

    override fun loadPlace(location: String, radius: String, type: String, pageToken: String, key: String): LiveData<List<Place>> {
        val liveData = MutableLiveData<List<Place>>()
        val call = mGooglePlacesWebService.getPlace(location, radius, type, pageToken, App.context.getString(R.string.google_maps_api_key))
        call.enqueue(object : Callback<PlaceResponse> {
            override fun onResponse(call: Call<PlaceResponse>, response: Response<PlaceResponse>) {
                liveData.value = response.body()?.places
            }

            override fun onFailure(call: Call<PlaceResponse>, t: Throwable) {
            }
        })
        return liveData
    }

    override fun loadPlaceDetail(placeId: String, key: String): LiveData<PlaceDetail> {
        val liveData = MutableLiveData<PlaceDetail>()
        val call = mGooglePlacesWebService.getPlaceDetail(placeId, App.context.getString(R.string.google_maps_api_key))
        call.enqueue(object : Callback<PlaceDetailResponse> {
            override fun onResponse(call: Call<PlaceDetailResponse>, responsePlace: Response<PlaceDetailResponse>) {
                liveData.value = responsePlace.body()?.placeDetailResponse
            }

            override fun onFailure(call: Call<PlaceDetailResponse>, t: Throwable) {
            }
        })
        return liveData
    }

}
