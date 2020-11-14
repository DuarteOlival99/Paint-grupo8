package com.example.paint.ui.adapters

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.paint.R
import com.example.paint.data.entity.HistoryCanvas
import com.example.paint.domain.project.MainActivity
import com.example.paint.ui.fragments.CanvasFragment
import com.example.paint.ui.fragments.MapFragment
import com.example.paint.ui.viewmodels.viewmodels.PaintViewModel
import kotlinx.android.synthetic.main.history_canvas_expression.view.*
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class HistoryCanvasListAdapter(
    private var viewModel: PaintViewModel,
    private val context: Context,
    private val layout: Int,
    private var
    listHistoryCanvas: MutableList<HistoryCanvas>,
    val ativity: MainActivity,
    val dialog: AlertDialog
) :
    RecyclerView.Adapter<HistoryCanvasListAdapter.HistoryCanvasListViewHolder>(){

    class HistoryCanvasListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageTitle: TextView = view.image_canvas_title
        val image: ImageView = view.imageView_history_canvas
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
        //holder.image.setImageBitmap(listHistoryCanvas[position].image)
        holder.imageTitle.text = listHistoryCanvas[position].title
        Glide.with(holder.image).load(listHistoryCanvas[position].url).into(holder.itemView.imageView_history_canvas)

        val item = holder.itemView

        item.cardView_canvas_history.setOnClickListener{
            ativity.setCanvasImage((listHistoryCanvas[position].url))
            dialog.dismiss()
        }


    }

}