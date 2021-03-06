package com.example.paint.ui.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Optional
import com.example.paint.R
import com.example.paint.ui.viewmodels.viewmodels.PaintViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.paint_fragment.*
import yuku.ambilwarna.AmbilWarnaDialog


class PaintFragment : Fragment(){

    private lateinit var viewModel : PaintViewModel
    private var pincelColor = R.color.colorPaint
    private var canvasColor = R.color.colorBackground
    val canvasFragment : CanvasFragment = CanvasFragment()
    val paleteFragment : PaleteFragment = PaleteFragment()

    private var mFirebaseStorage = FirebaseStorage.getInstance()
    private var mStorageRef: StorageReference? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.paint_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(PaintViewModel::class.java)
        ButterKnife.bind(this, view)

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mStorageRef = FirebaseStorage.getInstance().reference;

        if((activity as AppCompatActivity).resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            // Portrait
            Log.i("Portait", "Portait")
            val manager : FragmentManager = (activity as AppCompatActivity).supportFragmentManager
            manager.beginTransaction()
                .replace(R.id.paint_canvas_8, canvasFragment, canvasFragment.tag)
                .commit()

        } else {
            // Landscape Mode
            Log.i("Land", "Land")
            val manager : FragmentManager = (activity as AppCompatActivity).supportFragmentManager

            manager.beginTransaction()
                .replace(R.id.paint_canvas_8, canvasFragment, canvasFragment.tag)
                .commit()

            manager.beginTransaction()
                .replace(R.id.paint_canvas_2, paleteFragment, paleteFragment.tag)
                .commit()

        }
    }

   override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    @Optional
    @OnClick(R.id.textView_clean)
    fun onClickTextViewTeste(view: View){

        Toast.makeText(context, "Clean screen", Toast.LENGTH_SHORT).show();
        val fragment =
            parentFragmentManager.findFragmentById(R.id.paint_canvas_8) as CanvasFragment
        fragment.cleanScreen()

    }


    @Optional
    @OnClick(R.id.button_up)
    fun onClickbuttonUp(view: View){
        palete_vertical?.visibility = View.VISIBLE
        button_up?.visibility = View.GONE
    }

    @Optional
    @OnClick(R.id.button_down)
    fun onClickbuttonDown(view: View){
        palete_vertical?.visibility = View.GONE
        button_up?.visibility = View.VISIBLE
    }

    @Optional
    @OnClick(R.id.textView_brush_paint)
    fun onClickTextViewBrushPaint(view: View){
        fragmentManager?.let { PincelChangeDialogFragment().show(
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
                    canvasFragment.atualizaCorPicenl()
                }
            })
        colorPicker.show()
    }

    @Optional
    @OnClick(R.id.textView_color_paint_canvas_vertical)
    fun onClickTextViewColorPaintCanvas(view: View) {
        Log.i("cor muda", "openColorPicker")
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
                    atualizaCanvas()
                }
            })
        colorPicker.show()
    }

    fun atualizaCanvas(){
        Log.i("atualiza cor canvas", " ")
        canvasFragment.atualizaCorCanvas()
    }

    fun atualizaPincelEspessura(){
        canvasFragment.atualizaPincelEspessura()
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.pincel_menu, menu);
//        return super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val id = item.itemId;
//        when(item.itemId) {
//            R.id.pincel_menu -> {
//                Toast.makeText(activity as Context,"Tapped on icon",Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        return super.onOptionsItemSelected(item)
//    }

}