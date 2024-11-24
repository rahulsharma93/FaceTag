package com.example.facetag.domain.model

import android.graphics.Bitmap
import android.net.Uri

data class GalleryImage(
    val uri: Uri,
    val bitmap: Bitmap,
    val faces: List<Face> = emptyList()
)

data class Face(
    val left: Float,
    val top: Float,
    val width: Float,
    val height: Float
)