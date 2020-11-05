package com.example.paint.ui.utils

import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.paint.R
import com.example.paint.ui.fragments.AboutFragment
import com.example.paint.ui.fragments.MapFragment
import com.example.paint.ui.fragments.PaintFragment
import com.example.paint.ui.fragments.SettingsFragment

class NavigationManager {

    companion object{

        private fun placeFragment(fm: FragmentManager, fragment: Fragment) {
            Log.i("navigation", "navigation")
            val transition = fm.beginTransaction()
            transition.replace(R.id.frame, fragment)
            transition.addToBackStack(null)
            transition.commit()
        }

        private fun placeChildFragment(fm: FragmentManager, fragment: Fragment) {
            Log.i("navigation", "navigation")
            val transition = fm.beginTransaction()
            transition.replace(R.id.frame, fragment)
            transition.addToBackStack(null)
            transition.commit()
        }

        fun goToPaint(fm: FragmentManager) {
            placeFragment(
                fm,
                PaintFragment()
            )
        }

        fun goToMap(fm: FragmentManager) {
            placeFragment(
                fm,
                MapFragment()
            )
        }

        fun goToAbout(fm: FragmentManager) {
            placeFragment(
                fm,
                AboutFragment()
            )
        }


        fun goToSettings(fm: FragmentManager) {
            placeFragment(
                fm,
                SettingsFragment()
            )
        }


    }

}