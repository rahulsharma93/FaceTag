package com.example.facetag.data.repository

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import com.example.facetag.data.model.GalleryImageEntity
import com.example.facetag.domain.repository.GalleryRepository
import com.example.facetag.utils.FaceDetector
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    private val faceDetector: FaceDetector,
    private val context: Context
) : GalleryRepository {

    override suspend fun getGalleryImages(): List<GalleryImageEntity> {
        val images = mutableListOf<GalleryImageEntity>()
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.Media.DATA)

        context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            while (cursor.moveToNext()) {
                val filePath = cursor.getString(dataColumn)
                val bitmap = BitmapFactory.decodeFile(filePath)
                images.add(GalleryImageEntity(uri = Uri.parse(filePath), bitmap = bitmap))
            }
        }

        return images.map { image ->
            val faces = faceDetector.detectFaces(image.bitmap)
            image.copy(faces = faces)
        }
    }
}
