package com.example.paint.data.local.list

class ListStorage private constructor() {


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

}