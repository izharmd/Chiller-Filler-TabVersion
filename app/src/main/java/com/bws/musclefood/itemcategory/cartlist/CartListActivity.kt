package com.bws.musclefood.itemcategory.cartlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.delivery.AddNewAddressActivity
import com.bws.musclefood.delivery.deliveryoption.DeliveryOptionActivity
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_cart_list.*
import kotlinx.android.synthetic.main.activity_productlist.*
import kotlinx.android.synthetic.main.tool_bar.*
import kotlinx.android.synthetic.main.tool_bar.txtLogInSignUp
import kotlinx.android.synthetic.main.tool_bar_search_view.*

class CartListActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_list)
        supportActionBar?.hide()

       // txtLogInSignUp.visibility = View.GONE
        txtLogInSignUp.text = "Cart Details"
      //  txtLogInSignUp.setBackgroundColor(resources.getColor(R.color.gray));
      //  txtLogInSignUp.setTextColor(resources.getColor(R.color.black))

        constraintLayCart.visibility = View.INVISIBLE


        recyCartList.layoutManager = LinearLayoutManager(this)
       /* val data = ArrayList<CartListModel>()

        data.add(CartListModel(R.drawable.potato,"Potato","1 Kg","£ 150.00","£ 55.00","£ 95.00\n SAVED"))
        data.add(CartListModel(R.drawable.sweet_corn,"Sweet Corn","500 g","£ 120.00","£ 25.00","£ 95.00\nSAVED"))
       data.add(CartListModel(R.drawable.baby_potato,"Baby potato","5 Kg","£ 250.00","£ 45.00","£ 205.00\n SAVED"))
       data.add(CartListModel(R.drawable.banana_nendran,"Banana - Nendran","200 g","£ 60.00","£ 55.00","£ 10.00\n SAVED"))
     */

      // data.add(CartListModel(R.drawable.mosambi_economy,"Mosambi - Economy","750 g","£ 50.00","£ 30.60"))
        // data.add(ProductListModel(R.drawable.potato,"Potato","£150.00","£50.00","5 hrs","FRESHO","5 kg","50% OFF"))

        val dividerDrawable =
            ContextCompat.getDrawable(applicationContext, R.drawable.line_divider)
        recyCartList.addItemDecoration(DividerItemDecoration(dividerDrawable))

        val adapter = CartListAdapter(Constant.addDataToCart)
        recyCartList.adapter = adapter
        adapter.notifyDataSetChanged()

        imvSearch.setOnClickListener(){
            searchView1.visibility = View.VISIBLE
        }

        txtCheckOut.setOnClickListener(){
            startActivity(Intent(this@CartListActivity, DeliveryOptionActivity::class.java))
        }
    }
}