package com.example.tay.nearby.view.activity

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.view.GravityCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import com.example.tay.nearby.R
import com.example.tay.nearby.model.Place
import com.example.tay.nearby.view.adapter.PlaceTypeAdapter
import com.example.tay.nearby.utils.RecyclerViewSnapHelper
import com.example.tay.nearby.view.adapter.PlaceAdapter
import com.example.tay.nearby.view.utils.Location
import com.example.tay.nearby.view.utils.Permission
import com.example.tay.nearby.view.utils.snackBar
import com.example.tay.nearby.view.utils.toast
import com.example.tay.nearby.viewmodel.PlaceViewModel
import com.github.johnpersano.supertoasts.library.SuperActivityToast
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_master.*

const val TAG = "MasterActivity"
const val REQUEST_ACCESS_FINE_LOCATION = 111
const val REQUEST_CHECK_SETTINGS = 222
class MasterActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var placeTypeAdapter: PlaceTypeAdapter
    private lateinit var placeAdapter: PlaceAdapter
    private lateinit var placeViewModel: PlaceViewModel
    private val listPlaceType: MutableList<String> = ArrayList()

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private var savedLatitude: Double? = null
    private var savedLongitude: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_master)
        placeViewModel = ViewModelProviders.of(this)[PlaceViewModel::class.java]
        setupUI()
        if (hasNetwork()) {
            createLocationRequest()
            receiveLocationUpdates()
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            startLocationUpdates()
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CHECK_SETTINGS -> {
                when(resultCode) {
                    Activity.RESULT_OK -> startLocationUpdates()
                    Activity.RESULT_CANCELED -> {
                        getSavedLocation()
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            REQUEST_ACCESS_FINE_LOCATION -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates()
                } else {
                    getSavedLocation()
                }
            }
        }
    }

    private fun setupUI() {
        transparencyBar()
        setupNavigationDrawer()
        setupToolbar()
        setupMap()
        setupRecyclerView()
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

    // todo: get latitude and longitude
    override fun onMapReady(googleMap: GoogleMap?) {
        val sydney = LatLng(-33.852, 151.211)
        googleMap?.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private fun setupRecyclerView() {
        populatePlaceType()
        placeTypeAdapter = PlaceTypeAdapter(listPlaceType) { placeTypeClick(it) }
        recycler_view_place_type.apply {
            RecyclerViewSnapHelper().attachToRecyclerView(this)
            layoutManager = LinearLayoutManager(this@MasterActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = placeTypeAdapter
        }
    }

    private fun populatePlaceType() {
        val placeTypeArray = resources.getStringArray(R.array.place_type_array)
        for (placeType in placeTypeArray) listPlaceType.add(placeType)
    }

    // todo: get latitude and longitude
    private fun placeTypeClick(placeType: String) {
        placeViewModel.loadPlace("1.128780, 104.040340", "50000", placeType)
        placeViewModel.getPlace().observe(this,
                Observer<List<Place>> { listPlace ->
                    Log.d(TAG, listPlace.toString())
                })
    }

    private fun hasNetwork(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting ?: false
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest().apply {
            interval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnFailureListener {response ->
            if (response is ResolvableApiException){
                try {
                    response.startResolutionForResult(this, REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    toast(this, getString(R.string.notice_error_enable_location))
                }
            }
        }
    }

    private fun receiveLocationUpdates() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for(location in locationResult.locations) {
                    Location.saveLatLngSharedPref(this@MasterActivity,
                            java.lang.Double.doubleToRawLongBits(location.latitude),
                            java.lang.Double.doubleToRawLongBits(location.longitude))
                }
                stopLocationUpdates()
            }
        }
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun startLocationUpdates() {
        if(ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_ACCESS_FINE_LOCATION)
        } else {
            if(Permission.getPermissionSharedPref(this)) {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_ACCESS_FINE_LOCATION)
                Permission.savePermissionSharedPref(this, false)
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_ACCESS_FINE_LOCATION)
                snackBar(this, getString(R.string.notice_asking_enable_location_permission), getString(R.string.notice_enable_location_permission), object : SuperActivityToast.OnButtonClickListener {
                    override fun onClick(view: View?, token: Parcelable?) {
                        val intent = Intent()
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.apply {
                            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            data = uri
                        }
                        startActivity(intent)
                    }
                })
            }
        }
    }

    private fun getSavedLocation() {
        val location = Location.getLatLngSharedPref(this)
        savedLatitude = java.lang.Double.longBitsToDouble(location.first)
        savedLongitude = java.lang.Double.longBitsToDouble(location.second)
        if(savedLatitude == 0.0) savedLatitude = null
        if(savedLongitude == 0.0) savedLongitude = null
    }

}
