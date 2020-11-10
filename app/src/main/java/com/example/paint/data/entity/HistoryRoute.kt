package com.example.paint.data.entity

import android.location.Location
import java.util.*

data class HistoryRoute(
    var posicaoInicial: Location?,
    var posicaoFinal: Location?,
    var path: List<Route>,

) {

    var uuid: String = UUID.randomUUID().toString()

}