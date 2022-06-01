package com.bws.musclefood.orders.reorder

import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ReorderAdapter(val mList: List<OrderItem>,val db: AppDatabase) :
    RecyclerView.Adapter<ReorderAdapter.ViewHolder>() {

    var context: Context? = null
    var myInt: Int = 1
    var totalPrice: Double = 0.0
    var totalProductPrice: Double = 0.0
    var totalDiscount: Double = 0.0
    var netDiscount: Double = 0.0
    var  itemPrice:Float = 0f

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart_list, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemProduct = mList[position]
        holder.txtPName.text = itemProduct.OrderItem
        holder.txtPrice.text = "£"+itemProduct.ItemPrice
        holder.txtTotalQuentity.text = itemProduct.ItemQty

        holder.txtProSize.visibility = View.GONE

        //  holder.txtDiscountPrice.text = "£"+itemProduct.netPrice
       // holder.txtYouSaved.text = "£"+itemProduct.youSaved + "\n" + "SAVED"
       // holder.imvProduct.setImageResource(itemProduct.image)

       /* var youSaved = itemProduct.youSaved
        if (youSaved.equals("", true)) {
            holder.txtYouSaved.visibility = View.GONE
            holder.txtDiscountPrice.visibility = View.GONE
        }*/

        holder.txtDeleteProduct.setOnClickListener() {
           // mList.re(position)
            notifyDataSetChanged()

            Constant.totalCartItem = mList.size

            val productQuantity = holder.txtTotalQuentity.text.toString().toInt()
            val productPrice = holder.txtPrice.text.toString().drop(1).toDouble()
            totalProductPrice = productQuantity * productPrice
            totalPrice = totalPrice - totalProductPrice
            System.out.println("Total Price===" + productQuantity)

            val discountPrice = holder.txtDiscountPrice.text.toString().drop(1).toDouble()
            totalDiscount = discountPrice - totalProductPrice
            netDiscount = netDiscount - totalDiscount

          //  (context as CartListActivity).updateCartItem(totalPrice, netDiscount)

        }


        holder.txtDecrement.setOnClickListener() {
            myInt = holder.txtTotalQuentity.text.toString().toInt()

            if (myInt <= 1) {
                myInt = 1

            } else {
                myInt--
                holder.txtTotalQuentity.text = myInt.toString()

                val productQuantity = holder.txtTotalQuentity.text.toString().toInt()
                val productPrice = holder.txtPrice.text.toString().drop(1).toFloat()
                totalPrice = totalPrice - productPrice
                System.out.println("Total Price===" + productQuantity)

                val discountPrice = holder.txtDiscountPrice.text.toString().drop(1).toFloat()
                totalDiscount = (discountPrice - productPrice).toDouble()
                netDiscount = netDiscount - totalDiscount



                GlobalScope.launch(Dispatchers.Main) {
                    val itemPrice = db.contactDao().getReorderPrice(itemProduct.ProductID)
                    var price = itemPrice[0].ItemPrice.toFloat() - itemProduct.ItemRate.toFloat()
                    holder.txtPrice.text = "£"+price.toString()

                    db.contactDao().updateReorderItems(holder.txtTotalQuentity.text.toString(),price.toString(),itemProduct.ProductID)

                    val dt =  db.contactDao().getReorderItems()
                    Log.d("ALL DATA===",dt.toString())
                }

                (context as ReorderActivity).decrement(itemProduct.ItemRate)
            }
        }

        holder.txtInrement.setOnClickListener() {
            myInt = holder.txtTotalQuentity.text.toString().toInt()
            myInt++
            holder.txtTotalQuentity.text = myInt.toString()

            val productQuantity = holder.txtTotalQuentity.text.toString().toInt()
            val productPrice = holder.txtPrice.text.toString().drop(1).toFloat()
            totalPrice = totalPrice + productPrice
            System.out.println("Total Price===" + productQuantity)

            val discountPrice = holder.txtDiscountPrice.text.toString().drop(1).toFloat()
            totalDiscount = (discountPrice - productPrice).toDouble()
            netDiscount = netDiscount + totalDiscount


            GlobalScope.launch(Dispatchers.Main) {
                val itemPrice = db.contactDao().getReorderPrice(itemProduct.ProductID)
                var price = itemPrice[0].ItemPrice.toFloat() + itemProduct.ItemRate.toFloat()
                holder.txtPrice.text = "£"+price.toString()

                db.contactDao().updateReorderItems(holder.txtTotalQuentity.text.toString(),price.toString(),itemProduct.ProductID)
                val dt =  db.contactDao().getReorderItems()
                Log.d("ALL DATA===",dt.toString())

            }

            (context as ReorderActivity).increment(itemProduct.ItemRate)

        }


        val productQuantity = holder.txtTotalQuentity.text.toString().toInt()
        val productPrice = holder.txtPrice.text.toString().drop(1).toFloat()
        totalProductPrice = (productQuantity * productPrice).toDouble()
        totalPrice = totalPrice + totalProductPrice
        System.out.println("Total Price===" + totalPrice)

        val discountPrice = holder.txtDiscountPrice.text.toString().drop(1).toFloat()
        totalDiscount = discountPrice - totalProductPrice
        netDiscount = netDiscount + totalDiscount

//        (context as CartListActivity).updateCartItem(totalPrice, netDiscount)

        holder.txtDiscountPrice.setPaintFlags(holder.txtDiscountPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)


        holder.imvAddToFavourites.setOnClickListener() {
            holder.imvAddToFavourites.visibility = View.GONE
            holder.imvAddToFavouritesHover.visibility = View.VISIBLE
        }
        holder.imvAddToFavouritesHover.setOnClickListener() {
            holder.imvAddToFavourites.visibility = View.VISIBLE
            holder.imvAddToFavouritesHover.visibility = View.GONE
        }

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
        val txtInrement: TextView = itemView.findViewById(R.id.txtInrement)
        val txtTotalQuentity: TextView = itemView.findViewById(R.id.txtTotalQuentity)
        val txtYouSaved: TextView = itemView.findViewById(R.id.txtYouSaved)
        val txtProSize: TextView = itemView.findViewById(R.id.txtProSize)
        val imvProduct: ImageView = itemView.findViewById(R.id.imvProduct)

        val imvAddToFavourites: ImageView = itemView.findViewById(R.id.imvAddToFavourites)
        val imvAddToFavouritesHover: ImageView = itemView.findViewById(R.id.imvAddToFavouritesHover)
    }
}
