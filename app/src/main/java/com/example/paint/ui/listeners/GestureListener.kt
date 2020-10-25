package com.example.paint.ui.listeners

import android.graphics.Color
import android.util.Log
import android.view.GestureDetector.OnDoubleTapListener
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.paint.R
import com.example.paint.data.local.list.ListStorage
import com.example.paint.ui.fragments.CanvasFragment
import com.example.paint.ui.utils.MyCanvasView
import java.util.*


class GestureListener() : SimpleOnGestureListener(), OnDoubleTapListener {

    private var canvas: MyCanvasView? = null
    private val storage = ListStorage.getInstance()


    fun setCanvas(canvas: MyCanvasView?) {
        this.canvas = canvas
    }

    //SimpleOnGestureListener
    override fun onLongPress(motionEvent: MotionEvent) {
        Log.i("Long Press", "Long Press")
        Toast.makeText(this.canvas?.context, this.canvas?.context?.getString(R.string.cor_pincel_alterada), Toast.LENGTH_SHORT).show()

        val rnd = Random()
        val color: Int = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        storage.setPincelColor(color)
        canvas?.atualizaCorPicenl()
    }


    //OnDoubleTapListener
    override fun onDoubleTap(motionEvent: MotionEvent): Boolean {
        Log.i("double tap", "double tap")
        Toast.makeText(this.canvas?.context, this.canvas?.context?.getString(R.string.cor_canvas_alterada), Toast.LENGTH_SHORT).show()

        val rnd = Random()
        val color: Int = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        storage.setCanvasColor(color)
        canvas?.atualizaCorCanvas()

        return false
    }
}
