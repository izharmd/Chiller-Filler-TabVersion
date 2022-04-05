package com.bws.musclefood.delivery.deliveryoption.viewcartItems

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bws.musclefood.R
import com.bws.musclefood.itemcategory.cartlist.CartListModel


class ViewCartItemAdapter(val mList: List<CartListModel>) :
    RecyclerView.Adapter<ViewCartItemAdapter.ViewHolder>() {

    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_cart_list, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemProduct = mList[position]
        holder.txtProductName.text = itemProduct.pName
       // holder.txtProductQuantity.text = itemProduct.quantity
        holder.txtPrice.text = itemProduct.price
       // holder.imvProduct.setImageResource(itemProduct.image)

        var productImage = itemProduct.image
        if (productImage !== null) {
            Glide.with(context!!)
                .load(itemProduct.image)
                .into(holder.imvProduct)
        } else {
            holder.imvProduct.setImageResource(R.drawable.ic_launcher_background)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val txtProductName: TextView = itemView.findViewById(R.id.txtProductName)
        val txtProductQuantity: TextView = itemView.findViewById(R.id.txtProductQuantity)
        val txtPrice: TextView = itemView.findViewById(R.id.txtPrice)
        val imvProduct: ImageView = itemView.findViewById(R.id.imvProduct)
    }
}
