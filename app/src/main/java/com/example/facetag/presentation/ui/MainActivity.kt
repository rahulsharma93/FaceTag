package com.example.facetag.presentation.ui


import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var scanButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[GalleryViewModel::class.java]
        recyclerView = findViewById(R.id.galleryRecyclerView)
        scanButton = findViewById(R.id.scanButton)

        recyclerView.layoutManager = GridLayoutManager(this, 2)

        viewModel.galleryImages.observe(this) { images ->
            recyclerView.adapter = GalleryAdapter(images)
        }

        scanButton.setOnClickListener {
            viewModel.scanGallery()
        }
    }
}

