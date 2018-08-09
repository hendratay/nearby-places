package com.example.tay.nearby.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.tay.nearby.App
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

    private var retrofit: Retrofit
    private var mGooglePlacesWebService: GooglePlacesWebService
    var nextPageToken = ""

    init {
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val cache = Cache(App().getContext().cacheDir, cacheSize.toLong())
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

    // TODO : Fix response status return
    override fun loadPlaces(location: String, radius: String, type: String, pageToken: String, key: String): LiveData<List<Place>> {
        val liveData = MutableLiveData<List<Place>>()
        val call = mGooglePlacesWebService.getPlaces(location, radius, type, pageToken, key)
        call.enqueue(object : Callback<PlaceResponse> {
            override fun onResponse(call: Call<PlaceResponse>, response: Response<PlaceResponse>) {
                if (response.body()?.status == "OVER_QUERY_LIMIT") Log.i("QUERY", "Over Query Limit!")
                liveData.value = response.body()?.places
                nextPageToken = response.body()?.pageToken ?: ""
            }

            override fun onFailure(call: Call<PlaceResponse>, t: Throwable) {
            }
        })
        return liveData
    }

    override fun loadPlaceDetail(placeId: String, key: String): LiveData<PlaceDetail> {
        val liveData = MutableLiveData<PlaceDetail>()
        val call = mGooglePlacesWebService.getPlaceDetail(placeId, key)
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
