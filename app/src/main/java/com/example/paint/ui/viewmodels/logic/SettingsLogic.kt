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

    fun setDarkModeBoolean(b: Boolean) {
        storage.setDarkModeBoolean(b)
    }

    fun getDarkModeBoolean() : Boolean {
        return storage.getDarkModeBoolean()
    }

    fun setDarkModeAutomatico(b: Boolean) {
        storage.setDarkModeAutomatico(b)
    }

    fun getDarkModeAutomatico(): Boolean {
        return storage.getDarkModeAutomatico()
    }

    fun getBackgrondColor(): Int? {
        return storage.getBackgrondColor()
    }


}