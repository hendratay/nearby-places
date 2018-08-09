package com.example.tay.nearby.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.tay.nearby.R
import com.example.tay.nearby.adapter.PlaceAdapter.Companion.PHOTO_URL
import kotlinx.android.synthetic.main.fragment_about.view.*

class AboutFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_about, container, false)
        val placeDetail = (activity as DetailActivity).mPlaceDetail
        Glide.with(this)
                .load(PHOTO_URL + placeDetail.photos?.get(0)?.photoreference)
                .apply(RequestOptions()
                        .override(600, 400)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop())
                .into(rootView.place_photo);
        rootView.place_address.text = placeDetail.address
        rootView.place_website.text = placeDetail.website
        rootView.place_phone.text = placeDetail.phoneNumber
        return rootView
    }

}

