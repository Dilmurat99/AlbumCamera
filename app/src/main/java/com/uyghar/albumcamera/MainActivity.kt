package com.uyghar.albumcamera

import android.Manifest
import android.R.attr
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.core.app.ActivityCompat.startActivityForResult

import android.content.Intent

import androidx.core.app.ActivityCompat.requestPermissions

import android.content.pm.PackageManager
import android.os.Build
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import android.graphics.Bitmap

import android.app.Activity
import android.R.attr.data
import android.R.attr.data











class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private val CAMERA_REQUEST = 1888
    private val ALBUM_REQUEST = 2888
    private val MY_CAMERA_PERMISSION_CODE = 100
    private val MY_ALBUM_PERMISSION_CODE = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonAlbum: Button = findViewById(R.id.button_album)
        val buttonCamera: Button = findViewById(R.id.button_camera)
        imageView = findViewById(R.id.imageView)
        buttonCamera.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                        arrayOf(Manifest.permission.CAMERA),
                        MY_CAMERA_PERMISSION_CODE
                    )
                } else {
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(cameraIntent, CAMERA_REQUEST)
                    /*val activityResultLauncher =
                        registerForActivityResult(
                            ActivityResultContracts.RequestMultiplePermissions())
                        { permissions ->
                            // Handle Permission granted/rejected
                            permissions.entries.forEach {
                                val permissionName = it.key
                                val isGranted = it.value
                                if (isGranted) {
                                    // Permission is granted
                                } else {
                                    // Permission is denied
                                }
                            }
                        }*/

                }
            }
        }

        buttonAlbum.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        MY_ALBUM_PERMISSION_CODE
                    )
                } else {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    startActivityForResult(intent, ALBUM_REQUEST)


                    /*val activityResultLauncher =
                        registerForActivityResult(
                            ActivityResultContracts.RequestMultiplePermissions())
                        { permissions ->
                            // Handle Permission granted/rejected
                            permissions.entries.forEach {
                                val permissionName = it.key
                                val isGranted = it.value
                                if (isGranted) {
                                    // Permission is granted
                                } else {
                                    // Permission is denied
                                }
                            }
                        }*/

                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
                when(requestCode) {
                    CAMERA_REQUEST -> {
                        val bitmap = data?.extras?.get("data") as? Bitmap
                        imageView.setImageBitmap(bitmap)
                    }
                    ALBUM_REQUEST -> imageView.setImageURI(data?.data)

            }
        }

    }
}