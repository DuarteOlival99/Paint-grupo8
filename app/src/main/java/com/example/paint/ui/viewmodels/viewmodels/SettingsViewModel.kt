package com.example.paint.ui.viewmodels.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.paint.ui.viewmodels.logic.SettingsLogic


class SettingsViewModel ( application: Application ) : AndroidViewModel (application) {
    private val settingsLogic = SettingsLogic()

    fun setBackgroundColor(mDefaultColor: Int) {
        settingsLogic.setBackgroundColor(mDefaultColor)
    }



}