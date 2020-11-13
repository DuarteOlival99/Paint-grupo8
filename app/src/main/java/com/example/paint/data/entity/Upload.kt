package com.example.paint.data.entity

import java.util.*

data class Upload(
    var title: String,
    var url : String
) {

    var uuid: String = UUID.randomUUID().toString()

}