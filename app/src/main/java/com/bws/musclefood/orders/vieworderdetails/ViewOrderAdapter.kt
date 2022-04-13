package com.bws.musclefood.orders.vieworderdetails

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
import com.bws.musclefood.favourites.FavouritesActivity
import com.bws.musclefood.orders.searchorder.OrderItem

class ViewOrderAdapter (val mList: ArrayList<OrderItem>): RecyclerView.Adapter<ViewOrderAdapter.ViewHolder>() {

    var context: Context? = null
    var myInt: Int = 1
    var totalPrice: Double = 0.0
    var totalProductPrice: Double = 0.0
    var totalDiscount: Double = 0.0
    var netDiscount: Double = 0.0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_order, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemProduct = mList[position]
        holder.txtPName.text = itemProduct.OrderItem
        holder.txtPrice.text = "£"+itemProduct.ItemPrice
      //  holder.txtQuantity.text = itemProduct.Q
      //  holder.txtDiscountPrice.text = "£"+itemProduct.price
        //holder.txtYouSaved.text = "£"+itemProduct.youSaved + "\n" +"SAVED"
       // holder.imvProduct.setImageResource(itemProduct.image)

      //  var youSaved = itemProduct.youSaved
//        if(youSaved.equals("",true)){
//            holder.txtYouSaved.visibility = View.GONE
//            holder.txtDiscountPrice.visibility = View.GONE
//        }

//        val productQuantity = holder.txtTotalQuentity.text.toString().toInt()
//        val productPrice = holder.txtPrice.text.toString().drop(1).toFloat()
//        totalProductPrice = (productQuantity * productPrice).toDouble()
//        totalPrice = totalPrice + totalProductPrice
//        System.out.println("Total Price===" + totalPrice)
//
//        val discountPrice = holder.txtDiscountPrice.text.toString().drop(1).toFloat()
//        totalDiscount = discountPrice - totalProductPrice
//        netDiscount = netDiscount + totalDiscount
//        holder.txtDiscountPrice.setPaintFlags(holder.txtDiscountPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)


        //var favoriteFlag = itemProduct.FavoriteFlag

        if(itemProduct.FavoriteFlag == "Y"){
            holder.imvAddToFavourites.visibility = View.VISIBLE
            holder.imvAddToFavouritesHover.visibility = View.GONE
        }else{
            holder.imvAddToFavourites.visibility = View.GONE
            holder.imvAddToFavouritesHover.visibility = View.VISIBLE
        }

       /* holder.imvAddToFavourites.setOnClickListener() {
            holder.imvAddToFavourites.visibility = View.GONE
            holder.imvAddToFavouritesHover.visibility = View.VISIBLE
        }
        holder.imvAddToFavouritesHover.setOnClickListener() {
            holder.imvAddToFavourites.visibility = View.VISIBLE
            holder.imvAddToFavouritesHover.visibility = View.GONE
        }*/
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val txtPName: TextView = itemView.findViewById(R.id.txtPName)
        val txtQuantity: TextView = itemView.findViewById(R.id.txtQuantity)
        val txtPrice: TextView = itemView.findViewById(R.id.txtPrice)
        val txtDiscountPrice: TextView = itemView.findViewById(R.id.txtDiscountPrice)
        val txtDeleteProduct: TextView = itemView.findViewById(R.id.txtDeleteProduct)
        val txtDecrement: TextView = itemView.findViewById(R.id.txtDecrement)
        val txtAdd: TextView = itemView.findViewById(R.id.txtAdd)
        val txtInrement: TextView = itemView.findViewById(R.id.txtInrement)
        val txtTotalQuentity: TextView = itemView.findViewById(R.id.txtTotalQuentity)
        val txtYouSaved: TextView = itemView.findViewById(R.id.txtYouSaved)
        val imvProduct: ImageView = itemView.findViewById(R.id.imvProduct)
        val llIncrementDecrement: LinearLayout = itemView.findViewById(R.id.llIncrementDecrement)

        val imvAddToFavourites: ImageView = itemView.findViewById(R.id.imvAddToFavourites)
        val imvAddToFavouritesHover: ImageView = itemView.findViewById(R.id.imvAddToFavouritesHover)
    }
}
