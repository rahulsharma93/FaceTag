package com.example.facetag.presentation.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.facetag.R
import com.example.facetag.domain.model.GalleryImage

class GalleryAdapter(
    private val images: List<GalleryImage>
) : RecyclerView.Adapter<GalleryAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount() = images.size

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.photoImageView)
        private val overlay: FrameLayout = itemView.findViewById(R.id.overlay)

        fun bind(image: GalleryImage) {
            imageView.setImageBitmap(image.bitmap)
            overlay.removeAllViews()
            image.faces.forEach { face ->
                val box = View(itemView.context).apply {
                    layoutParams = FrameLayout.LayoutParams(
                        face.width.toInt(),
                        face.height.toInt()
                    ).apply {
                        leftMargin = face.left.toInt()
                        topMargin = face.top.toInt()
                    }
                    setBackgroundColor(Color.RED)
                    alpha = 0.5f
                }
                overlay.addView(box)
            }
        }
    }
}
