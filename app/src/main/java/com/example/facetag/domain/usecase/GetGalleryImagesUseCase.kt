package com.example.facetag.domain.usecase

import com.example.facetag.domain.model.Face
import com.example.facetag.domain.model.GalleryImage
import com.example.facetag.domain.repository.GalleryRepository
import javax.inject.Inject

class GetGalleryImagesUseCase @Inject constructor(
    private val repository: GalleryRepository
) {
    suspend operator fun invoke(): List<GalleryImage> {
        return repository.getGalleryImages().map { entity ->
            GalleryImage(
                uri = entity.uri,
                bitmap = entity.bitmap,
                faces = entity.faces.map { face ->
                    Face(face.left, face.top, face.width, face.height)
                }
            )
        }
    }
}
