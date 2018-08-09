package com.example.tay.nearby.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.tay.nearby.view.AboutFragment
import com.example.tay.nearby.view.GalleryFragment
import com.example.tay.nearby.R
import com.example.tay.nearby.view.ReviewFragment

class DetailPagerAdapter(fm: FragmentManager, private val mContext: Context) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> AboutFragment()
            1 -> ReviewFragment()
            2 -> GalleryFragment()
            else -> null
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> mContext.getString(R.string.about)
            1 -> mContext.getString(R.string.review)
            2 -> mContext.getString(R.string.gallery)
            else -> null
        }
    }

}
