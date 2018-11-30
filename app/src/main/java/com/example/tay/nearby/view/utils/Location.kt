package com.example.tay.nearby.view.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.tay.nearby.R

class Location {

    companion object {
        private fun getSharedPrefs(context: Context): SharedPreferences {
            return PreferenceManager.getDefaultSharedPreferences(context)
        }

        fun saveLatLngSharedPref(context: Context, lat: Long, lng: Long) {
            val editor =  getSharedPrefs(context).edit()
            editor.putLong(context.getString(R.string.saved_latitude), lat)
            editor.putLong(context.getString(R.string.saved_longitude), lng)
            editor.apply()
        }

        fun getLatLngSharedPref(context: Context): Pair<Long, Long> {
            val prefs = getSharedPrefs(context)
            val lat = prefs.getLong(context.getString(R.string.saved_latitude), 0)
            val lng = prefs.getLong(context.getString(R.string.saved_longitude), 0)
            return Pair(lat, lng)
        }

    }

}