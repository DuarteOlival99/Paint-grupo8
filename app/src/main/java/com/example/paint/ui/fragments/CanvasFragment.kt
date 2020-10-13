package com.example.paint.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.paint.R
import com.example.paint.ui.utils.MyCanvasView
import com.example.paint.ui.viewmodels.viewmodels.AboutViewModel


class CanvasFragment : Fragment() {

    private lateinit var viewModel : AboutViewModel
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

        return canvasView
    }

    fun teste() {
        canvasView.teste()
    }


}