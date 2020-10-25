package com.example.paint.data.local.list

import android.util.Log
import androidx.fragment.app.FragmentManager
import com.example.paint.R

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

}