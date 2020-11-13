package com.example.paint.ui.adapters

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.media.Image
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paint.R
import com.example.paint.data.entity.HistoryCanvas
import com.example.paint.data.entity.HistoryRoute
import com.example.paint.ui.fragments.MapFragment
import com.example.paint.ui.viewmodels.viewmodels.MapViewModel
import com.example.paint.ui.viewmodels.viewmodels.PaintViewModel
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.dialog_save_canvas.view.*
import kotlinx.android.synthetic.main.history_canvas_expression.view.*
import kotlinx.android.synthetic.main.history_route_expression.view.*
import java.io.IOException
import java.util.*

class HistoryCanvasListAdapter(
    private var viewModel: PaintViewModel,
    private val context: Context,
    private val layout: Int,
    private var
    listHistoryCanvas: MutableList<HistoryCanvas>,
) :
    RecyclerView.Adapter<HistoryCanvasListAdapter.HistoryCanvasListViewHolder>(){

    class HistoryCanvasListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.imageView_history_canvas
        val imageTitle: TextView = view.editText
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryCanvasListViewHolder {
            return HistoryCanvasListViewHolder(
                LayoutInflater.from(context).inflate(layout, parent, false)
            )
    }

    override fun getItemCount(): Int {
      return listHistoryCanvas.size
    }

    override fun onBindViewHolder(holder: HistoryCanvasListViewHolder, position: Int) {
        holder.image.setImageBitmap(listHistoryCanvas[position].image)
        holder.imageTitle.text = listHistoryCanvas[position].title



    }

}