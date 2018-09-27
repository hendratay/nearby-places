package com.example.tay.nearby.model.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.tay.nearby.App
import com.example.tay.nearby.BuildConfig
import com.example.tay.nearby.api.GooglePlacesWebService
import com.example.tay.nearby.model.Place
import com.example.tay.nearby.model.PlaceDetail
import com.example.tay.nearby.model.PlaceDetailResponse
import com.example.tay.nearby.model.PlaceResponse
import okhttp3.Cache
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlaceRepository {

    companion object {
        const val BASE_URL = "https://maps.googleapis.com/"
    }

    private val retrofit: Retrofit
    private val mGooglePlacesWebService: GooglePlacesWebService

    init {
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val cache = Cache(App.context.cacheDir, cacheSize.toLong())
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(GooglePlacesInterceptor())
                .cache(cache)
                .build()
        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        mGooglePlacesWebService = retrofit.create(GooglePlacesWebService::class.java)
    }

    fun loadPlace(location: String, radius: String, type: String): LiveData<List<Place>> {
        val liveData = MutableLiveData<List<Place>>()
        val call = mGooglePlacesWebService.getPlace(location, radius, type)
        call.enqueue(object : Callback<PlaceResponse> {
            override fun onResponse(call: Call<PlaceResponse>, response: Response<PlaceResponse>) {
                liveData.value = response.body()?.places
            }

            override fun onFailure(call: Call<PlaceResponse>, t: Throwable) {
            }
        })
        return liveData
    }

    fun loadPlaceDetail(placeId: String): LiveData<PlaceDetail> {
        val liveData = MutableLiveData<PlaceDetail>()
        val call = mGooglePlacesWebService.getPlaceDetail(placeId)
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

class GooglePlacesInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val url: HttpUrl = chain.request().url().newBuilder()
                .addQueryParameter("key", BuildConfig.GOOGLE_PLACES_API_KEY)
                .build()
        return chain.proceed(chain.request().newBuilder().addHeader("Accept", "application/json").url(url).build())
    }
}
