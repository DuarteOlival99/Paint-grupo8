package com.example.paint.domain.project

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paint.R
import com.example.paint.data.entity.HistoryRoute
import com.example.paint.data.sensors.battery.OnBatteryCurrentListener
import com.example.paint.ui.adapters.HistoryCanvasListAdapter
import com.example.paint.ui.adapters.HistoryListAdapter
import com.example.paint.ui.fragments.CanvasFragment
import com.example.paint.ui.utils.NavigationManager
import com.example.paint.ui.viewmodels.viewmodels.MapViewModel
import com.example.paint.ui.viewmodels.viewmodels.PaintViewModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_history_canvas.*
import kotlinx.android.synthetic.main.dialog_history_map.*
import kotlinx.android.synthetic.main.dialog_save_canvas.*


class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener, OnBatteryCurrentListener {

    private lateinit var viewModel: PaintViewModel

    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        setupDrawerMenu()
        when (item.itemId) {
            R.id.paint -> {
                title = getText(R.string.paint).toString()
                NavigationManager.goToPaint(
                    supportFragmentManager
                )
            }
            R.id.map -> {
                title = getText(R.string.map).toString()
                NavigationManager.goToMap(
                    supportFragmentManager
                )
            }
            R.id.settings -> {
                title = getText(R.string.settings).toString()
                NavigationManager.goToSettings(
                    supportFragmentManager
                )
            }
            R.id.about -> {
                title = getText(R.string.about).toString()
                NavigationManager.goToAbout(
                    supportFragmentManager
                )
            }
            R.id.teste -> {
                title = "teste"
                NavigationManager.goToTeste(
                    supportFragmentManager
                )
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId;
        when(item.itemId) {
            R.id.save_menu -> {
                val fragment = supportFragmentManager.findFragmentById(R.id.paint_canvas_8) as CanvasFragment
                val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_save_canvas, null)
                val mBuilder = AlertDialog.Builder(this)
                    .setView(dialogView)

                val mAlertDialog = mBuilder.show()

                mAlertDialog.imageView.setImageBitmap( fragment.getImageCanvas())

                mAlertDialog.buttonUpload.setOnClickListener {
                    val imageTitle : String = mAlertDialog.editText.text.toString()
                    fragment.saveFirebaseCanvas(imageTitle)
                    mAlertDialog.dismiss()
                }

                mAlertDialog.dialog_save_canvas_close.setOnClickListener {
                    mAlertDialog.dismiss()
                }

            }
            R.id.get_history_canvas -> {
                val fragment = supportFragmentManager.findFragmentById(R.id.paint_canvas_8) as CanvasFragment
                val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_history_canvas, null)
                val mBuilder = AlertDialog.Builder(this)
                    .setView(dialogView)

                val mAlertDialog = mBuilder.show()

//                mAlertDialog.list_history_canvas.layoutManager = LinearLayoutManager(this)
//                mAlertDialog.list_history_canvas.adapter =
//                    HistoryCanvasListAdapter(
//                        viewModel,
//                        this,
//                        R.layout.history_canvas_expression,
//                        viewModel.getCanvasHistory()
//                    )

                mAlertDialog.buttonUpload.setOnClickListener {
                    val imageTitle : String = mAlertDialog.editText.text.toString()
                    fragment.saveFirebaseCanvas(imageTitle)
                    mAlertDialog.dismiss()
                }

                mAlertDialog.dialog_save_canvas_close.setOnClickListener {
                    mAlertDialog.dismiss()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(PaintViewModel::class.java)
        NavigationManager.goToPaint(
            supportFragmentManager
        )
        setSupportActionBar(toolbar)
        setupDrawerMenu()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var retVal = true
            retVal = Settings.System.canWrite(this)
            if (!retVal) {
                if (!Settings.System.canWrite(applicationContext)) {
                    val intent = Intent(
                        Settings.ACTION_MANAGE_WRITE_SETTINGS,
                        Uri.parse("package:$packageName")
                    )
                    Toast.makeText(
                        applicationContext,
                        "Please, allow system settings for automatic logout ",
                        Toast.LENGTH_LONG
                    ).show()
                    startActivityForResult(intent, 200)
                }
            } else {
//                Toast.makeText(
//                    applicationContext,
//                    "You are not allowed to wright ",
//                    Toast.LENGTH_LONG
//                ).show()
            }
        }


        if (!screenRotated(savedInstanceState)) {
            NavigationManager.goToPaint(
                supportFragmentManager
            )
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun screenRotated(savedInstanceState: Bundle?) : Boolean {
        return savedInstanceState != null
    }

    private fun setupDrawerMenu() {
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )
        nav_drawer.setNavigationItemSelectedListener(this)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        }
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        }
        super.onBackPressed()
    }

    override fun onCurrentChanged(current: Double) {
        Log.i("current", current.toString())
    }

}