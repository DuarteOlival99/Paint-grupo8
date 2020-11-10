package com.example.paint.ui.adapters

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.paint.R
import com.example.paint.data.entity.HistoryRoute
import com.example.paint.ui.fragments.MapFragment
import com.example.paint.ui.viewmodels.viewmodels.MapViewModel
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.history_route_expression.view.*
import java.io.IOException
import java.util.*

class HistoryListAdapter(
    private var viewModel: MapViewModel,
    private val context: Context,
    private val layout: Int,
    private var
    listHistoryRoute: MutableList<HistoryRoute>,
    val fragment: MapFragment,
    val dialog: AlertDialog
) :
    RecyclerView.Adapter<HistoryListAdapter.HistoryListViewHolder>(){

    class HistoryListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val origem: TextView = view.posicao_inicial_text
        val destino: TextView = view.posicao_final_text
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryListViewHolder {
            return HistoryListViewHolder(
                LayoutInflater.from(context).inflate(layout, parent, false)
            )
    }

    override fun getItemCount(): Int {
      return listHistoryRoute.size
    }

    override fun onBindViewHolder(holder: HistoryListViewHolder, position: Int) {
        holder.origem.text = getAdress(listHistoryRoute[position].posicaoInicial!!)
        holder.destino.text = getAdress(listHistoryRoute[position].posicaoFinal!!)
        val item = holder.itemView

        item.posicao_inicial_text.paint.isUnderlineText = true
        item.posicao_final_text.paint.isUnderlineText = true

        item.cardView_history.setOnClickListener {
            //Toast.makeText(context, "carregou no historico", Toast.LENGTH_LONG).show()
            val iconRed: BitmapDescriptor = BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_RED
            )
            val iconGreen: BitmapDescriptor = BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_GREEN
            )

            fragment.adicionaMarker(
                LatLng(
                    listHistoryRoute[position].posicaoInicial!!.latitude.toDouble(),
                    listHistoryRoute[position].posicaoInicial!!.longitude.toDouble()
                ), "Start Drawing", getAdress(listHistoryRoute[position].posicaoInicial!!), iconGreen
            )

            for (p in listHistoryRoute[position].path){
                fragment.drawPolyline2(p.posicaoInicial, p.posicaoFinal)
            }

            fragment.adicionaMarker(
                LatLng(
                    listHistoryRoute[position].posicaoFinal!!.latitude.toDouble(),
                    listHistoryRoute[position].posicaoFinal!!.longitude.toDouble()
                ), "Stop Drawing", getAdress(listHistoryRoute[position].posicaoFinal!!), iconRed
            )

            dialog.dismiss()
        }

        item.posicao_inicial_text.setOnClickListener{
            fragment.goToMap(listHistoryRoute[position].posicaoInicial!!)
        }

        item.posicao_final_text.setOnClickListener{
            fragment.goToMap(listHistoryRoute[position].posicaoFinal!!)
        }

    }

    fun getAdress(location: Location) : String{
        var addressResult = ""
        val geocoder : Geocoder = Geocoder(this.context, Locale.getDefault())
        try {
            val addresses : List<Address> = geocoder.getFromLocation(
                location!!.latitude,
                location!!.longitude,
                1
            )

            val address = addresses[0].getAddressLine(0)
            val area = addresses[0].locality
            val city = addresses[0].adminArea
            val country = addresses[0].countryName
            val postalCode = addresses[0].postalCode

            addressResult = "$address"
        }catch (e: IOException){
            e.printStackTrace()
        }

        return addressResult
    }



}