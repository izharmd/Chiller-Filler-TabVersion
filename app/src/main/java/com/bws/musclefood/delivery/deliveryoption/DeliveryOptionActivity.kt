package com.bws.musclefood.delivery.deliveryoption

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.delivery.AddNewAddressActivity
import com.bws.musclefood.delivery.choosedeliveryaddress.ChooseDeliveryAddressActivity
import com.bws.musclefood.delivery.deliveryoption.viewcartItems.ViewCartItemAdapter
import com.bws.musclefood.itemcategory.cartlist.CartListActivity
import com.bws.musclefood.itemcategory.cartlist.CartListModel
import com.bws.musclefood.itemcategory.productlist.addquentity.QuentityAdapater
import com.bws.musclefood.itemcategory.productlist.addquentity.QuentityModel
import com.bws.musclefood.payment.PaymentActivity
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration
import kotlinx.android.synthetic.main.accitivity_delivery_option.*
import kotlinx.android.synthetic.main.tool_bar_address.*

class DeliveryOptionActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.accitivity_delivery_option)
        supportActionBar?.hide()
        imvSaveaddress.visibility = View.GONE
        txtTxtHeader.text = "Delivery Options"

        txtChangeAddress.setOnClickListener(){
            startActivity(Intent(this@DeliveryOptionActivity, ChooseDeliveryAddressActivity::class.java))
        }

        txtViewAllItems.setOnClickListener(){
            //dialogViewProduct("qwertyu")
            startActivity(Intent(this@DeliveryOptionActivity, CartListActivity::class.java))
        }

        txtProceedToPay.setOnClickListener(){
            startActivity(Intent(this@DeliveryOptionActivity, PaymentActivity::class.java))
        }

        imvBack.setOnClickListener(){
            finish()
        }
    }

    fun dialogViewProduct(pName:String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_view_cart_item)
        dialog.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val recyQuentity: RecyclerView = dialog.findViewById(R.id.recyQuentity)
        val imv_cross: ImageView = dialog.findViewById(R.id.imv_cross)
        val txtProductName: TextView = dialog.findViewById(R.id.txtProductName)
        txtProductName.text = Constant.addDataToCart.size.toString() +" Items" + "Total : £ 450.90"

        recyQuentity.layoutManager = LinearLayoutManager(this)

        val dividerDrawable =
            ContextCompat.getDrawable(this, R.drawable.line_divider)
        recyQuentity.addItemDecoration(DividerItemDecoration(dividerDrawable))


        val adapterTop = ViewCartItemAdapter(Constant.addDataToCart)
        recyQuentity.adapter = adapterTop
        adapterTop.notifyDataSetChanged()

        imv_cross.setOnClickListener(){
            dialog.dismiss()
        }

        dialog.show()
    }
}