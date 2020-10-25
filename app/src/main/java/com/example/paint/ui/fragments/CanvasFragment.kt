package com.example.paint.ui.fragments

import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import butterknife.OnClick
import com.example.paint.R
import com.example.paint.ui.listeners.GestureListener
import com.example.paint.ui.utils.MyCanvasView
import com.example.paint.ui.viewmodels.viewmodels.PaintViewModel


class CanvasFragment : Fragment(){

    private lateinit var viewModel : PaintViewModel
    private lateinit var canvasView: MyCanvasView
    private lateinit var gd : GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val mGestureListener = GestureListener()
        val mGestureDetector = GestureDetector(activity!!.applicationContext, mGestureListener)
        mGestureDetector.setIsLongpressEnabled(true)
        mGestureDetector.setOnDoubleTapListener(mGestureListener)

        canvasView = MyCanvasView(activity!!.applicationContext, mGestureDetector)
        mGestureListener.setCanvas(canvasView)

        // myCanvasView.systemUiVisibility = SYSTEM_UI_FLAG_FULLSCREEN
        canvasView.contentDescription = getString(R.string.canvasContentDescription)
        viewModel = ViewModelProviders.of(this).get(PaintViewModel::class.java)

        return canvasView
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    fun atualizaCorPicenl() {
        canvasView.atualizaCorPicenl()
    }

    fun atualizaCorCanvas() {
        canvasView.atualizaCorCanvas()
    }

    fun atualizaPincelEspessura() {
        canvasView.atualizaPincelEspessura()
    }

}