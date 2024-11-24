package com.example.facetag.data.model

import android.graphics.Bitmap
import android.net.Uri

data class GalleryImageEntity(
    val uri: Uri,
    val bitmap: Bitmap,
    val faces: List<FaceEntity> = emptyList()
)

data class FaceEntity(
    val left: Float,
    val top: Float,
    val width: Float,
    val height: Float
)