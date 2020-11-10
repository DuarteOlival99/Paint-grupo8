package com.example.paint.ui.viewmodels.logic

import android.location.Location
import com.example.paint.data.entity.HistoryRoute
import com.example.paint.data.local.list.ListStorage
import com.example.paint.ui.utils.Extensions
import com.google.android.gms.maps.model.LatLng

class MapLogic() {

    private val storage = ListStorage.getInstance()
    private val extensions = Extensions()

    fun getDrawingMap(): Boolean {
        return storage.getDrawingMap()
    }

    fun setDrawingMap(b: Boolean) {
        storage.setDrawingMap(b)
    }

    fun getPreviousLocation(): LatLng {
        val l = storage.getPreviousLocation()
        return extensions.LocationToLatLng(l!!)
    }

    fun getActualLocation(): LatLng {
        return extensions.LocationToLatLng(storage.getLocation()!!)
    }

    fun previousLocationEqualLocation() {
        storage.previousLocationEqualLocation()
    }

    fun addInicalPosition(location: Location?) {
        storage.addInicalPosition(location)
    }

    fun addRoute(previousLocation: Location, actualLocation: Location) {
        storage.addRoute(previousLocation, actualLocation)
    }

    fun getInicialLocation() : Location {
        return storage.getPreviousLocation()!!
    }

    fun getfinalLocation() : Location {
        return storage.getLocation()!!
    }

    fun addFinalPosition(location: Location?) {
        storage.addFinalPosition(location)
    }

    fun getHistoryRouteList(): List<HistoryRoute> {
        return storage.getHistoryRouteList()
    }


}