package com.example.tay.nearby.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tay.nearby.R

class PlaceTypeAdapter: RecyclerView.Adapter<PlaceTypeAdapter.PlaceTypeViewHolder>() {

    inner class PlaceTypeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind() {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceTypeViewHolder {
        return PlaceTypeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_place_type, parent, false))
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: PlaceTypeViewHolder, position: Int) {
        holder.bind()
    }

}
