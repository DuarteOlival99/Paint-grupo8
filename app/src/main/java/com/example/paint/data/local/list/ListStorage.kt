package com.example.paint.data.local.list

import android.location.Location
import android.util.Log
import com.example.paint.R
import com.example.paint.data.entity.HistoryRoute
import com.example.paint.data.entity.Route
import com.google.android.gms.maps.model.LatLng

class ListStorage private constructor() {

    private var backgroundColor : Int? = null
    private var brushColor = R.color.colorPaint
    private var paintColorDefault = true
    private var paintColorCanvas = true
    private var paintPincelEspessuraDefault = true
    private var canvasColor = R.color.colorBackground
    private var canvasCriado = false
    private var paintCriado = false
    private var pincelEspessura = 12

    private var drawCircle = false
    private var drawTriangle = false
    private var drawSquare = false

    private var darkModeBoolean = false
    private var darkModeAutomatico = true

    private var DrawingMap = false

    private var location: Location? = null
    private var previousLocation : Location? = null
    private var historyRoute = mutableListOf<HistoryRoute>()
    private var provisionalPositionInicial : Location? = null
    private var provisionalPositionfinal : Location? = null
    private var provisionalRoutelist : List<Route> = emptyList()

    companion object {

        private var instance: ListStorage? = null

        fun getInstance(): ListStorage {
            synchronized(this) {
                if (instance == null) {
                    instance =
                        ListStorage()
                }
                return instance as ListStorage
            }
        }

    }

    fun setBackgroundColor(mDefaultColor: Int) {
        backgroundColor = mDefaultColor
    }

    fun getBackgroundColor(): Int? {
        return backgroundColor
    }

    fun setPincelColor(pincelColor: Int) {
        brushColor = pincelColor
        paintColorDefault = false
        Log.i("color storage set", brushColor.toString())
    }

    fun getPincelColor(): Int {
        Log.i("color storage return", brushColor.toString())
        return brushColor
    }

    fun getDefaultPincelColor(): Boolean {
        return paintColorDefault
    }

    fun setCanvasColor(canvasColor: Int) {
       this.canvasColor = canvasColor
        paintColorCanvas = false
    }

    fun getCanvasColor(): Int {
        return canvasColor
    }

    fun getDefaultCanvasColor(): Boolean {
        return paintColorCanvas
    }

    fun setCanvasCriado(canvas: Boolean){
        canvasCriado = canvas
    }

    fun getCanvasCriado(): Boolean{
        return canvasCriado
    }

    fun setPaintFragment(paint: Boolean) {
        paintCriado = paint
    }
    fun getPaintCriado(): Boolean{
        return paintCriado
    }

    fun getPincelEspessura(): Int {
        return pincelEspessura
    }

    fun getDefaultPincelEspessuraColor() : Boolean {
        return paintPincelEspessuraDefault
    }

    fun setPincelEspessura(pincelEspessura : Int) {
        this.pincelEspessura = pincelEspessura
        paintPincelEspessuraDefault = false
    }

    fun getCircle(): Boolean{
        return drawCircle
    }
    fun setCircle(b: Boolean) {
        drawCircle = b
    }

    fun getTriangle(): Boolean{
        return drawTriangle
    }
    fun setTriangle(b: Boolean) {
        drawTriangle = b
    }

    fun getSquare(): Boolean{
        return drawSquare
    }
    fun setSquare(b: Boolean) {
        drawSquare = b
    }

    fun setDarkModeBoolean(b: Boolean) {
        darkModeBoolean = b
    }

    fun getDarkModeBoolean() : Boolean {
        return darkModeBoolean
    }

    fun setDarkModeAutomatico(b: Boolean) {
        darkModeAutomatico = b
    }

    fun getDarkModeAutomatico(): Boolean {
        return darkModeAutomatico
    }

    fun updateLocation(lastLocation: Location?) {
        location = lastLocation
    }

    fun getLocation(): Location? {
        return location
    }

    fun updatePreviousLocation() {
        previousLocation = location
    }

    fun getPreviousLocation(): Location? {
        return previousLocation
    }

    fun getDrawingMap(): Boolean {
        return DrawingMap
    }

    fun setDrawingMap(b: Boolean) {
        DrawingMap = b
    }

    fun previousLocationEqualLocation() {
        previousLocation = location
    }

    fun addInicalPosition(location: Location?) {
        provisionalPositionInicial = location
        provisionalPositionfinal= null
        provisionalRoutelist = emptyList()
    }

    fun addFinalPosition(location: Location?) {
        provisionalPositionfinal = location

        val provisionalRoute : HistoryRoute = HistoryRoute(provisionalPositionInicial, provisionalPositionfinal, provisionalRoutelist)
        historyRoute.add(provisionalRoute)

        Log.i("provisionalRoute", provisionalRoute.toString())
        Log.i("historyRoute", historyRoute.toString())

        for(item in historyRoute){
            Log.i("posicaoInicial: ", item.posicaoInicial.toString())
            Log.i("posicaoFinal: ", item.posicaoFinal.toString())
        }
    }

    fun addRoute(previousLocation: Location, actualLocation: Location) {
        if(provisionalRoutelist.isEmpty()){
            var list : MutableList<Route> = mutableListOf()
            val path : Route = Route(previousLocation, actualLocation)
            list.add(path)
            provisionalRoutelist = list
        }else{
            var list : MutableList<Route> = provisionalRoutelist as MutableList<Route>
            val path : Route = Route(previousLocation, actualLocation)
            list.add(path)
            provisionalRoutelist = list
        }

        Log.i("HistoryList", provisionalRoutelist.toString())
    }

    fun getHistoryRouteList() : List<HistoryRoute>{
        return historyRoute
    }

}