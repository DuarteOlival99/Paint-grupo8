package com.example.paint.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Optional
import com.example.paint.R
import com.example.paint.ui.listeners.OnColorChange
import com.example.paint.ui.viewmodels.viewmodels.PaintViewModel
import yuku.ambilwarna.AmbilWarnaDialog


class PaleteFragment : Fragment(), OnColorChange {
    private lateinit var viewModel : PaintViewModel

    private var pincelColor = R.color.colorPaint
    private var canvasColor = R.color.colorBackground

    val canvasFragment : CanvasFragment = CanvasFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_palete, container, false)
        viewModel = ViewModelProviders.of(this).get(PaintViewModel::class.java)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onStart() {
        viewModel.registerListener(this)
        super.onStart()
    }


    @Optional
    @OnClick(R.id.textView_brush_paint)
    fun onClickTextViewBrushPaint(view: View){
        fragmentManager?.let { PincelChangeDialogFragment().show(childFragmentManager, "pincel change") }
    }

    @Optional
    @OnClick(R.id.textView_color_paint)
    fun onClickTextViewColorPaintPincel(view: View) {
        openColorPickerPincel()
    }
    private fun openColorPickerPincel() {
        val colorPicker = AmbilWarnaDialog(context, pincelColor, object : AmbilWarnaDialog.OnAmbilWarnaListener {
            override fun onCancel(dialog: AmbilWarnaDialog) {}
            override fun onOk(dialog: AmbilWarnaDialog, color: Int) {
                pincelColor = color

                viewModel.setPincelColor(pincelColor)
                canvasFragment.atualizaCorPicenl()
            }
        })
        colorPicker.show()
    }

    @Optional
    @OnClick(R.id.textView_color_paint_canvas)
    fun onClickTextViewColorPaintCanvas(view: View) {
        openColorPickerCanvas()
    }
    private fun openColorPickerCanvas() {
        val colorPicker = AmbilWarnaDialog(context, canvasColor, object : AmbilWarnaDialog.OnAmbilWarnaListener {
            override fun onCancel(dialog: AmbilWarnaDialog) {}
            override fun onOk(dialog: AmbilWarnaDialog, color: Int) {
                canvasColor = color

                viewModel.setCanvasColor(canvasColor)
                viewModel.changeColor(canvasColor)
                //canvasFragment.atualizaCorCanvas()
            }
        })
        colorPicker.show()
    }

    override fun onColorChange(color: Int) {
       // canvasFragment.atualizaCorCanvas()
    }
}