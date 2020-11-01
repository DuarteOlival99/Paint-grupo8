package com.example.paint.data.sensors.battery

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate

class Battery private constructor(private val context: Context) : Runnable {

    private val TAG = Battery::class.java.simpleName
    private val TIME_BETWEEN_UPDATES = 2000L

    companion object {

        private var instance: Battery? = null
        private val handler = Handler()

        fun start (context: Context) {
            Log.i("battery", "começo")
            instance = if ( instance == null ) Battery(context) else instance
            instance?.start()
        }

    }

    private fun start() {
        handler.postDelayed(this, TIME_BETWEEN_UPDATES)

    }

    private fun getBaterryCurrentNow() : Double {
        val iFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus = context.registerReceiver(null, iFilter)
        val level = batteryStatus?.getIntExtra(
            BatteryManager.EXTRA_LEVEL,
            -1
        ) ?: -1
        val scale = batteryStatus?.getIntExtra(
            BatteryManager.EXTRA_SCALE,
            -1
        ) ?: -1
        val batteryPct = level / scale.toDouble()
        val batLevel = (batteryPct * 100).toInt()

        if (batLevel <= 20){
            Log.i("level", batLevel.toString())
            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES
            )
        } else{
            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        return batLevel.toDouble()
    }

    override fun run() {
        val current = getBaterryCurrentNow()
        Log.i(TAG, current.toString())
        handler.postDelayed(this, TIME_BETWEEN_UPDATES)
    }




}