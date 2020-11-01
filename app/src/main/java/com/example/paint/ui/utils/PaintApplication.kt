package com.example.paint.ui.utils

import android.app.Application
import com.example.paint.data.sensors.battery.Battery

class PaintApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Battery.start(this)
    }
}