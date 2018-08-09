package com.example.tay.nearby.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.example.tay.nearby.R
import com.example.tay.nearby.entity.Place
import com.example.tay.nearby.adapter.PlaceAdapter
import com.example.tay.nearby.viewmodel.ListPlaceViewModel
import com.google.android.gms.location.places.PlaceDetectionClient
import com.google.android.gms.location.places.Places
import kotlinx.android.synthetic.main.activity_master.*
import java.util.ArrayList

class MasterActivity : AppCompatActivity() {

    companion object {
        const val PLACE_ID = "com.example.tay.nearby.PLACE_DETAIL"
        private const val mRadius = "50000" // 50,000 meters
    }

    private lateinit var mPlaceDetectionClient: PlaceDetectionClient
    private lateinit var mViewModel: ListPlaceViewModel
    private lateinit var mAdapter: PlaceAdapter

    private var mLocation = ""
    private val mPlaceList = ArrayList<Place>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_master)

        // TODO : Check location service

        mPlaceDetectionClient = Places.getPlaceDetectionClient(this)
        mViewModel = ViewModelProviders.of(this)[ListPlaceViewModel::class.java]

        rv_master.layoutManager = GridLayoutManager(this, 2)
        mAdapter = PlaceAdapter(this, mPlaceList)
        rv_master.adapter = mAdapter

        getDeviceLocation()
/*        val scrollListener = object : EndlessRecyclerViewScrollListener(GridLayoutManager(this, 2)) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                if (nextPageToken != null) getPlace()
            }
        }
        rv_master.addOnScrollListener(scrollListener)*/
    }

    // TODO : get the highest likelyhodd for getplace
    private fun getDeviceLocation() {
        try {
            val locationResult = mPlaceDetectionClient.getCurrentPlace(null)
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val likelyPlace = task.result
                    val lat = likelyPlace[0].place.latLng.latitude
                    val lng = likelyPlace[0].place.latLng.longitude
                    mLocation = "$lat,$lng"
                    getPlace()
                    likelyPlace.release()
                }
            }
        } catch (e: SecurityException) {
        }

    }

    private fun getPlace() {
        mViewModel.loadPlace(mLocation, mRadius, "cafe", "", getString(R.string.google_places_api_key))
        mViewModel.apiResponse.observe(this,
                Observer { apiResponse -> handleResponse(apiResponse) })
    }

    private fun handleResponse(places: List<Place>?) {
        if (places != null && places.isNotEmpty()) mAdapter.addAll(places)
    }

    private fun getPlaceDetail(place: Place) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(PLACE_ID, place.placeId)
        startActivity(intent)
    }

}
