package com.bws.musclefood.itemcategory.rating

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bws.musclefood.R


class RatingAdapter(val mList: List<RatingModel>) :
    RecyclerView.Adapter<RatingAdapter.ViewHolder>() {

    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_rating, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemProduct = mList[position]
        holder.txtDisplayName.text = itemProduct.name
        holder.txtReviewCount.text = "4"
        holder.txtReviewDate.text = itemProduct.ratingDate
        holder.txtReviewComment.text = itemProduct.ratingComment
        holder.ratingReviewDialog.rating = itemProduct.ratingNumber.toFloat()
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val txtDisplayName = itemView.findViewById<TextView?>(R.id.txtDisplayName)
        val txtReviewCount = itemView.findViewById<TextView?>(R.id.txtReviewCount)
        val ratingReviewDialog = itemView.findViewById<RatingBar?>(R.id.ratingReviewDialog)
        val txtReviewDate = itemView.findViewById<TextView?>(R.id.txtReviewDate)
        val txtReviewComment = itemView.findViewById<TextView?>(R.id.txtReviewComment)
    }
}
