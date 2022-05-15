package com.bws.musclefood.orders.vieworderdetails

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.favourites.FavouritesAdapter
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_cart_list.*
import kotlinx.android.synthetic.main.activity_cart_list.searchView1
import kotlinx.android.synthetic.main.activity_view_order.*
import kotlinx.android.synthetic.main.tool_bar.*
import kotlinx.android.synthetic.main.tool_bar_search_view.*
import kotlinx.android.synthetic.main.tool_bar_search_view.imvSearch
import kotlinx.android.synthetic.main.tool_bar_search_view.txtLogInSignUp
import kotlinx.android.synthetic.main.tool_bar_view_order.*

class ViewOrderDetailsActivity:AppCompatActivity() {

    val arrViewOrder = ArrayList<ViewOrderModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_order)

        supportActionBar?.hide()
        txtLogInSignUp.text = "View Order"


        recyViewOrder.layoutManager = LinearLayoutManager(this)

        val dividerDrawable =
            ContextCompat.getDrawable(applicationContext, R.drawable.line_divider)
        recyViewOrder.addItemDecoration(DividerItemDecoration(dividerDrawable))


        val adapter = ViewOrderAdapter(Constant.orderItem)
        recyViewOrder.adapter = adapter
        adapter.notifyDataSetChanged()

        imvSearch.setOnClickListener(){
            searchView1.visibility = View.VISIBLE
        }

        imvBack.setOnClickListener {
            finish()
        }
    }
}