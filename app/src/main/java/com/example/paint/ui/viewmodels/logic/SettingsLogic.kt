package com.example.paint.ui.viewmodels.logic

import com.example.paint.data.local.list.ListStorage

class SettingsLogic() {
    private val storage = ListStorage.getInstance()


    fun setBackgroundColor(mDefaultColor: Int) {
        storage.setBackgroundColor(mDefaultColor)
    }

    fun getBackgroundColor(): Int? {
        return storage.getBackgroundColor()
    }


}