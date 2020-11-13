package com.example.paint.data.entity

import android.location.Location
import java.util.*

data class Path(
    var posicaoInicial: Location,
    var posicaoFinal: Location
) {

    var uuid: String = UUID.randomUUID().toString()

}