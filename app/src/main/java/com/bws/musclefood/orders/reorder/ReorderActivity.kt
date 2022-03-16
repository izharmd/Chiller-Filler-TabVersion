package com.bws.musclefood.orders.reorder

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.delivery.deliveryoption.DeliveryOptionActivity
import com.bws.musclefood.itemcategory.cartlist.CartListAdapter
import com.bws.musclefood.itemcategory.productlist.ProductListModel
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_cart_list.*
import kotlinx.android.synthetic.main.tool_bar.*
import kotlinx.android.synthetic.main.tool_bar.txtLogInSignUp
import kotlinx.android.synthetic.main.tool_bar_address.*
import kotlinx.android.synthetic.main.tool_bar_address.imvBack
import kotlinx.android.synthetic.main.tool_bar_cart_details.*
import kotlinx.android.synthetic.main.tool_bar_search_view.*
import kotlinx.android.synthetic.main.tool_bar_search_view.imvSearch

class ReorderActivity:AppCompatActivity() {

    var arrReorder = ArrayList<ReorderModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_list)

        supportActionBar?.hide()


        txtLogInSignUp.text = "Reorder"
        txtCartValue.text = "2"
        txtOrderNo.text = "Order Number : "+Constant.orderNo



        recyCartList.layoutManager = LinearLayoutManager(this)

        val dividerDrawable =
            ContextCompat.getDrawable(applicationContext, R.drawable.line_divider)
        recyCartList.addItemDecoration(DividerItemDecoration(dividerDrawable))

        arrReorder.add(ReorderModel(R.drawable.cheken1,"Suppergreen Stuffed Chicken Breasted","2","3.55","4.50","2"))
        arrReorder.add(ReorderModel(R.drawable.beef1,"Heritage Fillet Steaks","2","4.50","12.00","2"))


        val adapter = ReorderAdapter(arrReorder)
        recyCartList.adapter = adapter
        adapter.notifyDataSetChanged()

        imvSearch.setOnClickListener(){
            searchView1.visibility = View.VISIBLE
        }

        txtCheckOut.setOnClickListener(){
            startActivity(Intent(this@ReorderActivity, DeliveryOptionActivity::class.java))
        }

        imvBack.setOnClickListener(){
            finish()
        }
    }
}