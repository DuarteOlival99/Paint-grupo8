package com.example.paint.ui.viewmodels.viewmodels

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import com.example.paint.data.entity.HistoryRoute
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

    fun addInicalPosition(location: Location?) {
        mapLogic.addInicalPosition(location)
    }

    fun addRoute(previousLocation: Location, actualLocation: Location) {
        mapLogic.addRoute(previousLocation, actualLocation)
    }

    fun getInicialLocation(): Location {
        return mapLogic.getInicialLocation()
    }

    fun getfinalLocation(): Location {
        return mapLogic.getfinalLocation()
    }

    fun addFinalPosition(location: Location?) {
        mapLogic.addFinalPosition(location)
    }

    fun getHistoryRouteList(): List<HistoryRoute> {
        return mapLogic.getHistoryRouteList()
    }

}