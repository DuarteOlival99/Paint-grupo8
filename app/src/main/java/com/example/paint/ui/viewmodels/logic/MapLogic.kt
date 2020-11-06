package com.example.paint.ui.viewmodels.logic

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


}