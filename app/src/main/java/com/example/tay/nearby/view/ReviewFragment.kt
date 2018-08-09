package com.example.tay.nearby.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tay.nearby.R
import com.example.tay.nearby.adapter.ReviewAdapter
import kotlinx.android.synthetic.main.fragment_review.view.*

class ReviewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_review, container, false)
        rootView.rv_review.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rootView.rv_review.adapter = ReviewAdapter(requireContext(), (activity as DetailActivity).mPlaceDetail.review ?: emptyList())
        return rootView
    }

}
