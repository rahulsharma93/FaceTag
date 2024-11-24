package com.example.facetag.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.facetag.domain.model.GalleryImage
import com.example.facetag.domain.usecase.GetGalleryImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val getGalleryImagesUseCase: GetGalleryImagesUseCase
) : ViewModel() {

    private val _galleryImages = MutableLiveData<List<GalleryImage>>()
    val galleryImages: LiveData<List<GalleryImage>> = _galleryImages

    fun scanGallery() {
        viewModelScope.launch {
            val images = getGalleryImagesUseCase()
            _galleryImages.postValue(images)
        }
    }
}
