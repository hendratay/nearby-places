package com.example.tay.nearby.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import com.example.tay.nearby.R
import com.example.tay.nearby.model.remote.entity.Place
import com.example.tay.nearby.view.adapter.PlaceTypeAdapter
import com.example.tay.nearby.utils.RecyclerViewSnapHelper
import com.example.tay.nearby.view.adapter.PlaceAdapter
import com.example.tay.nearby.viewmodel.PlaceViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_master.*

class MasterActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var placeTypeAdapter: PlaceTypeAdapter
    private lateinit var placeAdapter: PlaceAdapter
    private lateinit var placeViewModel: PlaceViewModel
    private val listPlaceType: MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_master)
        placeViewModel = ViewModelProviders.of(this)[PlaceViewModel::class.java]
        transparencyBar()
        setupNavigationDrawer()
        setupToolbar()
        setupMap()
        setupRecyclerView()
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

    private fun placeTypeClick(placeType: String) {
        placeViewModel.loadPlace("1.128780, 104.040340", "50000", placeType)
        placeViewModel.getPlace().observe(this,
                Observer<List<Place>> {
                    Log.d("GOOGLE PLACES", it.toString())
                })
    }

}
