package com.example.paint.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.paint.R
import com.example.paint.ui.listeners.OnColorChange
import com.example.paint.ui.utils.MyCanvasView
import com.example.paint.ui.viewmodels.viewmodels.AboutViewModel
import com.example.paint.ui.viewmodels.viewmodels.PaintViewModel


class CanvasFragment : Fragment(){

    private lateinit var viewModel : PaintViewModel
    private lateinit var canvasView: MyCanvasView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        canvasView = MyCanvasView(activity!!.applicationContext)
        // myCanvasView.systemUiVisibility = SYSTEM_UI_FLAG_FULLSCREEN
        canvasView.contentDescription = getString(R.string.canvasContentDescription)
        viewModel = ViewModelProviders.of(this).get(PaintViewModel::class.java)

        return canvasView
    }

    override fun onStart() {
        //viewModel.registerListener(this)
        //viewModel.setCanvasCriado(true)
        super.onStart()
    }

    override fun onDestroy() {
        //viewModel.setCanvasCriado(false)
        super.onDestroy()
    }


    fun atualizaCorPicenl() {
        canvasView.atualizaCorPicenl()
    }

    fun atualizaCorCanvas() {
        if (viewModel.getCanvasCriado()){
            canvasView.atualizaCorCanvas()
        }
    }

/*    override fun onColorChange(color: Int) {
        Log.i("atualizou", "OK")
        if (viewModel.getCanvasCriado()){
            atualizaCorCanvas()
        }
    }*/

}