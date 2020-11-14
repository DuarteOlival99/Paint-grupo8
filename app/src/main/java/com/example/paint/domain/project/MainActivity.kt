package com.example.paint.domain.project

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
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
import com.example.paint.data.entity.HistoryCanvas
import com.example.paint.data.sensors.battery.OnBatteryCurrentListener
import com.example.paint.ui.adapters.HistoryCanvasListAdapter
import com.example.paint.ui.fragments.CanvasFragment
import com.example.paint.ui.utils.NavigationManager
import com.example.paint.ui.viewmodels.viewmodels.PaintViewModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_history_canvas.*
import kotlinx.android.synthetic.main.dialog_save_canvas.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener, OnBatteryCurrentListener {

    private lateinit var viewModel: PaintViewModel
    private var mFirebaseStorage = FirebaseStorage.getInstance()
    val imageRef = Firebase.storage.reference
    var listImagesFinal = mutableListOf<HistoryCanvas>()
    private var menuAtual = "Paint"

    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        setupDrawerMenu()
        when (item.itemId) {
            R.id.paint -> {
                title = getText(R.string.paint).toString()
                menuAtual = getText(R.string.paint).toString()
                NavigationManager.goToPaint(
                    supportFragmentManager
                )
                invalidateOptionsMenu()
            }
            R.id.map -> {
                title = getText(R.string.map).toString()
                menuAtual = getText(R.string.map).toString()
                NavigationManager.goToMap(
                    supportFragmentManager
                )
                invalidateOptionsMenu()
            }
            R.id.settings -> {
                title = getText(R.string.settings).toString()
                menuAtual = getText(R.string.settings).toString()
                NavigationManager.goToSettings(
                    supportFragmentManager
                )
                invalidateOptionsMenu()
            }
            R.id.about -> {
                title = getText(R.string.about).toString()
                menuAtual = getText(R.string.about).toString()
                NavigationManager.goToAbout(
                    supportFragmentManager
                )
                invalidateOptionsMenu()
            }
            R.id.camera -> {
                title = getText(R.string.camera).toString()
                menuAtual = getText(R.string.camera).toString()
                NavigationManager.goToCamera(
                    supportFragmentManager
                )
                invalidateOptionsMenu()
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu);
        val itemSave = menu!!.findItem(R.id.save_canvas)
        val itemGetHistoryCanvas = menu.findItem(R.id.get_history_canvas)
        if(menuAtual != getText(R.string.paint).toString()){
            itemGetHistoryCanvas.isVisible = false
            itemSave.isVisible = false
        }else{
            itemGetHistoryCanvas.isVisible = true
            itemSave.isVisible = true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId;
        when(item.itemId) {
            R.id.save_canvas -> {
                val fragmentCanvas =
                    supportFragmentManager.findFragmentById(R.id.paint_canvas_8) as CanvasFragment
                val dialogView = LayoutInflater.from(this).inflate(
                    R.layout.dialog_save_canvas,
                    null
                )
                val mBuilder = AlertDialog.Builder(this)
                    .setView(dialogView)

                val mAlertDialog = mBuilder.show()

                mAlertDialog.imageView.setImageBitmap(fragmentCanvas.getImageCanvas())

                mAlertDialog.buttonUpload.setOnClickListener {
                    val imageTitle: String = mAlertDialog.editText.text.toString()
                    fragmentCanvas.saveFirebaseCanvas(imageTitle)
                    mAlertDialog.dismiss()
                }

                mAlertDialog.dialog_save_canvas_close.setOnClickListener {
                    mAlertDialog.dismiss()
                }

            }
            R.id.get_history_canvas -> {
                val fragment =
                    supportFragmentManager.findFragmentById(R.id.paint_canvas_8) as CanvasFragment
                val dialogView = LayoutInflater.from(this).inflate(
                    R.layout.dialog_history_canvas,
                    null
                )
                val mBuilder = AlertDialog.Builder(this)
                    .setView(dialogView)

                val mAlertDialog = mBuilder.show()

                listFiles(mAlertDialog)

                mAlertDialog.dialog_history_canvas_close.setOnClickListener {
                    mAlertDialog.dismiss()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun listFiles(mAlertDialog: AlertDialog) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val images = imageRef.child("images/canvas/").listAll().await()
            var listImages = mutableListOf<HistoryCanvas>()
            for(image in images.items) {
                val url = image.downloadUrl.await()
                val title = image.name.split(" || ").map { it -> it.trim() }
                val imageCanvas = HistoryCanvas(title[0], url.toString())
                listImages.add(imageCanvas)
                Log.i("imageCanvas", imageCanvas.toString())
            }
            Log.i("teste", listImages.toString())
            listImagesFinal = listImages
            withContext(Dispatchers.Main) {
                Log.i("teste", listImages.toString())
                mAlertDialog.list_history_canvas.layoutManager = LinearLayoutManager(this@MainActivity)
                mAlertDialog.list_history_canvas.adapter =
                    HistoryCanvasListAdapter(
                        viewModel,
                        this@MainActivity,
                        R.layout.history_canvas_expression,
                        listImages,
                        this@MainActivity,
                        mAlertDialog
                    )
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_LONG).show()
            }
        }
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

    fun setCanvasImage(url: String) {
        val fragmentCanvas = supportFragmentManager.findFragmentById(R.id.paint_canvas_8) as CanvasFragment
        val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val bitMapUrl = getBitmapFromURL(url)
        fragmentCanvas.setCanvasImage(bitMapUrl)
    }

    fun getBitmapFromURL(src: String?): Bitmap? {
        return try {
            val url = URL(src)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            // Log exception
            Log.i("getBitmapFromURL_Error", e.toString())
            null
        }
    }

}