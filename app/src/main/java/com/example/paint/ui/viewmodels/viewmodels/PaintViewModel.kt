package com.example.paint.ui.viewmodels.viewmodels

import android.app.Application
import android.graphics.Color
import android.util.Log
import androidx.annotation.ColorRes
import androidx.lifecycle.AndroidViewModel
import com.example.paint.ui.listeners.OnColorChange
import com.example.paint.ui.viewmodels.logic.PaintLogic


class PaintViewModel (application: Application ) : AndroidViewModel (application) {
    private val paintLogic = PaintLogic()

    private var cor = Color.BLACK


    fun getCanvasColor() : Int {
        return paintLogic.getCanvasColor()
    }

    fun getBackgroundColor(): Int? {
        return paintLogic.getBackgroundColor()
    }

    fun setPincelColor(pincelColor: Int) {
        paintLogic.setPincelColor(pincelColor)
    }

    fun setCanvasColor(canvasColor: Int) {
        paintLogic.setCanvasColor(canvasColor)
    }

    fun getCanvasCriado(): Boolean {
        return paintLogic.getCanvasCriado()
    }

    fun setCanvasCriado(canvas: Boolean) {
        paintLogic.setCanvasCriado(canvas)
    }

    fun setPaintFragment(paint: Boolean) {
        paintLogic.setPaintFragment(paint)
    }

    fun getPaintCriado(): Boolean{
        return paintLogic.getPaintCriado()
    }

    fun getPincelEspessura(): Int {
        return paintLogic.getPincelEspessura()
    }

    fun setPincelEspessura(pincelEspessura: Int) {
        paintLogic.setPincelEspessura(pincelEspessura)
    }
}