package com.example.paint.ui.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Optional
import com.example.paint.R
import com.example.paint.data.entity.Upload
import com.example.paint.ui.viewmodels.viewmodels.PaintViewModel
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

class CameraFragment : Fragment(){
    private lateinit var viewModel: PaintViewModel
    lateinit var currentPhotoPath: String
    private var imageView: ImageView? = null
    private var mFirebaseStorage = FirebaseStorage.getInstance()
    private var mDatabaseStorage = FirebaseDatabase.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_teste, container, false)
        imageView = view!!.findViewById<View>(R.id.imageView1) as ImageView
        viewModel = ViewModelProviders.of(this).get(PaintViewModel::class.java)
        ButterKnife.bind(this, view)
        return view

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PaintViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    @Optional
    @OnClick(R.id.fabNewLugar)
    fun onClickNewLugar(view: View){
        if (checkSelfPermission(
                activity!!,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(android.Manifest.permission.CAMERA),
                CameraFragment.MY_CAMERA_PERMISSION_CODE
            )
        } else {
            val cameraIntent =
                Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, CameraFragment.CAMERA_REQUEST)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, getText(R.string.com_permissao), Toast.LENGTH_LONG).show()
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST)
            } else {
                Toast.makeText(context, getText(R.string.sem_permissao), Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            val photo = data!!.extras?.get("data") as Bitmap
            imageView!!.setImageBitmap(photo)

            val outputStream : ByteArrayOutputStream = ByteArrayOutputStream()
            photo.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            val data = outputStream.toByteArray()

            val path : String = "images/camera/" + UUID.randomUUID() + ".png"
            val mStorageRef : StorageReference = mFirebaseStorage.getReference(path)
            val mDatabaseRef : DatabaseReference = mDatabaseStorage.getReference("image/camera/")

            val metadata : StorageMetadata = StorageMetadata.Builder()
                .setCustomMetadata("caption", "teste")
                .build()

            val uploadTask : UploadTask = mStorageRef.putBytes(data, metadata)

            uploadTask.addOnFailureListener(context as Activity
            ) { e ->
                Toast.makeText(
                    context, "Upload Error: " +
                            e.message, Toast.LENGTH_LONG
                ).show()
            }.addOnSuccessListener(context as Activity,
                OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot -> //Uri url = taskSnapshot.getDownloadUrl();
                    val uri: Task<Uri> = taskSnapshot.storage.downloadUrl
                    while (!uri.isComplete);
                    val url: Uri = uri.result!!
                    Toast.makeText(
                                context, "Upload Success" , Toast.LENGTH_LONG
                    ).show()
                    val upload = Upload(
                        path,
                        url.toString()
                    )
                    mDatabaseRef.push().setValue(upload)
                    Log.i("FBApp1_URL ", url.toString())
                })


//            // Write a message to the database
//            val database = Firebase.database
//            val myRef = database.getReference("message")
//            myRef.setValue("Hello, World!" + UUID.randomUUID())

//            uploadTask.addOnFailureListener{
//                Toast.makeText(context, "failure", Toast.LENGTH_SHORT).show()
//            }
//
//            uploadTask.addOnSuccessListener {
//                Toast.makeText(context, "sucess", Toast.LENGTH_SHORT).show()
//                val downloadUrl = uploadTask.snapshot
//                var ImageUrl = downloadUrl.toString()
//                Log.i("downloadUrl", ImageUrl)
//            }
//
//            uploadTask.addOnCompleteListener {
//                if (uploadTask.isComplete){
//                    Toast.makeText(context, "complete", Toast.LENGTH_SHORT).show()
//                   // val downloadUri = uploadTask.result
//                    //Log.e("TAG", downloadUri.toString());
////                    val upload = Upload(
////                        "teste",
////                        downloadUri.toString()
////                    )
////                    mDatabaseRef.push().setValue(upload)
////                    Log.i("teste", upload.toString())
//                }
//            }

        }
    }

    // convert from bitmap to byte array
    fun getBytes(bitmap: Bitmap): ByteArray? {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream)
        return stream.toByteArray()
    }

    // convert from byte array to bitmap
    fun getImage(image: ByteArray): Bitmap? {
        return BitmapFactory.decodeByteArray(image, 0, image.size)
    }

    companion object {
        private const val CAMERA_REQUEST = 1888
        private const val MY_CAMERA_PERMISSION_CODE = 100
    }


}