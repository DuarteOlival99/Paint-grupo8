package com.example.paint.ui.fragments

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.paint.R
import com.example.paint.data.sensors.shake.ShakeDetector
import com.example.paint.ui.listeners.GestureListener
import com.example.paint.ui.utils.MyCanvasView
import com.example.paint.ui.viewmodels.viewmodels.PaintViewModel
import kotlin.math.max
import kotlin.properties.Delegates


class CanvasFragment : Fragment(){

    private lateinit var viewModel : PaintViewModel
    private lateinit var canvasView: MyCanvasView
    private lateinit var gd : GestureDetector
    // The following are used for the shake detection
    private var mSensorManager: SensorManager? = null
    private var mAccelerometer: Sensor? = null
    private var mShakeDetector: ShakeDetector? = null
    //luminosity
    private var sensorManager: SensorManager? = null
    private var lightSensor: Sensor? = null
    private var lightEventListener: SensorEventListener? = null
    private var maxValue = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSensor()
        sensorManager = context!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val mGestureListener = GestureListener()
        val mGestureDetector = GestureDetector(activity!!.applicationContext, mGestureListener)
        mGestureDetector.setIsLongpressEnabled(true)
        mGestureDetector.setOnDoubleTapListener(mGestureListener)

        canvasView = MyCanvasView(activity!!.applicationContext, mGestureDetector)
        mGestureListener.setCanvas(canvasView)

        // myCanvasView.systemUiVisibility = SYSTEM_UI_FLAG_FULLSCREEN
        canvasView.contentDescription = getString(R.string.canvasContentDescription)
        viewModel = ViewModelProviders.of(this).get(PaintViewModel::class.java)

        if (lightSensor == null) {
            Toast.makeText(context, "The device has no light sensor !", Toast.LENGTH_SHORT).show()
        }

        // max value for light sensor

        // max value for light sensor
        maxValue = lightSensor!!.maximumRange

        lightEventListener = object : SensorEventListener {
            override fun onSensorChanged(sensorEvent: SensorEvent) {
                val value = sensorEvent.values[0]
                (activity as AppCompatActivity).supportActionBar!!.title = "Luminosity : $value lx"

                val percentagemLuz = (value * 100) / maxValue
                if (percentagemLuz < 20){
                    val brightness = (100 - percentagemLuz).toInt()
                    Log.i("teste", brightness.toString())
                    if (brightness >= 99){
                        Settings.System.putInt(context!!.contentResolver, Settings.System.SCREEN_BRIGHTNESS,
                            255
                        )
                    }else{
                        Settings.System.putInt(context!!.contentResolver, Settings.System.SCREEN_BRIGHTNESS,
                            brightness.toInt()
                        )
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor, i: Int) {}
        }

        return canvasView
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    fun atualizaCorPicenl() {
        canvasView.atualizaCorPicenl()
    }

    fun atualizaCorCanvas() {
        canvasView.atualizaCorCanvas()
    }

    fun atualizaPincelEspessura() {
        canvasView.atualizaPincelEspessura()
    }




    override fun onResume() {
        super.onResume()
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager!!.registerListener(
            mShakeDetector,
            mAccelerometer,
            SensorManager.SENSOR_DELAY_UI
        )
        sensorManager!!.registerListener(lightEventListener, lightSensor, SensorManager.SENSOR_DELAY_FASTEST)
    }

    override fun onPause() { // Add the following line to unregister the Sensor Manager onPause
        mSensorManager!!.unregisterListener(mShakeDetector)
        sensorManager!!.unregisterListener(lightEventListener)
        super.onPause()
    }

    private fun initSensor() {
        // ShakeDetector initialization
        // ShakeDetector initialization
        mSensorManager = context!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager?
        mAccelerometer = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mShakeDetector = ShakeDetector()
        mShakeDetector!!.setOnShakeListener(object : ShakeDetector.OnShakeListener {
            override fun onShake(count: Int) { /*
                 * The following method, "handleShakeEvent(count):" is a stub //
                 * method you would use to setup whatever you want done once the
                 * device has been shook.
                 */
                Toast.makeText(context, count.toString(), Toast.LENGTH_SHORT).show()
                canvasView.cleanScreen()
            }
        })
    }

}