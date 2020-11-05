package com.example.paint.ui.fragments

import android.Manifest
import android.content.Context

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Typeface
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import com.example.paint.R
import com.example.paint.data.sensors.location.FusedLocation
import com.example.paint.data.sensors.location.OnLocationChangedListener
import com.example.paint.ui.utils.Extensions
import com.example.paint.ui.viewmodels.viewmodels.MapViewModel
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.fragment_map.view.*
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo2.ui.fragments.PermissionedFragment

const val REQUEST_CODE = 100

class MapFragment : PermissionedFragment(REQUEST_CODE), OnMapReadyCallback,
    OnLocationChangedListener {

    private lateinit var viewModel: MapViewModel
    private var map: GoogleMap? = null
    private var location: Location? = null

    private val extensions = Extensions()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        viewModel = ViewModelProviders.of(this).get(MapViewModel::class.java)
        ButterKnife.bind(this,view)
        view.map_view.onCreate(savedInstanceState)
        return view
    }

    override fun onStart() {
        super.onRequestPermissions(activity?.baseContext!!, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION))
        super.onStart()
    }

    override fun onDestroy() {
        Log.i("onDestroy_map", "evocado")
        super.onDestroy()
    }


    override fun onRequestPermissionSucces() {
        FusedLocation.registerListener(this)
        map_view.getMapAsync(this)
        map_view.onResume()
        zoom()    }

    override fun onRequestPermissionFailure() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FusedLocation.start(activity!!.applicationContext)
    }


    private fun adicionaMarker(position: LatLng, title: String, snippet: String, icon: BitmapDescriptor) {
        val marker = MarkerOptions()
            .position(position)
            .title(title)
            .snippet(snippet)
            .icon(icon)

        map!!.addMarker(marker)
    }

    override fun onMapReady(map: GoogleMap?) {
        this.map = map
        if (ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity!!, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            return
        }
        this.map!!.isMyLocationEnabled = true
        zoom()
        mapInfoWindow()
    }

    private fun mapInfoWindow(){
        map!!.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            override fun getInfoWindow(arg0: Marker?): View? {
                return null
            }

            override fun getInfoContents(marker: Marker): View? {
                val info = LinearLayout(context)
                info.orientation = LinearLayout.VERTICAL
                val title = TextView(context)
                title.setTextColor(Color.BLACK)
                title.gravity = Gravity.CENTER
                title.setTypeface(null, Typeface.BOLD)
                title.text = marker.title
                val snippet = TextView(context)
                snippet.setTextColor(Color.GRAY)
                snippet.text = marker.snippet
                info.addView(title)
                info.addView(snippet)
                return info
            }
        })
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    private fun zoom() {
        if (location != null) {
            map!!.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    extensions.LocationToLatLng(location!!), 13f
                )
            )
            val cameraPosition = CameraPosition.Builder()
                .target(
                    extensions.LocationToLatLng(location!!)
                ) // Sets the center of the map to location user
                .zoom(17f) // Sets the zoom
                .bearing(90f) // Sets the orientation of the camera to east
                .tilt(40f) // Sets the tilt of the camera to 30 degrees
                .build() // Creates a CameraPosition from the builder
            map!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }

    override fun onLocationChanged(locationResult: LocationResult) {
        val location = locationResult.lastLocation
        this.location = location
        if (this.location == null) {
            location.let { this.location = it }
            zoom()
        } else if ((this.location!!.latitude != location.latitude) ||
            (this.location!!.longitude != location.longitude)
        ) {
            location.let { this.location = it }
            zoom()
        }
    }
}