package com.bws.musclefood.favourites

import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.utils.LoadingDialog
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.HttpEntity
import cz.msebera.android.httpclient.entity.StringEntity
import org.json.JSONArray
import org.json.JSONObject

class FavouritesAdapter(val mList: ArrayList<FavouritesListResponseItem>) :
    RecyclerView.Adapter<FavouritesAdapter.ViewHolder>() {

    var context: Context? = null
    var myInt: Int = 1
    var totalPrice: Double = 0.0
    var totalProductPrice: Double = 0.0
    var totalDiscount: Double = 0.0
    var netDiscount: Double = 0.0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_favourites, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemProduct = mList[position]
        holder.txtPName.text = itemProduct.ProductName
        holder.txtPrice.text = "£" + itemProduct.ProductPrice
        // holder.txtQuantity.text = itemProduct.
        //  holder.txtDiscountPrice.text = itemProduct.price
        // holder.txtYouSaved.text = itemProduct.youSaved + "\n" +"SAVED"

        var productImage = itemProduct.ProductImage

        if (productImage !== null) {
            Glide.with(context!!)
                .load(itemProduct.ProductImage)
                .into(holder.imvProduct)
        } else {
            holder.imvProduct.setImageResource(R.drawable.ic_launcher_background)
        }


        holder.txtDeleteProduct.setOnClickListener() {
            mList.removeAt(position)
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

            // (context as FavouritesActivity).updateCartItem(totalPrice, netDiscount)

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

                (context as FavouritesActivity).updateCartItemDecrement()
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

            (context as FavouritesActivity).updateCartItem()
        }


        holder.txtAdd.setOnClickListener {
            // holder.llIncrementDecrement.visibility = View.VISIBLE
            // holder.txtAdd.visibility = View.GONE

            val quantity = holder.txtTotalQuentity.text.toString()
            val price = itemProduct.ProductPrice
            val totalPrice = itemProduct.ProductPrice.toFloat() * quantity.toInt()
            val productId = itemProduct.ProductID


            // Toast.makeText(context, quantity, Toast.LENGTH_SHORT).show()
            updateInsertCart(quantity, price, totalPrice.toString(), itemProduct.ProductID)
        }


        val productQuantity = holder.txtTotalQuentity.text.toString().toInt()
        val productPrice = holder.txtPrice.text.toString().drop(1).toFloat()
        totalProductPrice = (productQuantity * productPrice).toDouble()
        totalPrice = totalPrice + totalProductPrice
        System.out.println("Total Price===" + totalPrice)

        val discountPrice = holder.txtDiscountPrice.text.toString().drop(1).toFloat()
        totalDiscount = discountPrice - totalProductPrice
        netDiscount = netDiscount + totalDiscount

        // (context as FavouritesActivity).updateCartItem(totalPrice, netDiscount)


        holder.txtDiscountPrice.setPaintFlags(holder.txtDiscountPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)



        holder.imvAddToFavourites.setOnClickListener() {
            // holder.imvAddToFavourites.visibility = View.GONE
            // holder.imvAddToFavouritesHover.visibility = View.VISIBLE
            (context as FavouritesActivity).calRemoveFavouritePI(mList[position].ProductID)
        }
        /*  holder.imvAddToFavouritesHover.setOnClickListener() {
              holder.imvAddToFavourites.visibility = View.VISIBLE
              holder.imvAddToFavouritesHover.visibility = View.GONE
          }*/

        // totalFavoritesCartItem = mList.size

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
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
        val txtAdd: TextView = itemView.findViewById(R.id.txtAdd)
        val txtInrement: TextView = itemView.findViewById(R.id.txtInrement)
        val txtTotalQuentity: TextView = itemView.findViewById(R.id.txtTotalQuentity)
        val txtYouSaved: TextView = itemView.findViewById(R.id.txtYouSaved)
        val imvProduct: ImageView = itemView.findViewById(R.id.imvProduct)
        val llIncrementDecrement: LinearLayout = itemView.findViewById(R.id.llIncrementDecrement)

        val imvAddToFavourites: ImageView = itemView.findViewById(R.id.imvAddToFavourites)
        val imvAddToFavouritesHover: ImageView = itemView.findViewById(R.id.imvAddToFavouritesHover)
    }


    fun updateInsertCart(quantity: String, price: String, totalPrice: String, productId: String) {
        val client = AsyncHttpClient()
        val jsonArray = JSONArray()

        val loadingDialog = LoadingDialog.progressDialog(context!!)
        //  var keys = Constant.hashMap.keys
        // for (key in keys) {
        var jsonObject = JSONObject()
        // var cartModel = Constant.hashMap.get(key)
        try {
            jsonObject.put("CartItemID", "")
            jsonObject.put("Price", price)
            jsonObject.put("ProductID", productId)
            jsonObject.put("Quantity", quantity)
            jsonObject.put("SessionID", Constant.sessionID);
            jsonObject.put("TotalPrice", totalPrice)
            jsonObject.put("UserID", "2")
            jsonArray.put(jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //  }

        println("CART INSERT ==" + jsonArray.toString())

        val entity: HttpEntity
        entity = try {
            StringEntity(jsonArray.toString(), "UTF-8")
        } catch (e: IllegalArgumentException) {
            Log.d("HTTP", "StringEntity: IllegalArgumentException")
            return
        }
        val contentType = "application/json; charset=utf-8"
        client.post(
            context,
            Constant.BASE_URL + "InsertUpdateCartDetails",
            entity,
            contentType,
            object : AsyncHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<Header>,
                    responseBody: ByteArray
                ) {
                    val asyncResult = String(responseBody)
                    Toast.makeText(context, asyncResult, Toast.LENGTH_SHORT).show()
                    // startActivity(Intent(this@ProductListActivity, CartListActivity::class.java))
                }

                override fun onStart() {
                    super.onStart()

                    loadingDialog.show()
                }

                override fun onFinish() {
                    super.onFinish()
                    loadingDialog.dismiss()
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<Header>,
                    responseBody: ByteArray,
                    error: Throwable
                ) {
                    loadingDialog.dismiss()
                    Toast.makeText(context, statusCode.toString(), Toast.LENGTH_SHORT).show()
                }
            })
    }
}
