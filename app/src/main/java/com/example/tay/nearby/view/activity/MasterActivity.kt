package com.example.tay.nearby.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import com.example.tay.nearby.R
import com.example.tay.nearby.entity.Place
import com.example.tay.nearby.adapter.PlaceAdapter
import com.example.tay.nearby.viewmodel.ListPlaceViewModel
import com.google.android.gms.location.places.PlaceDetectionClient
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_master.*

class MasterActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        const val PLACE_ID = "com.example.tay.nearby.PLACE_DETAIL"
        private const val mRadius = "50000" // 50,000 meters
    }

    private lateinit var mPlaceDetectionClient: PlaceDetectionClient
    private lateinit var mViewModel: ListPlaceViewModel
    private lateinit var mAdapter: PlaceAdapter

    private var mLocation = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_master)
        transparencyBar()
        setupNavigationDrawer()
        setupToolbar()
        setupMap()

        // TODO : Check location service
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this)
        mViewModel = ViewModelProviders.of(this)[ListPlaceViewModel::class.java]
        getDeviceLocation()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun transparencyBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
    }

    private fun setupNavigationDrawer() {
        nav_view.setNavigationItemSelectedListener {
            it.isChecked = true
            drawer_layout.closeDrawers()
            true
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)
        }
    }

    private fun setupMap() {
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap?) {
        val sydney = LatLng(-33.852, 151.211)
        p0?.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        p0?.moveCamera(CameraUpdateFactory.newLatLng(sydney))
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
        mViewModel.loadPlace(mLocation, mRadius, "cafe", "", getString(R.string.google_maps_api_key))
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
