package com.example.tay.nearby.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tay.nearby.R
import kotlinx.android.synthetic.main.item_place_type.view.*

class PlaceTypeAdapter(private val listPlaceType: List<String>,
                       private val clickListener: (String) -> Unit): RecyclerView.Adapter<PlaceTypeAdapter.PlaceTypeViewHolder>() {

    inner class PlaceTypeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(placeType: String, clickListener: (String) -> Unit) {
            itemView.txt_place_type_name.text = placeType
            itemView.setOnClickListener { clickListener(placeType) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceTypeViewHolder {
        return PlaceTypeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_place_type, parent, false))
    }

    override fun getItemCount(): Int {
        return listPlaceType.size
    }

    override fun onBindViewHolder(holder: PlaceTypeViewHolder, position: Int) {
        holder.bind(listPlaceType[position], clickListener)
    }

}
