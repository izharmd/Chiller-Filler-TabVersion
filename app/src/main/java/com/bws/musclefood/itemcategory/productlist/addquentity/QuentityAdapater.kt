package com.bws.musclefood.itemcategory.productlist.addquentity

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bws.musclefood.R


class QuentityAdapater (val mList: List<QuentityModel>):
    RecyclerView.Adapter<QuentityAdapater.ViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_quentity, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemCategory = mList[position]
        holder.txtSize.text = itemCategory.quentitySize
        holder.txtPriceDescount.text = itemCategory.descountPrice
        holder.txtNetPrice.text = itemCategory.netPrice

        holder.txtPriceDescount.setPaintFlags(holder.txtPriceDescount.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){
        val txtSize: TextView = itemView.findViewById(R.id.txtSize)
        val txtPriceDescount: TextView = itemView.findViewById(R.id.txtPriceDescount)
        val txtNetPrice: TextView = itemView.findViewById(R.id.txtNetPrice)
    }
}