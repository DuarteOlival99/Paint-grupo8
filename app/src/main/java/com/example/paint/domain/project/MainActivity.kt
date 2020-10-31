package com.example.paint.domain.project

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.paint.R
import com.example.paint.data.sensors.shake.ShakeDetector
import com.example.paint.ui.fragments.CanvasFragment
import com.example.paint.ui.utils.NavigationManager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {


    // The following are used for the shake detection
    private var mSensorManager: SensorManager? = null
    private var mAccelerometer: Sensor? = null
    private var mShakeDetector: ShakeDetector? = null


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        setupDrawerMenu()
        when (item.itemId) {
            R.id.paint -> {
                title = getText(R.string.paint).toString()
//                val intent = Intent(this, PaintActivity::class.java).apply {}
//                startActivity(intent)
                NavigationManager.goToPaint(
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
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.pincel_menu, menu);
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId;
        when(item.itemId) {
            R.id.pincel_menu -> {
                Toast.makeText(this, "Clean screen", Toast.LENGTH_SHORT).show();
                val fragment = supportFragmentManager.findFragmentById(R.id.paint_canvas_8) as CanvasFragment
                fragment.cleanScreen()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NavigationManager.goToPaint(
            supportFragmentManager
        )
        setSupportActionBar(toolbar)
        setupDrawerMenu()
        initSensor()

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




    override fun onResume() {
        super.onResume()
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager!!.registerListener(
            mShakeDetector,
            mAccelerometer,
            SensorManager.SENSOR_DELAY_UI
        )
    }

    override fun onPause() { // Add the following line to unregister the Sensor Manager onPause
        mSensorManager!!.unregisterListener(mShakeDetector)
        super.onPause()
    }

    private fun initSensor() {
        // ShakeDetector initialization
        // ShakeDetector initialization
        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        mAccelerometer = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mShakeDetector = ShakeDetector()
        mShakeDetector!!.setOnShakeListener(object : ShakeDetector.OnShakeListener {
            override fun onShake(count: Int) { /*
                 * The following method, "handleShakeEvent(count):" is a stub //
                 * method you would use to setup whatever you want done once the
                 * device has been shook.
                 */
                //Toast.makeText(this@MainActivity, count.toString(), Toast.LENGTH_SHORT).show()

                /*
                title = getText(R.string.paint).toString()
//                val intent = Intent(this, PaintActivity::class.java).apply {}
//                startActivity(intent)
                NavigationManager.goToPaint(
                    supportFragmentManager
                )
                */

            }
        })
    }


}