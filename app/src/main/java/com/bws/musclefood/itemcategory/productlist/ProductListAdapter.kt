package com.bws.musclefood.itemcategory.productlist

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.common.Constant.Companion.hashMap
import com.bws.musclefood.common.Constant.Companion.quantity
import com.bws.musclefood.common.Constant.Companion.totalCartItem
import com.bws.musclefood.itemcategory.cartlist.CartListModel
import com.bws.musclefood.itemcategory.productlist.addquentity.QuentityAdapater
import com.bws.musclefood.itemcategory.productlist.addquentity.QuentityModel
import com.bws.musclefood.itemcategory.rating.RatingActivity
import com.bws.musclefood.itemcategory.rating.RatingAdapter
import com.bws.musclefood.itemcategory.rating.RatingModel
import com.bws.musclefood.productdetails.ProductDetailsActivity
import com.bws.musclefood.utils.AlertDialog
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration


class ProductListAdapter(val mList: ProductListResponse) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    var context: Context? = null
    var myInt: Int = 0

    val arrItem = ArrayList<String>()

    var cartItem:Int = 0

    //   var hashMap = HashMap<String, CartListModel>() //define empty hashmap

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_product_list, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemProduct = mList[position]
        holder.txtProductName.text = itemProduct.ProductName
        holder.txtSizeOfProduct.text = itemProduct.ProductSize

        holder.txtPrice.text = itemProduct.ProductPriceFormatted

       if(position == 0){

           Constant.mainCategory = "Retail Ready"
           Constant.categoryId = itemProduct.CategoryID
           Constant.categoryName = itemProduct.CategoryName
       }

        var productImage = itemProduct.ProductImage

        if (productImage !== null) {
            Glide.with(context!!)
                .load(itemProduct.ProductImage)
                .into(holder.imvProduct)
        } else {
            holder.imvProduct.setImageResource(R.drawable.ic_launcher_background)
        }


        var favouritesFlag = itemProduct.FavoriteFlag

        if(favouritesFlag == "Y"){
            holder.imvAddToFavourites.setImageResource(R.drawable.favorite_24)
            holder.imvAddToFavourites.visibility = View.VISIBLE
            holder.imvAddToFavouritesHover.visibility = View.GONE
        }else{
            holder.imvAddToFavourites.setImageResource(R.drawable.favorite_hover)
            holder.imvAddToFavourites.visibility = View.GONE
            holder.imvAddToFavouritesHover.visibility = View.VISIBLE
        }

        holder.txtDiscountPrice.setPaintFlags(holder.txtDiscountPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)


        holder.txtAdd.setOnClickListener() {

            myInt = 1
            totalCartItem = totalCartItem + 1

            cartItem = cartItem + 1

            (context as ProductListActivity).updateCartItem(cartItem)

            val itm = mList[position]

            holder.llIncrementDecrement.visibility = View.VISIBLE
            holder.txtAdd.visibility = View.GONE

            if (hashMap.isEmpty()) {
                hashMap.put(
                    itm.ProductID, CartListModel(
                        itm.ProductImage,
                        itm.ProductName,
                        "1",
                        itm.ProductPrice,
                        itm.ProductID
                    )
                )
            } else {
                if (!hashMap.containsKey(itm.ProductID)) {
                    hashMap.put(
                        itm.ProductID, CartListModel(
                            itm.ProductImage,
                            itm.ProductName,
                            "1",
                            itm.ProductPrice,
                            itm.ProductID
                        )
                    )
                }
            }


            arrItem.add(itemProduct.ProductName)
        }

        holder.txtQuantity.setOnClickListener() {
            val pName = itemProduct.ProductName
            dialogViewProduct(pName)
        }

        holder.txtDecrement.setOnClickListener() {
            myInt = holder.txtTotalQuentity.text.toString().toInt()
            val quantity = holder.txtTotalQuentity.text.toString().toInt()
            if(quantity > 1){
                holder.txtTotalQuentity.text =  (holder.txtTotalQuentity.text.toString().toInt() - 1).toString()
                cartItem = cartItem-1
                (context as ProductListActivity).updateCartItem(cartItem)
            }

            if (myInt <= 1) {
                myInt = 1
            } else {
                myInt--
                val itm = mList[position]
                if (hashMap.containsKey(itm.ProductID)) {
                    hashMap.remove(
                        itm.ProductID, CartListModel(
                            itm.ProductImage,
                            itm.ProductName,
                            holder.txtTotalQuentity.text.toString(),
                            itm.ProductPrice,
                            itm.ProductID
                        )
                    )
                }
            }

            val itmProduct = holder.txtTotalQuentity.text.toString().toInt()
            if (itmProduct == 0) {
                holder.txtAdd.visibility = View.VISIBLE
                holder.llIncrementDecrement.visibility = View.GONE
            }

            if (totalCartItem > 0) {
                totalCartItem = totalCartItem - 1
                // (context as ProductListActivity).updateCartItem(totalCartItem)
            }
        }

        holder.txtInrement.setOnClickListener() {
            myInt++

            val quantity = holder.txtTotalQuentity.text.toString().toInt() + 1

            if (quantity <= 10) {
                holder.txtTotalQuentity.text =  (holder.txtTotalQuentity.text.toString().toInt() + 1).toString()
                val itm = mList[position]

                if (hashMap.containsKey(itm.ProductID)) {
                    hashMap.replace(
                        itm.ProductID, CartListModel(
                            itm.ProductImage,
                            itm.ProductName,
                            holder.txtTotalQuentity.text.toString(),
                            itm.ProductPrice,
                            itm.ProductID
                        )
                    )
                    totalCartItem = totalCartItem + 1
                    cartItem = cartItem + 1
                    (context as ProductListActivity).updateCartItem(cartItem)
                }
            } else {
                AlertDialog().dialog(context as Activity, "Can not add quantity more than 10 ")
            }
        }

        holder.llRating.setOnClickListener() {
            context?.startActivity(Intent(context, RatingActivity::class.java))
            // dialogRating(itemProduct.productName)
        }

        holder.imvAddToFavourites.setOnClickListener() {
            // holder.imvAddToFavourites.setImageResource(R.drawable.favorite_24)
            holder.imvAddToFavourites.visibility = View.GONE
            holder.imvAddToFavouritesHover.visibility = View.VISIBLE
            val itm = mList[position]
            val pId = itm.ProductID
            (context as ProductListActivity).calRemoveFavouritePI(pId)
        }


         holder.imvAddToFavouritesHover.setOnClickListener() {
             holder.imvAddToFavourites.visibility = View.VISIBLE
             holder.imvAddToFavouritesHover.visibility = View.GONE

             val itm = mList[position]
             val pId = itm.ProductID
             (context as ProductListActivity).calAddFavouritePI(pId)
         }


        holder.imvProduct.setOnClickListener() {
            quantity = myInt.toString()
            context?.startActivity(Intent(context, ProductDetailsActivity::class.java))
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {


        val txtProductName: TextView = itemView.findViewById(R.id.txtProductName)
        val txtSizeOfProduct: TextView = itemView.findViewById(R.id.txtSizeOfProduct)
        val txtQuantity: TextView = itemView.findViewById(R.id.txtQuantity)
        val txtPrice: TextView = itemView.findViewById(R.id.txtPrice)
        val txtDiscountPrice: TextView = itemView.findViewById(R.id.txtDiscountPrice)
        val txtAdd: TextView = itemView.findViewById(R.id.txtAdd)
        val txtDecrement: TextView = itemView.findViewById(R.id.txtDecrement)
        val txtInrement: TextView = itemView.findViewById(R.id.txtInrement)
        val txtTotalQuentity: TextView = itemView.findViewById(R.id.txtTotalQuentity)
        val txtOffer: TextView = itemView.findViewById(R.id.txtOffer)
        val txtRatingPoint: TextView = itemView.findViewById(R.id.txtRatingPoint)
        val txtRating: TextView = itemView.findViewById(R.id.txtRating)
        val txtWriteReview: TextView = itemView.findViewById(R.id.txtWriteReview)
        val txtOutOfStock: TextView = itemView.findViewById(R.id.txtOutOfStock)
        val txtNotiFyme: TextView = itemView.findViewById(R.id.txtNotiFyme)

        val imvAddToFavourites: ImageView = itemView.findViewById(R.id.imvAddToFavourites)
        val imvAddToFavouritesHover: ImageView = itemView.findViewById(R.id.imvAddToFavouritesHover)
        val imvProduct: ImageView = itemView.findViewById(R.id.imvProduct)
        val llIncrementDecrement: LinearLayout = itemView.findViewById(R.id.llIncrementDecrement)
        val llRating: LinearLayout = itemView.findViewById(R.id.llRating)

    }

    fun dialogViewProduct(pName: String) {
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_quentity)
        dialog.getWindow()
            ?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val recyQuentity: RecyclerView = dialog.findViewById(R.id.recyQuentity)
        val imv_cross: ImageView = dialog.findViewById(R.id.imv_cross)
        val txtProductName: TextView = dialog.findViewById(R.id.txtProductName)
        txtProductName.text = pName

        recyQuentity.layoutManager = LinearLayoutManager(context)
        val quentityModel = ArrayList<QuentityModel>()


        quentityModel.add(QuentityModel("1 Kg - ", "£ 110.50", "£ 90.00"))
        quentityModel.add(QuentityModel("5 Kg - ", "£ 320.50", "£ 240.00"))

        val dividerDrawable =
            ContextCompat.getDrawable(context!!, R.drawable.line_divider)
        recyQuentity.addItemDecoration(DividerItemDecoration(dividerDrawable))


        val adapterTop = QuentityAdapater(quentityModel)
        recyQuentity.adapter = adapterTop
        adapterTop.notifyDataSetChanged()

        imv_cross.setOnClickListener() {
            dialog.dismiss()
        }

        dialog.show()
    }


    //    REVIEW DIALOG
    fun dialogRating(pName: String) {
        val dialog = Dialog(context!!, R.style.NewDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_rating)
        dialog.getWindow()
            ?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val recvRating: RecyclerView = dialog.findViewById(R.id.recvRating)
        val imv_cross: ImageView = dialog.findViewById(R.id.imv_cross)
        val txtProductName: TextView = dialog.findViewById(R.id.txtProductName)
        val txtSubmitReview: TextView = dialog.findViewById(R.id.txtSubmitReview)
        val txtProductName_1: TextView = dialog.findViewById(R.id.txtProductName_1)
        val txtRatingCount: TextView = dialog.findViewById(R.id.txtRatingCount)
        val reviewRating: RatingBar = dialog.findViewById(R.id.reviewRating)
        val reviewRating_1: RatingBar = dialog.findViewById(R.id.reviewRating_1)

        reviewRating.rating = 4f
        reviewRating_1.rating = 4f

        txtProductName.text = pName
        txtProductName_1.text = pName
        txtRatingCount.text = "4"

        recvRating.layoutManager = LinearLayoutManager(context)
        val ratingModel = ArrayList<RatingModel>()
        ratingModel.add(RatingModel(pName, 4, "Nice Product like it", "4 Mar 2022"))
        ratingModel.add(RatingModel(pName, 4, "Good..", "8 Feb 2022"))
        ratingModel.add(RatingModel(pName, 4, "Its imaging..", "2 Mar 2022"))
        val dividerDrawable =
            ContextCompat.getDrawable(context!!, R.drawable.line_divider)
        recvRating.addItemDecoration(DividerItemDecoration(dividerDrawable))


        val adapterTop = RatingAdapter(ratingModel)
        recvRating.adapter = adapterTop
        adapterTop.notifyDataSetChanged()

        txtSubmitReview.setOnClickListener() {
            // dialog.dismiss()
        }

        imv_cross.setOnClickListener() {
            dialog.dismiss()
        }

        dialog.show()
    }


}