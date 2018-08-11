package com.example.tay.nearby.view.activity

import android.arch.lifecycle.Observer
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.tay.nearby.R
import com.example.tay.nearby.adapter.DetailPagerAdapter
import com.example.tay.nearby.entity.PlaceDetail
import com.example.tay.nearby.view.activity.MasterActivity.Companion.PLACE_ID
import com.example.tay.nearby.viewmodel.ListPlaceViewModel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var mLocationUri: Uri
    private lateinit var mViewModel: ListPlaceViewModel
    lateinit var mPlaceDetail: PlaceDetail

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val placeId = intent.getStringExtra(PLACE_ID)
        mViewModel.loadPlaceDetail(placeId, getString(R.string.google_maps_api_key))
        mViewModel.detailResponse.observe(this,
            Observer { it ->
                it?.let {
                    mPlaceDetail = it
                    val lat = it.geometry?.location?.lat
                    val lng = it.geometry?.location?.lng
                    val location = String.format("geo:%f,%f?q=%s", lat, lng, Uri.encode(it.name))
                    mLocationUri = Uri.parse(location)
                }
            }
        )

        view_pager_detail.adapter = DetailPagerAdapter(supportFragmentManager, this)
        tab_layout_detail.setupWithViewPager(view_pager_detail)
    }

    private fun openGoogleApp(locationUri: Uri?) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = locationUri
        intent.setPackage("com.google.android.apps.maps")
        startActivity(intent)
    }

}
