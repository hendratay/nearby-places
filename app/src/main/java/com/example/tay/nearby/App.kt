package com.example.tay.nearby

import android.app.Application
import android.content.Context

class App: Application() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        App.context = applicationContext
    }

}