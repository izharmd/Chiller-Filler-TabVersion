package com.bws.musclefood.itemcategory.productlist.categorytop

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bws.musclefood.R

class TopCategoryAdapter (val mList: List<TopCategoryModel>):
    RecyclerView.Adapter<TopCategoryAdapter.ViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category_top_round_image, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemCategory = mList[position]
        holder.txtCategoryTop.text = itemCategory.categoryName
        holder.imvProduct.setImageResource(itemCategory.topImage)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){

        val txtCategoryTop: TextView = itemView.findViewById(R.id.txtCategoryTop)
        val imvProduct: ImageView = itemView.findViewById(R.id.imvProduct)

    }
}