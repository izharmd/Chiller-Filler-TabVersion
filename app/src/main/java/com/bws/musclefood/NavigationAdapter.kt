package com.bws.musclefood

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bws.musclefood.common.Constant
import com.bws.musclefood.itemcategory.productlist.ProductListActivity
import com.bws.musclefood.orders.reorder.ReorderModel

class NavigationAdapter(val mList: ArrayList<Item>) :
    RecyclerView.Adapter<NavigationAdapter.ViewHolder>() {

    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_view_item_type2, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemProduct = mList[position]
        holder.texItem.text = itemProduct.productItems


        holder.itemView.setOnClickListener {

            (context as ProductListActivity).closeDrawer()
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val texItem: TextView = itemView.findViewById(R.id.texItem)

    }
}
