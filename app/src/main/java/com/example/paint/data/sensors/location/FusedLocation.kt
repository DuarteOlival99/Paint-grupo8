package com.example.paint.data.sensors.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.paint.data.local.list.ListStorage
import com.google.android.gms.location.*


class FusedLocation private constructor(context: Context) : LocationCallback() {
        private val TAG = FusedLocation::class.java.simpleName
        // Intervalos de tempo em que a localização é verificada, 2 segundos
        private val TIME_BETWEEN_UPDATES = 2000L
        // Este atributo é utilizado para configurar os pedidos de localização
        private var locationRequest: LocationRequest? = null
        // Este atributo será utilizado para acedermos à API da Fused Location
        private var client = FusedLocationProviderClient(context)
        private val storage = ListStorage.getInstance()


    init {
        // Configurar a precisão e os tempos entre atualizações da localização
        locationRequest = LocationRequest()
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest?.interval = TIME_BETWEEN_UPDATES
        // Instanciar o objeto que permite definir as configurações
        val locationSettingsRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest!!)
            .build()
        // Aplicar as configurações ao serviço de localização
        LocationServices.getSettingsClient(context)
            .checkLocationSettings(locationSettingsRequest)
    }

    companion object {
        private var listener: OnLocationChangedListener? = null
        private var instance: FusedLocation? = null

        fun registerListener(listener: OnLocationChangedListener) {
            Companion.listener = listener
        }

        fun unregisterListener() {
            listener = null
        }

        fun notifyListeners(locationResult: LocationResult) {
            listener?.onLocationChanged(locationResult)
        }

        fun start(context: Context) {
            instance = if (instance == null)
                FusedLocation(context)
            else instance
            instance?.startLocationUpdates(context)
        }
    }
    private fun startLocationUpdates(context: Context) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        client.requestLocationUpdates(locationRequest, this, Looper.myLooper())
    }

    override fun onLocationResult(locationResult: LocationResult?) {
        Log.i(TAG,locationResult?.lastLocation.toString())
        storage.updateLocation(locationResult!!.lastLocation)
        locationResult?.let { notifyListeners(it) }
        super.onLocationResult(locationResult)
    }
}



