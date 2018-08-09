package com.example.tay.nearby.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.tay.nearby.R
import com.example.tay.nearby.adapter.PlaceAdapter.Companion.PHOTO_URL
import com.example.tay.nearby.entity.Photo
import kotlinx.android.synthetic.main.item_gallery.view.*

class GalleryAdapter(private val mContext: Context, private val mPhotoList: List<Photo>) : RecyclerView.Adapter<GalleryAdapter.GalleryAdapterViewHolder>() {

    inner class GalleryAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(photo: Photo) {
            Glide.with(mContext)
                    .load(PHOTO_URL + photo.photoreference)
                    .apply(RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(itemView.gallery_photo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryAdapter.GalleryAdapterViewHolder {
        return GalleryAdapterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_gallery, parent, false))
    }

    override fun onBindViewHolder(holder: GalleryAdapterViewHolder, position: Int) {
        holder.bind(mPhotoList[position])
    }

    override fun getItemCount(): Int {
        return mPhotoList.size
    }
}
