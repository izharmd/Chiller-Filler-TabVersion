package com.bws.musclefood.itemcategory.cartlist

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bws.musclefood.R

class CartListAdapter (val mList: List<CartListModel>): RecyclerView.Adapter<CartListAdapter.ViewHolder>() {

    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart_list, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemProduct = mList[position]
        holder.txtPName.text = itemProduct.pName
        holder.txtPrice.text = itemProduct.netPrice
        holder.txtQuantity.text = itemProduct.quantity
        holder.txtDiscountPrice.text = itemProduct.price
        holder.txtYouSaved.text = itemProduct.youSaved + "\n" +"SAVED"
        holder.imvProduct.setImageResource(itemProduct.image)

        var youSaved = itemProduct.youSaved
        if(youSaved.equals("",true)){
            holder.txtYouSaved.visibility = View.GONE
            holder.txtDiscountPrice.visibility = View.GONE
        }

        holder.txtDiscountPrice.setPaintFlags(holder.txtDiscountPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val txtPName: TextView = itemView.findViewById(R.id.txtPName)
        val txtQuantity: TextView = itemView.findViewById(R.id.txtQuantity)
        val txtPrice: TextView = itemView.findViewById(R.id.txtPrice)
        val txtDiscountPrice: TextView = itemView.findViewById(R.id.txtDiscountPrice)
        val txtDecrement: TextView = itemView.findViewById(R.id.txtDecrement)
        val txtInrement: TextView = itemView.findViewById(R.id.txtInrement)
        val txtYouSaved: TextView = itemView.findViewById(R.id.txtYouSaved)
        val imvProduct: ImageView = itemView.findViewById(R.id.imvProduct)
    }
}
