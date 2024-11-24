package com.example.facetag.domain.repository

import com.example.facetag.data.model.GalleryImageEntity

interface GalleryRepository {
    suspend fun getGalleryImages(): List<GalleryImageEntity>
}