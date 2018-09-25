package com.example.tay.nearby.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tay.nearby.R
import com.example.tay.nearby.model.remote.entity.Place

class PlaceAdapter(private val context: Context,
                   private val listPlace: List<Place>): RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {

    inner class PlaceViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(place: Place) {
//            itemView.img_place_list_photo.setImageResource()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        return PlaceViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_place_list, parent, false))
    }

    override fun getItemCount(): Int {
        return listPlace.size
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.bind(listPlace[position])
    }

}
