package com.bws.musclefood.itemcategory.cartlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.common.Constant.Companion.totalCartItem
import com.bws.musclefood.delivery.deliveryoption.DeliveryOptionActivity
import com.bws.musclefood.urils.AlertDialog
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_cart_list.*
import kotlinx.android.synthetic.main.activity_productlist.*
import kotlinx.android.synthetic.main.tool_bar.*
import kotlinx.android.synthetic.main.tool_bar.txtLogInSignUp
import kotlinx.android.synthetic.main.tool_bar_address.*
import kotlinx.android.synthetic.main.tool_bar_address.imvBack
import kotlinx.android.synthetic.main.tool_bar_cart_details.*
import kotlinx.android.synthetic.main.tool_bar_search_view.*
import kotlinx.android.synthetic.main.tool_bar_search_view.imvSearch
import java.text.NumberFormat
import java.util.*

class CartListActivity : AppCompatActivity() {

    var totalOrderValue = 0f
    var t_orderValue = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_list)
        supportActionBar?.hide()

        txtOrderNo.visibility = View.GONE

        txtLogInSignUp.text = "Cart Details"
        recyCartList.layoutManager = LinearLayoutManager(this)

        val dividerDrawable =
            ContextCompat.getDrawable(applicationContext, R.drawable.line_divider)
        recyCartList.addItemDecoration(DividerItemDecoration(dividerDrawable))

        val adapter = CartListAdapter(Constant.addDataToCart)
        recyCartList.adapter = adapter
        adapter.notifyDataSetChanged()

        imvSearch.setOnClickListener() {
            searchView1.visibility = View.VISIBLE
        }

        txtCheckOut.setOnClickListener() {

            if (totalOrderValue >= 80) {
                startActivity(Intent(this@CartListActivity, DeliveryOptionActivity::class.java))
            } else {
                AlertDialog().dialog(
                    this,
                    "Minimum cart value should be ??80 or more to place order."
                )
            }


        }

        imvBack.setOnClickListener() {
            finish()
        }
    }

    fun updateCartItem(totalPrice: Double, netDiscount: Double) {

        val currencyFormatter = NumberFormat.getCurrencyInstance()
        txtTotalPrice.text = "?? " + currencyFormatter.format(totalPrice).toString()
        txtTotalPrice.text = "?? " + currencyFormatter.format(totalPrice).toString().drop(1)
        txtTotalSave.text = "?? " + currencyFormatter.format(netDiscount).toString().drop(1)

        txtCartValue.text = totalCartItem.toString()
        totalOrderValue = currencyFormatter.format(totalPrice).toString().drop(1).toFloat()


        var orderValue = 80.00 - totalOrderValue

        txtAddWorth.text = "??"+orderValue.toString() +"0"+ " worth products before place order"

    }

}