package com.bws.musclefood.delivery.deliveryoption.viewcartItems

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant.Companion.cartItem
import com.bws.musclefood.database.AppDatabase
import com.bws.musclefood.itemcategory.cartlist.CartListAdapter
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_cart_list.*
import kotlinx.android.synthetic.main.activity_view_item.*
import kotlinx.android.synthetic.main.activity_view_item.recyCartList
import kotlinx.android.synthetic.main.tool_bar_cart_details.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ViewItemActivity : AppCompatActivity() {

    lateinit var db:AppDatabase
    lateinit var viewCartItemAdapter: ViewCartItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_item)
        supportActionBar?.hide()
        db = AppDatabase(this)

        imvCart.visibility = View.VISIBLE

        GlobalScope.launch {
            val arrCartList = db.contactDao().getProductItem()

            txtCartValue.text = cartItem.toString()
            txtLogInSignUp.text = "Basket"


            recyCartList.layoutManager = LinearLayoutManager(this@ViewItemActivity)
            val dividerDrawable =
                ContextCompat.getDrawable(applicationContext, R.drawable.line_divider)
            recyCartList.addItemDecoration(DividerItemDecoration(dividerDrawable))
            viewCartItemAdapter = ViewCartItemAdapter(arrCartList)
            recyCartList.adapter = viewCartItemAdapter
            viewCartItemAdapter.notifyDataSetChanged()

            imvBack.setOnClickListener {
                finish()
            }
        }

    }
}