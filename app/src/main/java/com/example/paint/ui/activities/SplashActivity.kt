package com.example.paint.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import androidx.lifecycle.ViewModelProviders
import com.example.paint.R
import com.example.paint.domain.project.MainActivity
import com.example.paint.ui.viewmodels.viewmodels.SplashScreenViewModel
import kotlinx.android.synthetic.main.settings_fragment.*
import kotlin.properties.Delegates

class SplashActivity : AppCompatActivity() {
    lateinit var viewModel: SplashScreenViewModel
    private var mDefaultColor by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        viewModel = ViewModelProviders.of(this).get(SplashScreenViewModel::class.java)

        val pref = PreferenceManager.getDefaultSharedPreferences(baseContext)
        pref.apply {
            val backgroundColor = getInt("BACKGROUNDCOLOR", R.color.white)
            mDefaultColor = backgroundColor
            viewModel.setBackgroundColor(backgroundColor)
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