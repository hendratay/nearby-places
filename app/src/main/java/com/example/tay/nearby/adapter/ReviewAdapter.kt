package com.example.tay.nearby.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tay.nearby.R
import com.example.tay.nearby.entity.Review
import kotlinx.android.synthetic.main.item_review.view.*

class ReviewAdapter(private val mContext: Context, private val mReviewList: List<Review>) : RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder>() {

    inner class ReviewAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(review: Review) {
            Glide.with(mContext)
                    .load(review.reviewPhoto)
                    .apply(RequestOptions()
                            .circleCrop())
                    .into(itemView.review_photo)
            itemView.review_name.text = review.reviewName
            itemView.review_rating.rating = review.reviewRating
            itemView.review_time.text = review.reviewTime.toString()
            itemView.review_text.text = review.reviewText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.ReviewAdapterViewHolder {
        return ReviewAdapterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false))
    }

    override fun onBindViewHolder(holder: ReviewAdapter.ReviewAdapterViewHolder, position: Int) {
        holder.bind(mReviewList[position])
    }

    override fun getItemCount(): Int {
        return mReviewList.size
    }

}
