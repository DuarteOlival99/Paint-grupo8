package com.example.paint.ui.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.example.paint.R
import com.example.paint.domain.project.MainActivity
import com.example.paint.ui.viewmodels.viewmodels.SplashScreenViewModel
import kotlinx.android.synthetic.main.settings_fragment.*
import kotlin.properties.Delegates

class SplashActivity : AppCompatActivity() {
    lateinit var viewModel: SplashScreenViewModel
    private var mDefaultColor = Color.WHITE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        viewModel = ViewModelProviders.of(this).get(SplashScreenViewModel::class.java)

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
}