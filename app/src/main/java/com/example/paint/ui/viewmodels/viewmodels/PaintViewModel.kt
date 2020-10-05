package com.example.paint.ui.viewmodels.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.paint.ui.viewmodels.logic.AboutLogic
import com.example.paint.ui.viewmodels.logic.PaintLogic
import com.example.paint.ui.viewmodels.logic.SettingsLogic


class PaintViewModel (application: Application ) : AndroidViewModel (application) {
    private val paintLogic = PaintLogic()

    fun getBackgroundColor(): Int? {
        return paintLogic.getBackgroundColor()
    }



}