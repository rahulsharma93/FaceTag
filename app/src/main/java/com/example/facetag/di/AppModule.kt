package com.example.facetag.di

import android.content.Context
import com.example.facetag.data.repository.GalleryRepositoryImpl
import com.example.facetag.domain.repository.GalleryRepository
import com.example.facetag.utils.FaceDetector
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Module
    @InstallIn(SingletonComponent::class)
    object AppModule {

        @Provides
        fun provideFaceDetector( @ApplicationContext context: Context): FaceDetector = FaceDetector(context)

        @Provides
        fun provideGalleryRepository(
            faceDetector: FaceDetector,
            @ApplicationContext context: Context
        ): GalleryRepository = GalleryRepositoryImpl(faceDetector, context)
    }
}
