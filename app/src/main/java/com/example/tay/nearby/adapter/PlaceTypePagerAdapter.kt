package com.example.tay.nearby.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.tay.nearby.R
import com.example.tay.nearby.view.fragment.PlaceTypeFragment

class PlaceTypePagerAdapter(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> PlaceTypeFragment()
            1 -> PlaceTypeFragment()
            2 -> PlaceTypeFragment()
            else -> null
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.getString(R.string.about)
            1 -> context.getString(R.string.review)
            2 -> context.getString(R.string.gallery)
            else -> null
        }
    }

}