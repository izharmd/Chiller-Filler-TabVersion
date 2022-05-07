package com.bws.musclefood.itemcategory.basket

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.factory.FactoryProvider
import com.bws.musclefood.itemcategory.cartlist.CartListAdapter
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.AlertDialog
import com.bws.musclefood.utils.LoadingDialog
import com.bws.musclefood.utils.PreferenceConnector
import com.bws.musclefood.utils.Resources
import com.bws.musclefood.viewmodels.CartListViewModel
import kotlinx.android.synthetic.main.activity_basket.*
import kotlinx.android.synthetic.main.activity_cart_list.*
import kotlinx.android.synthetic.main.tool_bar_address.*
import kotlinx.android.synthetic.main.tool_bar_address.imvBack
import kotlinx.android.synthetic.main.tool_bar_cart_details.*
import org.json.JSONObject
import java.text.NumberFormat

class BasketsActivity:AppCompatActivity() {

    //  var totalPrice = 0f
    var cartItem: Int = 0

    lateinit var cartListViewModel: CartListViewModel
    lateinit var preferenceConnector: PreferenceConnector
    lateinit var cartListAdapter: BasketAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)
        supportActionBar?.hide()
       // txtTxtHeader.text = "Saved Baskets"
        imvSaveaddress.visibility = View.GONE


        imvBack.setOnClickListener(){
            finish()
        }

       recyBasketList.layoutManager = LinearLayoutManager(this)

        preferenceConnector = PreferenceConnector(this)



        getAllProductList()
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
                    loadingDialog.dismiss()
                }

                is Resources.Success -> {
                    Constant.TotalPrice = 0f
                    cartListAdapter = BasketAdapter(it.data!!)
                    recyBasketList.adapter = cartListAdapter
                    cartListAdapter.notifyDataSetChanged()

                    if(it.data.size > 0){
                        txtSavedBasket.visibility = View.GONE
                    }else{
                        txtSavedBasket.visibility = View.VISIBLE
                    }

                  /*  if (it.data.size != 0) {
                        for (i in 0 until it.data.size) {
                            var jsonOjb = JSONObject()
                            cartItem = cartItem + it.data[i].Quantity.toInt()
                            var price = it.data[i].Price.toFloat() * it.data[i].Quantity.toInt()
                            Constant.TotalPrice =
                                Constant.TotalPrice + price
                            // txtTotalPrice.text = Constant.TotalPrice.toString()

                            println("JSON=="+price)

                            jsonOjb.put("CartItemID", it.data[i].CartItemID)
                            jsonOjb.put("ProductID", it.data[i].ProductID)
                            jsonOjb.put("ProductName", it.data[i].ProductName)
                            jsonOjb.put("ProductQuantity", it.data[i].Quantity)
                            jsonOjb.put("ProductPrice", it.data[i].Price)
                            jsonOjb.put(
                                "ProductTotalPrice",
                                it.data[i].FormattedProductTotalPrice.drop(2)
                            )
                            Constant.jsonOrder.put(jsonOjb)
                        }
                        val currencyFormatter = NumberFormat.getCurrencyInstance()

                        txtTotalPrice.text = "£"+ Constant.TotalPrice.toString() + "0"
                        txtCartValue.text = cartItem.toString()
                        println("TOTAL===="+ Constant.TotalPrice)
                    }else{
                        finish()
                    }
                    if (Constant.TotalPrice <= 80.00) {
                        var orderValue = 80.00 - Constant.TotalPrice
                        txtAddWorth.text =
                            "You are " + "£" + orderValue.toString() + "0" + " away from meeting the minimum spend."
                    } else {
                        txtAddWorth.text =
                            "You are " + "£" + "00." + "00" + " away from meeting the minimum spend."
                        //  txtTotalPrice.text = "£00.00"
                    }*/

                    loadingDialog.dismiss()
                }
                is Resources.Error -> {
                    AlertDialog().dialog(this, it.errorMessage.toString())
                    this.viewModelStore.clear()
                    loadingDialog.dismiss()
                }
            }
        }
    }
}