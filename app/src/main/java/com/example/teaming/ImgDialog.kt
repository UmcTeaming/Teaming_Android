package com.example.teaming

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.teaming.databinding.ImgAddDialogBinding
import android.Manifest
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.teaming.databinding.UserAddDialogBinding
import java.net.URI

class ImgDialog : DialogFragment() {
    private lateinit var binding: ImgAddDialogBinding
    private lateinit var onImgSelectedListener: OnImgSelectedListener
    private var img_num: Int = 0

    private val CAMERA_PERMISSION_CODE = 101
    private val CAMERA_REQUEST_CODE = 102

    private val GALLERY_PERMISSION_CODE = 103
    private val GALLERY_REQUEST_CODE = 104

    interface OnImgSelectedListener {
        fun onImgSelected(img_num: Int)
        fun onImgSelected(imageUri: Uri?)
        fun onImgSelected(imageBitmap: Bitmap?)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ImgAddDialogBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.cameraBtn.setOnClickListener {
            if (checkCameraPermission()) {
                cameraLauncher.launch(null)
            } else {
                requestCameraPermission()
            }
        }


        binding.galleryBtn.setOnClickListener {
            if (checkGalleryPermission()) {
                galleryLauncher.launch("image/*")
            } else {
                requestGalleryPermission()
            }
        }

        binding.basicImg.setOnClickListener {
            img_num = 1
            onImgSelectedListener.onImgSelected(img_num)
            dismiss()
        }

        return view
    }

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_CODE
        )
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "카메라 권한이 필요합니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            GALLERY_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "갤러리 읽기 권한이 필요합니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri: Uri? ->
        if (imageUri != null) {
            onImgSelectedListener.onImgSelected(imageUri)
            dismiss()
        }
    }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { imageBitmap: Bitmap? ->
        if (imageBitmap != null) {
            onImgSelectedListener.onImgSelected(imageBitmap)
            dismiss()
        }
    }

    private fun checkGalleryPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_MEDIA_IMAGES
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestGalleryPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
            GALLERY_PERMISSION_CODE
        )
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Initialize the onColorSelectedListener with the parent Fragment
        try {
            onImgSelectedListener = targetFragment as OnImgSelectedListener
        } catch (e: ClassCastException) {
            throw ClassCastException("Calling Fragment must implement OnImgSelectedListener")
        }
    }

}