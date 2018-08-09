package com.example.tay.nearby.api

import com.example.tay.nearby.entity.PlaceResponse
import com.example.tay.nearby.entity.PlaceDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GooglePlacesWebService {

    @GET("maps/api/place/nearbysearch/json")
    fun getPlaces(@Query("location") location: String,
                  @Query("radius") radius: String,
                  @Query("type") type: String,
                  @Query("pagetoken") pageToken: String,
                  @Query("key") apiKey: String): Call<PlaceResponse>

    @GET("maps/api/place/details/json")
    fun getPlaceDetail(@Query("placeid") placeid: String,
                       @Query("key") key: String): Call<PlaceDetailResponse>

}
