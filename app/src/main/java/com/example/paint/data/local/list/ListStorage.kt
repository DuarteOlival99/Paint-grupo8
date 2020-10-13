package com.example.paint.data.local.list

import android.util.Log
import com.example.paint.R

class ListStorage private constructor() {

    private var backgroundColor : Int? = null
    private var brushColor = R.color.colorPaint
    private var paintColorDefault = true

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

}