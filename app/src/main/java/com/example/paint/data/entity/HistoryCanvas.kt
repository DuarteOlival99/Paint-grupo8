package com.example.paint.data.entity

import android.graphics.Bitmap
import android.location.Location
import java.util.*

data class HistoryCanvas(
    var title: String,
   // var image : Bitmap,
    var url : String
) {

    var uuid: String = UUID.randomUUID().toString()

}