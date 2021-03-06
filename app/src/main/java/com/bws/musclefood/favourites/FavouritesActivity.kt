package com.bws.musclefood.favourites

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
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_cart_list.*
import kotlinx.android.synthetic.main.tool_bar.*
import kotlinx.android.synthetic.main.tool_bar.txtLogInSignUp
import kotlinx.android.synthetic.main.tool_bar_address.*
import kotlinx.android.synthetic.main.tool_bar_address.imvBack
import kotlinx.android.synthetic.main.tool_bar_cart_details.*
import kotlinx.android.synthetic.main.tool_bar_search_view.*
import kotlinx.android.synthetic.main.tool_bar_search_view.imvSearch
import java.text.NumberFormat
import java.util.*

class FavouritesActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)
        supportActionBar?.hide()

        // txtLogInSignUp.visibility = View.GONE
        txtLogInSignUp.text = "Favourite Items"
        //  txtLogInSignUp.setBackgroundColor(resources.getColor(R.color.gray));
        //  txtLogInSignUp.setTextColor(resources.getColor(R.color.black))

        //  constraintLayCart.visibility = View.INVISIBLE


        recyCartList.layoutManager = LinearLayoutManager(this)

        val dividerDrawable =
            ContextCompat.getDrawable(applicationContext, R.drawable.line_divider)
        recyCartList.addItemDecoration(DividerItemDecoration(dividerDrawable))

        val adapter = FavouritesAdapter(Constant.arrFavourites)
        recyCartList.adapter = adapter
        adapter.notifyDataSetChanged()

        imvSearch.setOnClickListener(){
            searchView1.visibility = View.VISIBLE
        }

        txtCheckOut.setOnClickListener(){
            startActivity(Intent(this@FavouritesActivity, DeliveryOptionActivity::class.java))
        }

        imvBack.setOnClickListener(){
            finish()
        }
    }

    fun updateCartItem(totalPrice: Double, netDiscount: Double){
        val currentLocale = Locale.UK
        val currencyFormatter = NumberFormat.getCurrencyInstance()
        //txtTotalPrice.text = "?? " + currencyFormatter.format(totalPrice).toString()
        //txtTotalPrice.text = "?? " + currencyFormatter.format(totalPrice).toString().drop(1)
        txtTotalPrice.text = "?? 7.10"
        txtTotalSave.text = "?? 9.40"
        txtCartValue.text = "2"

        //txtCartValue.text = Constant.totalCartItem.toString()
    }
}