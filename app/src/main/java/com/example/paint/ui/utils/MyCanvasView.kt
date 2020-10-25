package com.example.paint.ui.utils

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentManager
import com.example.paint.R
import com.example.paint.data.local.list.ListStorage
import java.util.*


private const val STROKE_WIDTH = 12f // has to be floats

class MyCanvasView(context: Context?) : View(context) , View.OnTouchListener {
    private val paint = Paint()
    private val path = Path()
    private var mGestureDetector: GestureDetector? = null
    private  var mContext: Context? = null
    private  var mAttrs: AttributeSet? = null


    private val storage = ListStorage.getInstance()
    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap
    private var backGroundColor = ResourcesCompat.getColor(resources, R.color.colorBackground, null)
    private var drawColor = ResourcesCompat.getColor(resources, R.color.colorPaint, null)
    //private var drawColor = storage.getPincelColor()

    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f

    private var currentX = 0f
    private var currentY = 0f

    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop

    private lateinit var frame: Rect

    constructor(context: Context?, attrs: AttributeSet?)  : this(context){
        //this.mContext = context
        this.mAttrs = attrs
        //super(context, attrs)
        setOnTouchListener(this)
        setBackgroundColor(backGroundColor)
        initPaint()
    }

    constructor(context: Context?, mGestureDetector: GestureDetector)  : this(context){
        //this.mContext = context
        //super(context, attrs)
        this.mGestureDetector = mGestureDetector
        setOnTouchListener(this)
        setBackgroundColor(backGroundColor)
        initPaint()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(extraBitmap, 0f, 0f, null)
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        mGestureDetector!!.onTouchEvent(event)
        return false // let the event go to the rest of the listeners
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val eventX = event.x
        val eventY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(eventX, eventY) // updates the path initial point
                //touchStart()
                //path.reset()
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(
                    eventX,
                    eventY
                )
                extraCanvas.drawPath(path, paint)
                invalidate()
            } // makes a line to the point each time this event is fired
            MotionEvent.ACTION_UP -> path.reset()
        }
        return true
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)

        Log.i("size", "$width $height")
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        //extraCanvas.drawColor(backgroundColor)
        atualizaCorCanvas()
        //if (::extraBitmap.isInitialized) extraBitmap.recycle()

        // Calculate a rectangular frame around the picture.
        val inset = 40
        frame = Rect(inset, inset, width - inset, height - inset)
    }

    fun changeBackground() {
        val r = Random()
        backGroundColor = Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256))
        setBackgroundColor(backGroundColor)
    }

    fun erase() {
        paint.color = backGroundColor
    }

    private fun initPaint() {
        paint.apply {
            Log.i("pincel color", storage.getPincelColor().toString())
            if (storage.getDefaultPincelColor()){
                color = drawColor
            }else{
                color = storage.getPincelColor()
            }

            // Smooths out edges of what is drawn without affecting shape.
            isAntiAlias = true
            // Dithering affects how colors with higher-precision than the device are down-sampled.
            isDither = true
            style = Paint.Style.STROKE // default: FILL
            strokeJoin = Paint.Join.ROUND // default: MITER
            strokeCap = Paint.Cap.ROUND // default: BUTT
            if (storage.getDefaultPincelEspessuraColor()){
                strokeWidth = STROKE_WIDTH // default: Hairline-width (really thin)
            }else{
                strokeWidth = storage.getPincelEspessura().toFloat()
            }

        }

    }

    fun atualizaCorPicenl() {
        paint.apply {
            Log.i("pincel color", storage.getPincelColor().toString())
            if (storage.getDefaultPincelColor()){
                color = drawColor
            }else{
                color = storage.getPincelColor()
            }
        }
    }

    fun atualizaCorCanvas() {
        if (storage.getDefaultCanvasColor()){
            setBackgroundColor(backGroundColor)
        }else{
            setBackgroundColor(storage.getCanvasColor())
        }
    }

    fun atualizaPincelEspessura() {
        paint.apply {
            strokeWidth = storage.getPincelEspessura().toFloat()
        }
    }

}