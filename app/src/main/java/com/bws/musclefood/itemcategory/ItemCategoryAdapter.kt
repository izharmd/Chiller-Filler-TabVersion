package com.bws.musclefood.itemcategory

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bws.musclefood.R
import com.bws.musclefood.itemcategory.productlist.ProductListActivity
import com.bws.musclefood.itemcategory.subcategory.SubCategoryActivity
import com.bws.musclefood.login.LoginActivity

class ItemCategoryAdapter(val list: List<ItemCategoryModel>) :
    RecyclerView.Adapter<ItemCategoryAdapter.ViewHolder>() {

    private var context: Context? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemCategory = list[position]
        holder.txtCategory.text = itemCategory.category
        holder.imvCatogoryImage.setImageResource(itemCategory.image)

        val arr = itemCategory.arrSubCategory

        holder.itemView.setOnClickListener(){
            val designation = list[position].arrSubCategory
            val stri = arr.get(0)
            val i = Intent(context, ProductListActivity::class.java).apply {
                putStringArrayListExtra("Data",designation)
                 putExtra("POSITION",position)
                 putExtra("CATEGORY",list[position].category)
            }

            context?.startActivity(i)
        }
    }


    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val txtCategory: TextView = itemView.findViewById(R.id.txtCategory)
        val imvCatogoryImage: ImageView = itemView.findViewById(R.id.imvCatogoryImage)
    }
}