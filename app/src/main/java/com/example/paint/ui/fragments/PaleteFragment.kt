package com.example.paint.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Optional
import com.example.paint.R
import com.example.paint.ui.listeners.OnColorChange
import com.example.paint.ui.viewmodels.viewmodels.PaintViewModel
import yuku.ambilwarna.AmbilWarnaDialog


class PaleteFragment : Fragment() {
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
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    @Optional
    @OnClick(R.id.textView_brush_paint)
    fun onClickTextViewBrushPaint(view: View){
        fragmentManager?.let { PincelChangeDialogFragmentHorizontal().show(
            childFragmentManager,
            "pincel change"
        ) }
    }

    @Optional
    @OnClick(R.id.textView_color_paint)
    fun onClickTextViewColorPaintPincel(view: View) {
        openColorPickerPincel()
    }
    private fun openColorPickerPincel() {
        val colorPicker = AmbilWarnaDialog(
            context,
            pincelColor,
            object : AmbilWarnaDialog.OnAmbilWarnaListener {
                override fun onCancel(dialog: AmbilWarnaDialog) {}
                override fun onOk(dialog: AmbilWarnaDialog, color: Int) {
                    pincelColor = color

                    viewModel.setPincelColor(pincelColor)
                    alteraCorPaint()
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
        val colorPicker = AmbilWarnaDialog(
            context,
            canvasColor,
            object : AmbilWarnaDialog.OnAmbilWarnaListener {
                override fun onCancel(dialog: AmbilWarnaDialog) {}
                override fun onOk(dialog: AmbilWarnaDialog, color: Int) {
                    canvasColor = color

                    viewModel.setCanvasColor(canvasColor)
                    alteraCorCanvas()
                }
            })
        colorPicker.show()
    }

    fun alteraCorCanvas(){
        val fragment = parentFragmentManager.findFragmentById(R.id.paint_canvas_8) as CanvasFragment
        fragment.atualizaCorCanvas()
    }

    fun alteraCorPaint(){
        val fragment = parentFragmentManager.findFragmentById(R.id.paint_canvas_8) as CanvasFragment
        fragment.atualizaCorPicenl()
    }

    fun atualizaPincelEspessura(){
        val fragment = parentFragmentManager.findFragmentById(R.id.paint_canvas_8) as CanvasFragment
        fragment.atualizaPincelEspessura()
    }

    @OnClick(R.id.bola_preta)
    fun onClickCorPreta(view: View){
        //TODO
    }

    @OnClick(R.id.bola_cinzenta)
    fun onClickCorCinzenta(view: View){

    }

    @OnClick(R.id.bola_amarela)
    fun onClickCorAmarela(view: View){

    }

    @OnClick(R.id.bola_vermelha)
    fun onClickCorVermelha(view: View){

    }

    @OnClick(R.id.bola_verde)
    fun onClickCorVerde(view: View){

    }

    @OnClick(R.id.bola_azul)
    fun onClickCorAzul(view: View){

    }

/*    override fun onColorChange(color: Int) {
        Log.i("Palete", "palete")
       //canvasFragment.atualizaCorCanvas()
    }*/
}