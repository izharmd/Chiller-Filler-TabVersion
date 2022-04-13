package com.bws.musclefood.itemcategory.cartlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.delivery.deliveryoption.DeliveryOptionActivity
import com.bws.musclefood.factory.FactoryProvider
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.AlertDialog
import com.bws.musclefood.utils.LoadingDialog
import com.bws.musclefood.utils.PreferenceConnector
import com.bws.musclefood.utils.Resources
import com.bws.musclefood.viewmodels.CartListViewModel
import com.bws.musclefood.viewmodels.RemoveProductViewModel
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_cart_list.*
import kotlinx.android.synthetic.main.tool_bar.txtLogInSignUp
import kotlinx.android.synthetic.main.tool_bar_address.imvBack
import kotlinx.android.synthetic.main.tool_bar_cart_details.*
import kotlinx.android.synthetic.main.tool_bar_search_view.imvSearch
import java.text.NumberFormat

class CartListActivity : AppCompatActivity() {

    var totalOrderValue = 0f
    var totalPrice = 0f
    var cartItem:Int = 0

    lateinit var cartListViewModel: CartListViewModel
    lateinit var cartListAdapter: CartListAdapter

    lateinit var removeProductViewModel: RemoveProductViewModel
    lateinit var preferenceConnector: PreferenceConnector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_list)
        supportActionBar?.hide()

        preferenceConnector = PreferenceConnector(this)

        txtOrderNo.visibility = View.GONE
        txtLogInSignUp.text = "Cart Details"
        recyCartList.layoutManager = LinearLayoutManager(this)

        val dividerDrawable =
            ContextCompat.getDrawable(applicationContext, R.drawable.line_divider)
        recyCartList.addItemDecoration(DividerItemDecoration(dividerDrawable))


        //GET ALL PRODUCT LIST
        getAllProductList()

        imvSearch.setOnClickListener() {
            searchView1.visibility = View.VISIBLE
        }

        txtCheckOut.setOnClickListener() {

            startActivity(Intent(this@CartListActivity, DeliveryOptionActivity::class.java))
           /* if (totalOrderValue >= 80) {
                startActivity(Intent(this@CartListActivity, DeliveryOptionActivity::class.java))
            } else {
                AlertDialog().dialog(
                    this,
                    "Minimum cart value should be £80 or more to place order."
                )
            }*/
        }

        imvBack.setOnClickListener() {
            finish()
        }
    }


    //REMOVE PRODUCT FROM BASKETS
    fun removeProduct(productId: String) {
        removeProductViewModel = ViewModelProvider(
            this,
            FactoryProvider(Repository(), this)
        ).get(RemoveProductViewModel::class.java)
        val body = RequestBodies.GetRemoveCartProductBody(
            productId,
            preferenceConnector.getValueString("USER_ID").toString()
        )
        removeProductViewModel.getRemoveProduct(body)
        val loadingDialog = LoadingDialog.progressDialog(this)
        removeProductViewModel.resultRemoveProduct.observe(this) {
            when (it) {
                is Resources.Loading -> {
                    loadingDialog.show()
                }
                is Resources.NoInternet -> {
                    AlertDialog().dialog(this, it.noInternetMessage.toString())
                    this.viewModelStore.clear()
                    loadingDialog.hide()
                }
                is Resources.Success -> {

                    cartItem = 0
                    var statusCode = it.data?.StatusCode
                    if (statusCode == "200") {
                        Toast.makeText(this, it.data?.StatusMSG, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, it.data?.StatusMSG, Toast.LENGTH_SHORT).show()
                    }
                    this.viewModelStore.clear()
                    loadingDialog.hide()
                    //RELOAD CART LIST AFTER REMOVE PRODUCT
                    getAllProductList()
                }
                is Resources.Error -> {
                    AlertDialog().dialog(this, it.errorMessage.toString())
                    this.viewModelStore.clear()
                    loadingDialog.hide()
                }
            }
        }
    }


    //GET ALL PRODUCT LIST
    fun getAllProductList() {
        cartListViewModel = ViewModelProvider(
            this,
            FactoryProvider(Repository(), this)
        ).get(CartListViewModel::class.java)

        val body = RequestBodies.GetAllCartDetailsBody(
            preferenceConnector.getValueString("USER_ID").toString(), Constant.sessionID
        )
        cartListViewModel.getCartList(body)
        val loadingDialog = LoadingDialog.progressDialog(this)
        cartListViewModel.cartList.observe(this) {
            when (it) {
                is Resources.Loading -> {
                    loadingDialog.show()
                }
                is Resources.NoInternet -> {
                    AlertDialog().dialog(this, it.noInternetMessage.toString())
                    this.viewModelStore.clear()
                    loadingDialog.hide()
                }

                is Resources.Success -> {

                    cartListAdapter = CartListAdapter(it.data!!)
                    recyCartList.adapter = cartListAdapter
                    cartListAdapter.notifyDataSetChanged()

                    if(it.data.size != 0){
                        for (i in 0 until it.data.size){

                            cartItem = cartItem + it.data[i].Quantity.toInt()

                            var price = it.data[i].Price.toFloat() * it.data[i].Quantity.toFloat()
                            totalPrice = totalPrice + price
                            txtTotalPrice.text = totalPrice.toString()
                        }

                        txtCartValue.text = cartItem.toString()
                        var orderValue = 80.00 - totalPrice
                        txtAddWorth.text =
                            "You are " + "£" + orderValue.toString() + "0" + " away from meeting the minimum spend."

                        val currencyFormatter = NumberFormat.getCurrencyInstance()
                       //  = totalPrice.toString()
                       // txtTotalPrice.text = "£ " + currencyFormatter.format(totalPrice).toString()
                      //  txtTotalPrice.text = "£ " + currencyFormatter.format(totalPrice).toString().drop(1)
                    }else{
                        txtAddWorth.text =
                            "You are " + "£" + "80." + "00" + " away from meeting the minimum spend."
                        txtTotalPrice.text = "£00.00"

                    }


                    loadingDialog.hide()
                }
                is Resources.Error -> {
                    AlertDialog().dialog(this, it.errorMessage.toString())
                    this.viewModelStore.clear()
                    loadingDialog.hide()
                }
            }
        }
    }

    fun updateCartItem(totalPrice: Double, netDiscount: Double) {
        val currencyFormatter = NumberFormat.getCurrencyInstance()
        txtTotalPrice.text = "£ " + currencyFormatter.format(totalPrice).toString()
        txtTotalPrice.text = "£ " + currencyFormatter.format(totalPrice).toString().drop(1)
        txtTotalSave.text = "£ " + currencyFormatter.format(netDiscount).toString().drop(1)


       // txtCartValue.text = totalCartItem.toString()
        //  totalOrderValue = currencyFormatter.format(totalPrice).toString().drop(1).toFloat()

        var orderValue = 80.00 - totalPrice
        txtAddWorth.text =
            "You are " + "£" + orderValue.toString() + "0" + " away from meeting the minimum spend."

    }

    fun cartItemIncrement(){
        cartItem = cartItem + 1
        txtCartValue.text = cartItem.toString()
    }

    fun cartItemDecrement(){
        cartItem = cartItem - 1
        txtCartValue.text = cartItem.toString()
    }
}