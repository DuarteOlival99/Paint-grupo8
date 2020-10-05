package com.example.paint.data.local.list

import com.example.paint.R

class ListStorage private constructor() {

    private var backgroundColor : Int? = null

    companion object {

        private var instance: ListStorage? = null

        fun getInstance(): ListStorage {
            synchronized(this) {
                if (instance == null) {
                    instance =
                        ListStorage()
                }
                return instance as ListStorage
            }
        }

    }

    fun setBackgroundColor(mDefaultColor: Int) {
        backgroundColor = mDefaultColor
    }

    fun getBackgroundColor(): Int? {
        return backgroundColor
    }

}