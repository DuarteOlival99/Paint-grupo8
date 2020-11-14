package com.example.paint.ui.utils

import android.content.Context
import android.graphics.*
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.example.paint.R
import com.example.paint.data.entity.Upload
import com.example.paint.data.local.list.ListStorage
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream
import java.util.*


private const val STROKE_WIDTH = 12f // has to be floats

open class MyCanvasView(context: Context?) : View(context) , View.OnTouchListener {
    private var mFirebaseStorage = FirebaseStorage.getInstance()
    private var mDatabaseStorage = FirebaseDatabase.getInstance()
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

    private var actionUp = false
    private var actionDown = false
    private var actionMove = false

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
        currentX = eventX
        currentY = eventY
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                actionDown = true
                Log.i("ACTION_DOWN", "ACTION_DOWN")
                path.moveTo(eventX, eventY) // updates the path initial point
                //touchStart()
                //path.reset()
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                actionMove = true
                Log.i("ACTION_MOVE", "ACTION_MOVE")
                if (storage.getCircle() || storage.getTriangle() || storage.getSquare()) {
                    when {
                        storage.getCircle() -> {
                            drawCircle(extraCanvas)
                            Log.i("MCircle", "MCircle")
                        }
                        storage.getSquare() -> {
                            drawSquare(extraCanvas)
                            Log.i("MSquare", "MSquare")
                        }
                        storage.getTriangle() -> {
                            drawTriangle(currentX, currentY, extraCanvas)
                            Log.i("MTriangle", "MTriangle")

                        }
                    }
                } else {
                    path.lineTo(
                        eventX,
                        eventY
                    )
                    extraCanvas.drawPath(path, paint)
                }
                invalidate()
            } // makes a line to the point each time this event is fired
            MotionEvent.ACTION_UP -> {
                actionUp = true
                Log.i("ACTION_UP", "ACTION_UP")
                if ((actionDown && actionUp) && !actionMove) {
                    if (storage.getCircle() || storage.getTriangle() || storage.getSquare()) {
                        when {
                            storage.getCircle() -> {
                                drawCircle(extraCanvas)
                                Log.i("MCircle", "MCircle")
                            }
                            storage.getSquare() -> {
                                drawSquare(extraCanvas)
                                Log.i("MSquare", "MSquare")
                            }
                            storage.getTriangle() -> {
                                drawTriangle(currentX, currentY, extraCanvas)
                                Log.i("MTriangle", "MTriangle")

                            }
                        }
                    }
                }
                invalidate()
                path.reset()
            }
        }
        actionDown = false
        actionMove = false
        actionUp =  false
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

    private fun drawCircle(canvas: Canvas){
        Log.i("DCircle", "DCircle1")
        canvas.drawCircle(currentX, currentY, 70F, paint)
        Log.i("DCircle2", "DCircle2")
    }

    private fun drawSquare(canvas: Canvas){
        val RADIUS = 70
        val square: Rect = Rect(
            ((currentX - ((0.8) * RADIUS)).toInt()),
            ((currentY - ((0.6) * RADIUS)).toInt()),
            ((currentX + ((0.8) * RADIUS)).toInt()),
            ((currentY + ((0.6 * RADIUS))).toInt())
        )
       //canvas.drawBitmap(extraBitmap,square,square,paint )
       canvas.drawRect(square, paint);

    }

    private fun drawTriangle(x: Float, y: Float, canvas: Canvas) {
        val width = 140
        val halfWidth = width / 2
        val path = Path()
        path.moveTo(x.toFloat(), (y - halfWidth).toFloat()) // Top
        path.lineTo((x - halfWidth).toFloat(), (y + halfWidth).toFloat()) // Bottom left
        path.lineTo((x + halfWidth).toFloat(), (y + halfWidth).toFloat()) // Bottom right
        path.lineTo(x.toFloat(), (y - halfWidth).toFloat()) // Back to Top
        path.close()
        canvas.drawPath(path, paint)
    }

    fun cleanScreen() {
        extraCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
        invalidate()
    }

    fun saveFirebaseCanvas(imageTitle: String){

        val outputStream : ByteArrayOutputStream = ByteArrayOutputStream()
        extraBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val data = outputStream.toByteArray()

        val path : String = "images/canvas/" + imageTitle + " || " + UUID.randomUUID() + ".png"
        val fireTeste : StorageReference = mFirebaseStorage.getReference(path)
        val mDatabaseRef : DatabaseReference = mDatabaseStorage.getReference("image/canvas/")

        val metadata : StorageMetadata = StorageMetadata.Builder()
            .setCustomMetadata("Canvas", imageTitle)
            .build()

        val uploadTask : UploadTask = fireTeste.putBytes(data, metadata)

        uploadTask.addOnFailureListener(
        ) { e ->
            Toast.makeText(
                context, "Upload Error: " +
                        e.message, Toast.LENGTH_LONG
            ).show()
        }.addOnSuccessListener(
            OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot -> //Uri url = taskSnapshot.getDownloadUrl();
                val uri: Task<Uri> = taskSnapshot.storage.downloadUrl
                while (!uri.isComplete);
                val url: Uri = uri.result!!
                Toast.makeText(
                    context, "Upload Success, download URL " +
                            url.toString(), Toast.LENGTH_LONG
                ).show()
                val upload = Upload(
                    imageTitle,
                    url.toString()
                )
                mDatabaseRef.push().setValue(upload)
                Log.i("FBApp1_URL ", url.toString())
            })

    }

    fun getImageCanvas(): Bitmap? {
        return extraBitmap
    }

    fun setCanvasImage(bitmapFromURL: Bitmap?) {
        if (bitmapFromURL != null) {

            val workingBitmap: Bitmap = Bitmap.createBitmap(bitmapFromURL)
            val mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true)

            extraBitmap = mutableBitmap
            extraCanvas = Canvas(extraBitmap)
            invalidate()
        }
    }


}