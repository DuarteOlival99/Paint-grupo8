package com.example.paint.ui.viewmodels.logic

import com.example.paint.data.local.list.ListStorage

class PaintLogic() {
    private val storage = ListStorage.getInstance()

    fun setBackgroundColor(mDefaultColor: Int) {
        storage.setBackgroundColor(mDefaultColor)
    }

    fun getBackgroundColor(): Int? {
        return storage.getBackgroundColor()
    }

    fun setPincelColor(pincelColor: Int) {
        storage.setPincelColor(pincelColor)
    }

    fun setCanvasColor(canvasColor: Int) {
        storage.setCanvasColor(canvasColor)
    }

    fun getCanvasCriado(): Boolean {
        return storage.getCanvasCriado()
    }

    fun setCanvasCriado(canvas: Boolean){
        storage.setCanvasCriado(canvas)
    }

    fun setPaintFragment(paint: Boolean) {
        storage.setPaintFragment(paint)
    }

    fun getPaintCriado(): Boolean{
        return storage.getPaintCriado()
    }

    fun getCanvasColor(): Int {
        return storage.getCanvasColor()
    }

    fun getPincelEspessura(): Int {
        return storage.getPincelEspessura()
    }

    fun setPincelEspessura(pincelEspessura: Int) {
        storage.setPincelEspessura(pincelEspessura)
    }

}