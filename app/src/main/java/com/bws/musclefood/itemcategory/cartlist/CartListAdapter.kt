package com.bws.musclefood.itemcategory.cartlist

import android.app.Activity
import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.database.AppDatabase
import com.bws.musclefood.utils.AlertDialog
import com.bws.musclefood.viewmodels.RemoveProductViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartListAdapter(/*val textView: TextView,*/val mList: ArrayList<CartListResponseItem>,val db:AppDatabase) :
    RecyclerView.Adapter<CartListAdapter.ViewHolder>() {

    var context: Context? = null
    var myInt: Int = 1
    var totalPrice: Double = 0.0
    var totalProductPrice: Double = 0.0
    var totalDiscount: Double = 0.0
    var netDiscount: Double = 0.0
    var priceFormatted: Double = 0.0
    var totalPriceFormatted: Double = 0.0

    lateinit var removeProductViewModel: RemoveProductViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart_list, parent, false)
        context = parent.context

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemProduct = mList[position]
        holder.txtPName.text = itemProduct.ProductName
        holder.txtProSize.text = itemProduct.ProductSize
        holder.txtPrice.text = "£" + itemProduct.Price
        holder.txtTotalQuentity.text = itemProduct.Quantity
        var productImage = itemProduct.ProductImageName



        var favouritesFlag = itemProduct.FavoriteFlag

        if (favouritesFlag == "Y") {
            holder.imvAddToFavourites.setImageResource(R.drawable.favorite_24)
            holder.imvAddToFavourites.visibility = View.VISIBLE
            holder.imvAddToFavouritesHover.visibility = View.GONE
        } else {
            holder.imvAddToFavourites.setImageResource(R.drawable.favorite_hover)
            holder.imvAddToFavourites.visibility = View.GONE
            holder.imvAddToFavouritesHover.visibility = View.VISIBLE
        }



        priceFormatted = itemProduct.FormattedProductTotalPrice.drop(1).toDouble()

        totalPriceFormatted = totalPriceFormatted + priceFormatted

        if (productImage !== null) {
            Glide.with(context!!)
                .load(itemProduct.ProductImageName)
                .into(holder.imvProduct)
        } else {
            holder.imvProduct.setImageResource(R.drawable.ic_launcher_background)
        }




        holder.txtDeleteProduct.setOnClickListener {
            (context as CartListActivity).removeProduct(itemProduct.ProductID)
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
                Constant.TotalPrice = Constant.TotalPrice - productPrice
                System.out.println("Total Price===" + productQuantity)

                val discountPrice = holder.txtDiscountPrice.text.toString().drop(1).toFloat()
                totalDiscount = (discountPrice - productPrice).toDouble()
                netDiscount = netDiscount - totalDiscount

               (context as CartListActivity).updateCartItem(Constant.TotalPrice, netDiscount)

                (context as CartListActivity).cartItemDecrement()

                GlobalScope.launch(Dispatchers.Main) {
                    db.contactDao().updateProductItems(holder.txtTotalQuentity.text.toString(),itemProduct.ProductID)
                    val dt =  db.contactDao().getProductItem()
                    Log.d("ALL DATA===",dt.toString())
                }

            }
        }

        holder.txtInrement.setOnClickListener() {
            myInt = holder.txtTotalQuentity.text.toString().toInt()
            myInt++

            if (myInt <= 10) {
                holder.txtTotalQuentity.text = myInt.toString()

                val productQuantity = holder.txtTotalQuentity.text.toString().toInt()
                val productPrice = holder.txtPrice.text.toString().drop(1).toFloat()
                Constant.TotalPrice = Constant.TotalPrice + productPrice
                System.out.println("Total Price===" + productQuantity)

                val discountPrice = holder.txtDiscountPrice.text.toString().drop(1).toFloat()
                totalDiscount = (discountPrice - productPrice).toDouble()
                netDiscount = netDiscount + totalDiscount

                (context as CartListActivity).updateCartItem(Constant.TotalPrice, netDiscount)
                (context as CartListActivity).cartItemIncrement()

                GlobalScope.launch(Dispatchers.Main) {
                    db.contactDao().updateProductItems(holder.txtTotalQuentity.text.toString(),itemProduct.ProductID)
                    val dt =  db.contactDao().getProductItem()
                    Log.d("ALL DATA===",dt.toString())
                }

            } else {
                AlertDialog().dialog(context as Activity, "Can not add quantity more than 10 ")
            }
        }


        val productQuantity = holder.txtTotalQuentity.text.toString().toInt()
        val productPrice = holder.txtPrice.text.toString().drop(1).toFloat()
        totalProductPrice = (productQuantity * productPrice).toDouble()
        totalPrice = totalPrice + totalProductPrice
        System.out.println("Total Price===" + totalPrice)

        val discountPrice = holder.txtDiscountPrice.text.toString().drop(1).toFloat()
        totalDiscount = discountPrice - totalProductPrice
        netDiscount = netDiscount + totalDiscount

        //   (context as CartListActivity).updateCartItem(totalPriceFormatted, netDiscount)


        // textView.text = totalPriceFormatted.toString()


        holder.txtDiscountPrice.setPaintFlags(holder.txtDiscountPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)


        holder.imvAddToFavourites.setOnClickListener() {
            holder.imvAddToFavourites.setImageResource(R.drawable.favorite_24)
            holder.imvAddToFavouritesHover.setImageResource(R.drawable.favorite_hover)
            holder.imvAddToFavourites.visibility = View.GONE
            holder.imvAddToFavouritesHover.visibility = View.VISIBLE
            (context as CartListActivity).calRemoveFavouritePI(mList[position].ProductID)
        }
        holder.imvAddToFavouritesHover.setOnClickListener() {
            holder.imvAddToFavourites.setImageResource(R.drawable.favorite_24)
            holder.imvAddToFavouritesHover.setImageResource(R.drawable.favorite_hover)
            holder.imvAddToFavourites.visibility = View.VISIBLE
            holder.imvAddToFavouritesHover.visibility = View.GONE
            (context as CartListActivity).calAddFavouritePI(mList[position].ProductID)
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }


    override fun getItemViewType(position: Int): Int {
        return position
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
