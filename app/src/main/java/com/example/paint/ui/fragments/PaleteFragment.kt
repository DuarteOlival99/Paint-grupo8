package com.example.paint.ui.fragments

import android.graphics.Color
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
import com.example.paint.ui.viewmodels.viewmodels.PaintViewModel
import kotlinx.android.synthetic.main.fragment_palete.*
import yuku.ambilwarna.AmbilWarnaDialog
import java.security.cert.PolicyQualifierInfo


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
                    alteraCorPincel()
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

    fun alteraCorPincel(){
        val fragment = parentFragmentManager.findFragmentById(R.id.paint_canvas_8) as CanvasFragment
        fragment.atualizaCorPicenl()
    }

    fun atualizaPincelEspessura(){
        val fragment = parentFragmentManager.findFragmentById(R.id.paint_canvas_8) as CanvasFragment
        fragment.atualizaPincelEspessura()
    }

    fun alteraCorPincelPalete(cor: Int){
        viewModel.setPincelColor(cor)
        alteraCorPincel()
    }

    @Optional
    @OnClick(R.id.bola_preta)
    fun onClickCorPreta(view: View){
        alteraCorPincelPalete(Color.BLACK)
    }

    @Optional
    @OnClick(R.id.bola_cinzenta)
    fun onClickCorCinzenta(view: View){
        alteraCorPincelPalete(Color.GRAY)
    }

    @Optional
    @OnClick(R.id.bola_amarela)
    fun onClickCorAmarela(view: View){
        alteraCorPincelPalete(Color.YELLOW)
    }

    @Optional
    @OnClick(R.id.bola_vermelha)
    fun onClickCorVermelha(view: View){
        alteraCorPincelPalete(Color.RED)
    }

    @Optional
    @OnClick(R.id.bola_verde)
    fun onClickCorVerde(view: View){
        alteraCorPincelPalete(Color.GREEN)
    }

    @Optional
    @OnClick(R.id.bola_azul)
    fun onClickCorAzul(view: View){
        alteraCorPincelPalete(Color.BLUE)
    }

    //Formas
    @Optional
    @OnClick(R.id.circulo)
    fun onClickCircle(view: View){
        Log.i("onClickCircle", "onClickCircle")
        viewModel.setCircle(true)
        viewModel.setTriangle(false)
        viewModel.setSquare(false)
        circulo.visibility = View.GONE
        circulo_x.visibility = View.VISIBLE

        triangulo.visibility = View.VISIBLE
        triangulo_x.visibility = View.GONE
        quadrado.visibility = View.VISIBLE
        quadrado_x.visibility = View.GONE

    }

    @Optional
    @OnClick(R.id.circulo_x)
    fun onClickCircleX(view: View){
        setClickedFalse()
        setSquareVisibility()
        setCircleVisibility()
        setTriangleVisibility()
    }

    @Optional
    @OnClick(R.id.triangulo)
    fun onClickTriangle(view: View){
        viewModel.setCircle(false)
        viewModel.setTriangle(true)
        viewModel.setSquare(false)
        triangulo.visibility = View.GONE
        triangulo_x.visibility = View.VISIBLE

        circulo.visibility = View.VISIBLE
        circulo_x.visibility = View.GONE
        quadrado.visibility = View.VISIBLE
        quadrado_x.visibility = View.GONE
    }

    @Optional
    @OnClick(R.id.triangulo_x)
    fun onClickTriangleX(view: View){
        setClickedFalse()
        setSquareVisibility()
        setCircleVisibility()
        setTriangleVisibility()
    }

    @Optional
    @OnClick(R.id.quadrado)
    fun onClickSquare(view: View){
        viewModel.setCircle(false)
        viewModel.setTriangle(false)
        viewModel.setSquare(true)
        quadrado.visibility = View.GONE
        quadrado_x.visibility = View.VISIBLE

        triangulo.visibility = View.VISIBLE
        triangulo_x.visibility = View.GONE
        circulo.visibility = View.VISIBLE
        circulo_x.visibility = View.GONE
    }

    @Optional
    @OnClick(R.id.quadrado_x)
    fun onClickSquareX(view: View){
        setClickedFalse()
        setSquareVisibility()
        setCircleVisibility()
        setTriangleVisibility()
    }

    fun setClickedFalse(){
        viewModel.setCircle(false)
        viewModel.setTriangle(false)
        viewModel.setSquare(false)
    }

    fun setSquareVisibility(){
        quadrado.visibility = View.VISIBLE
        quadrado_x.visibility = View.GONE
    }

    fun setCircleVisibility(){
        circulo.visibility = View.VISIBLE
        circulo_x.visibility = View.GONE
    }

    fun setTriangleVisibility(){
        triangulo.visibility = View.VISIBLE
        triangulo_x.visibility = View.GONE
    }




}