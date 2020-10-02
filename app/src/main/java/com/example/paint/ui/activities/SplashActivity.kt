package com.example.paint.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.paint.R
import com.example.paint.domain.project.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
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