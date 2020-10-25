package com.example.paint.ui.viewmodels.logic

import com.example.paint.data.local.list.ListStorage

class SplashScreenLogic() {
    private val storage = ListStorage.getInstance()


    fun setBackgroundColor(mDefaultColor: Int) {
        storage.setBackgroundColor(mDefaultColor)
    }
}