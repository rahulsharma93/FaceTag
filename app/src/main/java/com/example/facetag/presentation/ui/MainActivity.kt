package com.example.facetag.presentation.ui


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.facetag.R
import com.example.facetag.presentation.viewmodel.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: GalleryViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestGalleryPermission()
        viewModel = ViewModelProvider(this)[GalleryViewModel::class.java]
        recyclerView = findViewById(R.id.galleryRecyclerView)

        recyclerView.layoutManager = GridLayoutManager(this, 2)

        viewModel.galleryImages.observe(this) { images ->
            recyclerView.adapter = GalleryAdapter(images)
        }
    }

    private fun requestGalleryPermission() {
        when {
            hasGalleryPermission() -> {
                scanGallery()
            }

            shouldShowRequestPermissionRationale(getPermissionName()) -> {
                Toast.makeText(
                    this,
                    "Gallery permission is required to scan photos.",
                    Toast.LENGTH_LONG
                ).show()
            }

            else -> {
                permissionLauncher.launch(getPermissionName())
            }
        }
    }

    private fun hasGalleryPermission(): Boolean {
        val permission = getPermissionName()
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

    private fun getPermissionName(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            scanGallery()
        } else {
            Toast.makeText(
                this,
                "Permission denied. Cannot scan gallery.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun scanGallery() {
        Toast.makeText(this, "Scanning gallery...", Toast.LENGTH_SHORT).show()
        viewModel.scanGallery()
    }

}

