package com.example.paint.ui.fragments

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Typeface
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.paint.R
import com.example.paint.data.entity.HistoryRoute
import com.example.paint.data.sensors.location.FusedLocation
import com.example.paint.data.sensors.location.OnLocationChangedListener
import com.example.paint.ui.adapters.HistoryListAdapter
import com.example.paint.ui.utils.Extensions
import com.example.paint.ui.viewmodels.viewmodels.MapViewModel
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.fragment_map.view.*
import kotlinx.android.synthetic.main.dialog_history_map.*
import kotlinx.android.synthetic.main.dialog_history_map.view.*
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo2.ui.fragments.PermissionedFragment
import java.io.IOException
import java.util.*

const val REQUEST_CODE = 100

class MapFragment : PermissionedFragment(REQUEST_CODE), OnMapReadyCallback,
    OnLocationChangedListener , GoogleMap.OnPolylineClickListener, GoogleMap.OnPolygonClickListener {

    private lateinit var viewModel: MapViewModel
    private var map: GoogleMap? = null
    private var location: Location? = null
    private var aproximaBoolean = false

    private val extensions = Extensions()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        viewModel = ViewModelProviders.of(this).get(MapViewModel::class.java)
        ButterKnife.bind(this, view)
        view.map_view.onCreate(savedInstanceState)
        return view
    }

    override fun onStart() {
        super.onRequestPermissions(
            activity?.baseContext!!, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
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


    fun adicionaMarker(
        position: LatLng,
        title: String,
        snippet: String,
        icon: BitmapDescriptor
    ) {
        val marker = MarkerOptions()
            .position(position)
            .title(title)
            .snippet(snippet)
            .icon(icon)

        map!!.addMarker(marker)
    }

    override fun onMapReady(map: GoogleMap?) {
        this.map = map
        if (ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity!!, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED ) {
            return
        }
        this.map!!.isMyLocationEnabled = true

        zoom()
        mapInfoWindow()

        // Set listeners for click events.
        map!!.setOnPolylineClickListener(this)
        map.setOnPolygonClickListener(this)
    }

    fun drawPolyline(){
        // Add polylines to the map.
        // Polylines are useful to show a route or some other connection between points.
        val l1 : LatLng = viewModel.getPreviousLocation()
        val l2 : LatLng = viewModel.getActualLocation()
        val polyline1 = map!!.addPolyline(
            PolylineOptions()
                .clickable(true)
                .add(
                    l1,
                    l2,
                )
        )
        // Store a data object with the polyline, used here to indicate an arbitrary type.
        polyline1!!.tag = "B"
        // Style the polyline.
        stylePolyline(polyline1)

        zoom()
    }

    fun drawPolyline2(previousLocation: Location, actualLocation: Location){
        // Add polylines to the map.
        // Polylines are useful to show a route or some other connection between points.
        val l1 : LatLng = extensions.LocationToLatLng(previousLocation)
        val l2 : LatLng = extensions.LocationToLatLng(actualLocation)
        val polyline1 = map!!.addPolyline(
            PolylineOptions()
                .clickable(true)
                .add(
                    l1,
                    l2,
                )
        )
        // Store a data object with the polyline, used here to indicate an arbitrary type.
        polyline1!!.tag = "B"
        // Style the polyline.
        stylePolyline(polyline1)

        zoom()
    }

    private val COLOR_BLACK_ARGB = -0x1000000
    private val POLYLINE_STROKE_WIDTH_PX = 12

    private fun stylePolyline(polyline: Polyline) {
        // Get the data object stored with the polyline.
        val type = polyline.tag?.toString() ?: ""
        when (type) {
            "A" -> {
                // Use a custom bitmap as the cap at the start of the line.
                polyline.startCap = CustomCap(
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_arrow), 10f
                )
            }
            "B" -> {
                // Use a round cap at the start of the line.
                polyline.startCap = RoundCap()
            }
        }
        polyline.endCap = RoundCap()
        polyline.width = POLYLINE_STROKE_WIDTH_PX.toFloat()
        polyline.color = COLOR_BLACK_ARGB
        polyline.jointType = JointType.ROUND
    }

    private val PATTERN_GAP_LENGTH_PX = 20
    private val DOT: PatternItem = Dot()
    private val GAP: PatternItem = Gap(PATTERN_GAP_LENGTH_PX.toFloat())

    // Create a stroke pattern of a gap followed by a dot.
    private val PATTERN_POLYLINE_DOTTED = listOf(GAP, DOT)

    override fun onPolylineClick(polyline: Polyline) {
        // Flip from solid stroke to dotted stroke pattern.
        if (polyline.pattern == null || !polyline.pattern!!.contains(DOT)) {
            polyline.pattern = PATTERN_POLYLINE_DOTTED
        } else {
            // The default pattern is a solid stroke.
            polyline.pattern = null
        }
        Toast.makeText(
            context, "Route type " + polyline.tag.toString(),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onPolygonClick(polygon: Polygon) {
        // Flip the values of the red, green, and blue components of the polygon's color.
        var color = polygon.strokeColor xor 0x00ffffff
        polygon.strokeColor = color
        color = polygon.fillColor xor 0x00ffffff
        polygon.fillColor = color
        Toast.makeText(context, "Area type ${polygon.tag?.toString()}", Toast.LENGTH_SHORT).show()
    }

    private val COLOR_WHITE_ARGB = -0x1
    private val COLOR_GREEN_ARGB = -0xc771c4
    private val COLOR_PURPLE_ARGB = -0x7e387c
    private val COLOR_ORANGE_ARGB = -0xa80e9
    private val COLOR_BLUE_ARGB = -0x657db
    private val POLYGON_STROKE_WIDTH_PX = 8
    private val PATTERN_DASH_LENGTH_PX = 20

    private val DASH: PatternItem = Dash(PATTERN_DASH_LENGTH_PX.toFloat())

    // Create a stroke pattern of a gap followed by a dash.
    private val PATTERN_POLYGON_ALPHA = listOf(GAP, DASH)

    // Create a stroke pattern of a dot followed by a gap, a dash, and another gap.
    private val PATTERN_POLYGON_BETA = listOf(DOT, GAP, DASH, GAP)

    private fun stylePolygon(polygon: Polygon) {
        // Get the data object stored with the polygon.
        val type = polygon.tag?.toString() ?: ""
        var pattern: List<PatternItem>? = null
        var strokeColor = COLOR_BLACK_ARGB
        var fillColor = COLOR_WHITE_ARGB
        when (type) {
            "alpha" -> {
                // Apply a stroke pattern to render a dashed line, and define colors.
                pattern = PATTERN_POLYGON_ALPHA
                strokeColor = COLOR_GREEN_ARGB
                fillColor = COLOR_PURPLE_ARGB
            }
            "beta" -> {
                // Apply a stroke pattern to render a line of dots and dashes, and define colors.
                pattern = PATTERN_POLYGON_BETA
                strokeColor = COLOR_ORANGE_ARGB
                fillColor = COLOR_BLUE_ARGB
            }
        }
        polygon.strokePattern = pattern
        polygon.strokeWidth = POLYGON_STROKE_WIDTH_PX.toFloat()
        polygon.strokeColor = strokeColor
        polygon.fillColor = fillColor
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
            val bitmap = Bitmap.createBitmap(
                intrinsicWidth,
                intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
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

    fun getAdress() : String{
        var addressResult = ""
        val geocoder : Geocoder = Geocoder(this.context, Locale.getDefault())
        try {
            val addresses : List<Address> = geocoder.getFromLocation(
                location!!.latitude,
                location!!.longitude,
                1
            )

            val address = addresses[0].getAddressLine(0)
            val area = addresses[0].locality
            val city = addresses[0].adminArea
            val country = addresses[0].countryName
            val postalCode = addresses[0].postalCode

            addressResult = "$address"
        }catch (e: IOException){
            e.printStackTrace()
        }

        return addressResult
    }

    fun goToMap(l: Location){
        val gmmIntentUri = Uri.parse("google.navigation:q=${l.latitude},${l.longitude}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    @OnClick(R.id.startRouteMap)
    fun onClickStartRouteMap(view: View){
        val iconRed: BitmapDescriptor = BitmapDescriptorFactory.defaultMarker(
            BitmapDescriptorFactory.HUE_RED
        )
        val iconGreen: BitmapDescriptor = BitmapDescriptorFactory.defaultMarker(
            BitmapDescriptorFactory.HUE_GREEN
        )

        if(viewModel.getDrawingMap()){ // true é pk carregou em start e ta a desenhar -> visivel stop drawing
            startRouteMap.text = getString(R.string.start_routing)
            startRouteMap.backgroundTintList = resources.getColorStateList(R.color.mapStartRoute);
            viewModel.setDrawingMap(false)
            viewModel.addFinalPosition(location)
            if (map != null){
                adicionaMarker(
                    LatLng(
                        location!!.latitude.toDouble(),
                        location!!.longitude.toDouble()
                    ), "Stop Drawing", getAdress(), iconRed
                )
            }
        }else { // false é pk esta a espera para desenhar -> visivel start drawing
            startRouteMap.text = getString(R.string.stop_drawing)
            startRouteMap.backgroundTintList = resources.getColorStateList(R.color.mapEndRoute);
            viewModel.previousLocationEqualLocation()
            viewModel.setDrawingMap(true)
            viewModel.addInicalPosition(location)
            if (map != null){
                adicionaMarker(
                    LatLng(
                        location!!.latitude.toDouble(),
                        location!!.longitude.toDouble()
                    ), "Start Drawing", getAdress(), iconGreen
                )
            }
        }
    }

    @OnClick(R.id.historyMap)
    fun onclickHistoryMap(view: View){
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_history_map, null)
        val mBuilder = AlertDialog.Builder(context)
            .setView(dialogView)

        val mAlertDialog = mBuilder.show()

        mAlertDialog.list_history.layoutManager = LinearLayoutManager(activity as Context)
        mAlertDialog.list_history.adapter =
            HistoryListAdapter(
                viewModel,
                activity as Context,
                R.layout.history_route_expression,
                viewModel.getHistoryRouteList() as MutableList<HistoryRoute>,
                this@MapFragment,
                mAlertDialog
            )

        mAlertDialog.historyMap_close.setOnClickListener {
            mAlertDialog.dismiss()
        }

    }

    override fun onLocationChanged(locationResult: LocationResult) {
        val location = locationResult.lastLocation
        this.location = location
        if(viewModel.getDrawingMap()){
            Log.i("drawPolyline", "drawPolyline")
            drawPolyline()
            viewModel.addRoute(viewModel.getInicialLocation(), viewModel.getfinalLocation())
        }
        if (this.location != null) {
            location.let { this.location = it }
            if(!aproximaBoolean && location != null){
                zoom()
                aproximaBoolean = true
            }
        } else if ((this.location!!.latitude != location.latitude) ||
            (this.location!!.longitude != location.longitude)
        ) {
            location.let { this.location = it }
            //zoom()
        }
    }
}