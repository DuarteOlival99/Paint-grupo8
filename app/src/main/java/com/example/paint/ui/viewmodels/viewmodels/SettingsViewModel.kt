package com.example.paint.ui.viewmodels.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.paint.ui.viewmodels.logic.SettingsLogic


class SettingsViewModel ( application: Application ) : AndroidViewModel (application) {
    private val settingsLogic = SettingsLogic()

    fun setBackgroundColor(mDefaultColor: Int) {
        settingsLogic.setBackgroundColor(mDefaultColor)
    }

    fun setDarkModeBoolean(b: Boolean) {
        settingsLogic.setDarkModeBoolean(b)
    }

    fun getDarkModeBoolean(): Boolean {
        return settingsLogic.getDarkModeBoolean()
    }

    fun setDarkModeAutomatico(b: Boolean) {
        settingsLogic.setDarkModeAutomatico(b)
    }

    fun getDarkModeAutomatico(): Boolean {
        return settingsLogic.getDarkModeAutomatico()
    }


}