package com.example.paint.ui.viewmodels.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.paint.ui.viewmodels.logic.SettingsLogic
import com.example.paint.ui.viewmodels.logic.SplashScreenLogic


class SplashScreenViewModel (application: Application ) : AndroidViewModel (application) {
    private val splashScreenLogic = SplashScreenLogic()

    fun setBackgroundColor(mDefaultColor: Int) {
        splashScreenLogic.setBackgroundColor(mDefaultColor)
    }
}