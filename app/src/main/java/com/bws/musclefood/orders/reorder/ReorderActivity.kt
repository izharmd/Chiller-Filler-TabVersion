package com.bws.musclefood.orders.reorder

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.common.Constant.Companion.orderType
import com.bws.musclefood.database.AppDatabase
import com.bws.musclefood.delivery.deliveryoption.DeliveryOptionActivity
import com.bws.musclefood.factory.FactoryProvider
import com.bws.musclefood.itemcategory.cartlist.CartListAdapter
import com.bws.musclefood.itemcategory.productlist.ProductListModel
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.LoadingDialog
import com.bws.musclefood.utils.PreferenceConnector
import com.bws.musclefood.utils.Resources
import com.bws.musclefood.viewmodels.AddNewAddressViewModel
import com.bws.musclefood.viewmodels.ReorderViewModel
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_cart_list.*
import kotlinx.android.synthetic.main.tool_bar.*
import kotlinx.android.synthetic.main.tool_bar.txtLogInSignUp
import kotlinx.android.synthetic.main.tool_bar_address.*
import kotlinx.android.synthetic.main.tool_bar_address.imvBack
import kotlinx.android.synthetic.main.tool_bar_cart_details.*
import kotlinx.android.synthetic.main.tool_bar_search_view.*
import kotlinx.android.synthetic.main.tool_bar_search_view.imvSearch
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ReorderActivity : AppCompatActivity() {

    var qty = 0

    var totalOrderPrice = 0.00
    lateinit var reorderViewModel: ReorderViewModel
    lateinit var preferenceConnector: PreferenceConnector

    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_list)

        supportActionBar?.hide()

        txtEmptyBaskets.visibility = View.GONE

        preferenceConnector = PreferenceConnector(this)
        db = AppDatabase(this)


        txtLogInSignUp.text = "Reorder"
        txtCartValue.text = "2"
        txtOrderNo.text = "Order Number : " + Constant.orderNumber



        recyCartList.layoutManager = LinearLayoutManager(this)

        val dividerDrawable =
            ContextCompat.getDrawable(applicationContext, R.drawable.line_divider)
        recyCartList.addItemDecoration(DividerItemDecoration(dividerDrawable))


        imvSearch.setOnClickListener() {
            searchView1.visibility = View.VISIBLE
        }

        txtCheckOut.setOnClickListener() {
            orderType = "REORDER"
            startActivity(Intent(this@ReorderActivity, DeliveryOptionActivity::class.java))
        }

        imvBack.setOnClickListener() {
            finish()
        }
        reorder()
    }

    fun reorder() {
        val body = RequestBodies.ReorderBody(
            preferenceConnector.getValueString("USER_ID").toString(),
            Constant.orderNo
        )

        reorderViewModel = ViewModelProvider(
            this,
            FactoryProvider(Repository(), this)
        ).get(ReorderViewModel::class.java)

        reorderViewModel.getReorder(body)
        val loadingDialog = LoadingDialog.progressDialog(this)

        reorderViewModel.resultReroder.observe(this) {

            when (it) {

                is Resources.Loading -> {

                }
                is Resources.NoInternet -> {


                }

                is Resources.Success -> {

                    totalOrderPrice = it.data!![0].OrderAmount.drop(2).toDouble()
                    txtTotalPrice.text = "£" +totalOrderPrice.toString() + "0"

                    if (totalOrderPrice >= 60.00) {
                        txtAddWorth.text = "£00.00 away form meeting the minimum spend."
                    } else {
                        val addMorePrice = 60.0 - it.data!![0].OrderAmount.drop(2).toDouble()
                        txtAddWorth.text = "£" + addMorePrice.toString() + " away form meeting the minimum spend."
                    }

                    val itemSize = it.data!![0].OrderItemList


                    for (i in itemSize.indices) {
                        qty = qty + itemSize[i].ItemQty.toInt()
                    }

                    val adapter = ReorderAdapter(it.data!![0].OrderItemList, db)
                    recyCartList.adapter = adapter
                    adapter.notifyDataSetChanged()

                    txtCartValue.text = qty.toString()

                    GlobalScope.launch {
                        db.contactDao().deleteReorderItems()
                        db.contactDao().saveReorderItems(it.data!![0].OrderItemList)

                    }

                }

                is Resources.Error -> {


                }

            }
        }


    }

    fun decrement(itemRate: String) {
        var cartItem = qty - 1
        qty = cartItem
        txtCartValue.text = cartItem.toString()
        val totalPrice = txtTotalPrice.text.toString().drop(1).toFloat() - itemRate.toFloat()
        txtTotalPrice.text = totalPrice.toString()

        val tPrice = txtTotalPrice.text.toString().drop(1).toFloat()

        if(tPrice <= 60.00){
            val price = 60.00 - tPrice
            txtAddWorth.text = "£" + price.toString() +"0"+" away form meeting the minimum spend."
            txtTotalPrice.text = "£" +totalPrice.toString()+"0"
        }else {
            val totalPrice = txtTotalPrice.text.toString().toFloat()
            txtTotalPrice.text = "£" +totalPrice.toString()+"0"
        }

    }

    fun increment(itemRate: String) {
        var cartItem = qty + 1
        qty = cartItem
        txtCartValue.text = cartItem.toString()

        val totalPrice = txtTotalPrice.text.toString().drop(1).toFloat() + itemRate.toFloat()
        txtTotalPrice.text = totalPrice.toString()

        val tPrice = txtTotalPrice.text.toString().toFloat()

        if(tPrice <= 60.00){
            val price = 60.00 - tPrice
            txtAddWorth.text = "£" + price.toString() +"0"+ " away form meeting the minimum spend."
            txtTotalPrice.text = "£" +totalPrice.toString()+"0"
        }else {
            val totalPrice = txtTotalPrice.text.toString().toFloat()
            txtTotalPrice.text = "£" +totalPrice.toString()+"0"
        }
    }



}