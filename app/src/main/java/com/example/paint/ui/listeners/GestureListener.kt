package com.example.paint.ui.listeners

import android.content.Context
import android.util.Log
import android.view.GestureDetector.OnDoubleTapListener
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.widget.Toast
import com.example.paint.ui.utils.MyCanvasView


class GestureListener() : SimpleOnGestureListener(), OnDoubleTapListener {

    private var canvas: MyCanvasView? = null


    fun setCanvas(canvas: MyCanvasView?) {
        this.canvas = canvas
    }

    ////////SimpleOnGestureListener
    override fun onLongPress(motionEvent: MotionEvent) {
        Log.i("Long Press", "Long Press")
        Toast.makeText(this.canvas?.context,"Long Press", Toast.LENGTH_SHORT).show()

        //TODO escolher o que fazer

    }

    /////////OnDoubleTapListener
    override fun onDoubleTap(motionEvent: MotionEvent): Boolean {
        Log.i("double tap", "double tap")
        Toast.makeText(this.canvas?.context,"Double Click", Toast.LENGTH_SHORT).show()

        //TODO mudar a cor do background para uma cor random

        return false
    }
}
