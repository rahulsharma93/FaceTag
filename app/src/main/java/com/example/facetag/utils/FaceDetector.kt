package com.example.facetag.utils

import android.content.Context
import android.graphics.Bitmap
import com.example.facetag.data.model.FaceEntity
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.facelandmarker.FaceLandmarker
import com.google.mediapipe.tasks.vision.facelandmarker.FaceLandmarker.FaceLandmarkerOptions
import com.google.mediapipe.framework.image.BitmapImageBuilder

class FaceDetector(context: Context) {

    private val faceLandmarker: FaceLandmarker

    init {
        val options = FaceLandmarkerOptions.builder()
            .setBaseOptions(
                BaseOptions.builder()
                    .setModelAssetPath("face_landmarker.task") // Place the model file in assets/
                    .build()
            )
            .setRunningMode(RunningMode.IMAGE)
            .build()

        faceLandmarker = FaceLandmarker.createFromOptions(context, options)
    }

    /**
     * Detects faces in the given bitmap and calculates bounding boxes.
     * @param bitmap The image to process.
     * @return A list of FaceEntity objects containing bounding box details.
     */
    fun detectFaces(bitmap: Bitmap): List<FaceEntity> {
        val mpImage = BitmapImageBuilder(bitmap).build()
        val result = faceLandmarker.detect(mpImage)

        return result.faceLandmarks().map { faceLandmark ->
            val xs = faceLandmark.map { it.x() }
            val ys = faceLandmark.map { it.y() }

            FaceEntity(
                left = xs.minOrNull() ?: 0f,
                top = ys.minOrNull() ?: 0f,
                width = (xs.maxOrNull() ?: 0f) - (xs.minOrNull() ?: 0f),
                height = (ys.maxOrNull() ?: 0f) - (ys.minOrNull() ?: 0f)
            )
        }
    }

    fun close() {
        faceLandmarker.close()
    }
}
