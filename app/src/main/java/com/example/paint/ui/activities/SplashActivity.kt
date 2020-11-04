package com.example.paint.ui.activities

import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProviders
import com.example.paint.R
import com.example.paint.domain.project.MainActivity
import com.example.paint.ui.viewmodels.viewmodels.PaintViewModel
import com.example.paint.ui.viewmodels.viewmodels.SettingsViewModel
import com.example.paint.ui.viewmodels.viewmodels.SplashScreenViewModel
import kotlinx.android.synthetic.main.settings_fragment.*
import kotlin.properties.Delegates

class SplashActivity : AppCompatActivity() {
    lateinit var viewModel: SplashScreenViewModel
    lateinit var viewModelSettings: SettingsViewModel
    private var mDefaultColor = Color.WHITE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        viewModel = ViewModelProviders.of(this).get(SplashScreenViewModel::class.java)
        viewModelSettings = ViewModelProviders.of(this).get(SettingsViewModel::class.java)

        if(viewModelSettings.getDarkModeAutomatico()){
            verificaBateria()
        }

        val pref = PreferenceManager.getDefaultSharedPreferences(baseContext)
        pref.apply {
            val backgroundColor = getInt("BACKGROUNDCOLOR", Color.WHITE)
            mDefaultColor = backgroundColor
            viewModel.setBackgroundColor(mDefaultColor)
        }

        val editor = pref.edit()
        editor
            .putInt("BACKGROUNDCOLOR", mDefaultColor)
            .apply()

        viewModel.setBackgroundColor(mDefaultColor)

        splash()
    }

    fun splash(){
        val duration = 3000L
        val handle = Handler()
        handle.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, duration)
    }

    fun verificaBateria(){
        val iFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus = this.registerReceiver(null, iFilter)
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

        if (batLevel <= 20 && !viewModelSettings.getDarkModeBoolean()){
            Log.i("level", batLevel.toString())
            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES
            )
            viewModelSettings.setDarkModeBoolean(true)
        }
    }

}