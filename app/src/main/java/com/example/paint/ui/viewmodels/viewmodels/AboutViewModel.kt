package com.example.paint.ui.viewmodels.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.paint.ui.viewmodels.logic.AboutLogic
import com.example.paint.ui.viewmodels.logic.SettingsLogic


class AboutViewModel (application: Application ) : AndroidViewModel (application) {
    private val aboutLogic = AboutLogic()

    fun getBackgroundColor(): Int? {
        return aboutLogic.getBackgroundColor()
    }



}