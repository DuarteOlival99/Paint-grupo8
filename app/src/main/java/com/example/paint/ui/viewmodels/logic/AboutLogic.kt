package com.example.paint.ui.viewmodels.logic

import android.content.Context
import com.example.paint.data.local.list.ListStorage

class AboutLogic() {
    private val storage = ListStorage.getInstance()

    fun setBackgroundColor(mDefaultColor: Int) {
        storage.setBackgroundColor(mDefaultColor)
    }

    fun getBackgroundColor(): Int? {
        return storage.getBackgroundColor()
    }
}