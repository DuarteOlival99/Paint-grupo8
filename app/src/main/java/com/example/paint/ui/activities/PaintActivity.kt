package com.example.paint.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.paint.R
import com.example.paint.ui.fragments.PaintFragment


class PaintActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paint)

//        val canvasFragment : PaintFragment = PaintFragment()
//        val manager : FragmentManager = supportFragmentManager
//        manager.beginTransaction()
//            .replace(R.id.canvas, canvasFragment, canvasFragment.tag)
//            .commit()

    }

}