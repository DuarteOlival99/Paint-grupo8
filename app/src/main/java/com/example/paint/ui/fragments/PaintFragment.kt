package com.example.paint.ui.fragments

import android.content.Context
import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.paint.R
import com.example.paint.ui.viewmodels.viewmodels.PaintViewModel
import kotlinx.android.synthetic.main.paint_fragment.*
import kotlinx.android.synthetic.main.settings_fragment.*
import yuku.ambilwarna.AmbilWarnaDialog

class PaintFragment : Fragment() {

    private lateinit var viewModel : PaintViewModel
    private var pincelColor = Color.BLACK

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

    override fun onStart() {
        super.onStart()
        //paint.setBackgroundColor(viewModel.getBackgroundColor()!!)
    }

    @OnClick(R.id.button_up)
    fun onClickbuttonUp(view: View){
        palete_vertical.visibility = View.VISIBLE
        button_up.visibility = View.GONE
    }

    @OnClick(R.id.button_down)
    fun onClickbuttonDown(view: View){
        palete_vertical.visibility = View.GONE
        button_up.visibility = View.VISIBLE
    }

    @OnClick(R.id.textView_color_paint)
    fun onClickTextViewColorPaint(view: View) {
        openColorPicker()
    }


    private fun openColorPicker() {
        val colorPicker = AmbilWarnaDialog(context, pincelColor, object : AmbilWarnaDialog.OnAmbilWarnaListener {
            override fun onCancel(dialog: AmbilWarnaDialog) {}
            override fun onOk(dialog: AmbilWarnaDialog, color: Int) {
                pincelColor = color

                viewModel.setPincelColor(pincelColor)

                val pref = PreferenceManager.getDefaultSharedPreferences(context)
                val editor = pref.edit()
                editor
                    .putInt("BACKGROUNDCOLOR", pincelColor)
                    .apply()
            }
        })
        colorPicker.show()
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