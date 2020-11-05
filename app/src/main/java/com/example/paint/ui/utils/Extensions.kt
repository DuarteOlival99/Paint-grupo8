package com.example.paint.ui.utils

import android.location.Location
import com.google.android.gms.maps.model.LatLng

class Extensions { //Ficha 10

    fun LocationToLatLng(location: Location) : LatLng {
        return LatLng(location.latitude, location.longitude)
    }

    fun distanceBetweenLatLng(LatLng1Latitude: Double , LatLng1Longitude: Double, LatLng2Latitude: Double , LatLng2Longitude: Double): Float {

        val results = FloatArray(1)
        Location.distanceBetween(    //	distanceBetween(double startLatitude, double startLongitude, double endLatitude, double endLongitude, float[] results)
            LatLng1Latitude, LatLng1Longitude,
            LatLng2Latitude, LatLng2Longitude,
            results
        )
        results to Float
        return metroToKm(results[0])
    }

    fun metroToKm (distance: Float) : Float {

        if (distance > 1000.0f) {
            return distance / 1000.0f
        } else {
            return distance / 1000.0f
        }

    }


}