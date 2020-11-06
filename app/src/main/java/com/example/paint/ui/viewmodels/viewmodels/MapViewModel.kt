package com.example.paint.ui.viewmodels.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.paint.ui.viewmodels.logic.MapLogic
import com.example.paint.ui.viewmodels.logic.PaintLogic
import com.google.android.gms.maps.model.LatLng

class MapViewModel(application: Application) : AndroidViewModel(application)  {

    private val mapLogic = MapLogic()

    fun getDrawingMap(): Boolean {
        return mapLogic.getDrawingMap()
    }

    fun setDrawingMap(b: Boolean) {
        mapLogic.setDrawingMap(b)
    }

    fun getPreviousLocation(): LatLng {
        return mapLogic.getPreviousLocation()
    }

    fun getActualLocation(): LatLng {
        return mapLogic.getActualLocation()
    }

    fun previousLocationEqualLocation() {
        mapLogic.previousLocationEqualLocation()
    }

}