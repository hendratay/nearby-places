package com.example.tay.nearby.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.tay.nearby.entity.Place
import com.example.tay.nearby.R
import com.example.tay.nearby.repository.PlaceRepositoryImplementation.Companion.BASE_URL
import kotlinx.android.synthetic.main.item_place.view.*
import java.util.ArrayList

class PlaceAdapter(private val mContext: Context,
                   private var mPlaceList: MutableList<Place>):
        RecyclerView.Adapter<PlaceAdapter.PlaceAdapterViewHolder>() {

    companion object {
        val PHOTO_URL = BASE_URL + "maps/api/place/photo" +
                "?maxwidth=400" +
                "&maxheight=400" +
                "&key=AIzaSyD9IJQZu5QBrnxlVXDDIO4I7mwZV_e_KOk" +
                "&photoreference="
    }

    inner class PlaceAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(place: Place) {
            itemView.place_name.text = place.name
            Glide.with(mContext)
                    .load(PHOTO_URL + place.photo?.get(0)?.photoreference)
                    .apply(RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(itemView.place_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceAdapter.PlaceAdapterViewHolder {
        return PlaceAdapterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false))
    }

    override fun onBindViewHolder(holder: PlaceAdapterViewHolder, position: Int) {
        holder.bind(mPlaceList[position])
    }

    override fun getItemCount(): Int {
        return mPlaceList.size
    }

    /**
     * Adds all of the items to the data set.
     * @param items The item list to be added.
     */
    fun addAll(items: List<Place>) {
        mPlaceList = ArrayList()
        mPlaceList.addAll(items)
        notifyDataSetChanged()
    }

    /**
     * Clears all items from the data set.
     */
    fun clear() {
        mPlaceList.clear()
        notifyDataSetChanged()
    }

}
