package com.example.tay.nearby.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tay.nearby.R
import com.example.tay.nearby.adapter.GalleryAdapter
import kotlinx.android.synthetic.main.fragment_gallery.view.*

class GalleryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_gallery, container, false)
        rootView.rv_gallery.layoutManager = GridLayoutManager(context, 3)
        rootView.rv_gallery.adapter = GalleryAdapter(requireContext(), (activity as DetailActivity).mPlaceDetail.photos ?: emptyList())
        return rootView
    }
}
