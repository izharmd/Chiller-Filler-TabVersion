package com.bws.musclefood.favourites

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
import com.bws.musclefood.common.Constant.Companion.serviceType
import com.bws.musclefood.delivery.deliveryoption.DeliveryOptionActivity
import com.bws.musclefood.factory.FactoryProvider
import com.bws.musclefood.itemcategory.cartlist.CartListActivity
import com.bws.musclefood.itemcategory.cartlist.CartListAdapter
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.AlertDialog
import com.bws.musclefood.utils.LoadingDialog
import com.bws.musclefood.utils.PreferenceConnector
import com.bws.musclefood.utils.Resources
import com.bws.musclefood.viewmodels.FavouriteListViewModel
import com.bws.musclefood.viewmodels.RemoveFavoriteViewModel
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_cart_list.*
import kotlinx.android.synthetic.main.tool_bar.*
import kotlinx.android.synthetic.main.tool_bar.txtLogInSignUp
import kotlinx.android.synthetic.main.tool_bar_address.*
import kotlinx.android.synthetic.main.tool_bar_address.imvBack
import kotlinx.android.synthetic.main.tool_bar_cart_details.*


class FavouritesActivity : AppCompatActivity() {

    lateinit var favouriteListViewModel: FavouriteListViewModel

    lateinit var preferenceConnector: PreferenceConnector

    var favoritesItmSize:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)
        supportActionBar?.hide()

        preferenceConnector = PreferenceConnector(this)

        txtCartValue.text = Constant.cartItem.toString()


        txtLogInSignUp.text = "Favourite Items"

        recyCartList.layoutManager = LinearLayoutManager(this)


        imvSearch.setOnClickListener() {
            searchView1.visibility = View.VISIBLE
        }

        txtCheckOut.setOnClickListener() {
            startActivity(Intent(this@FavouritesActivity, DeliveryOptionActivity::class.java))
        }

        imvBack.setOnClickListener() {
            finish()
        }

       imvCart.setOnClickListener {

           startActivity(Intent(this@FavouritesActivity,CartListActivity::class.java))
       }


        callFavouriteListPI()
    }


    fun callFavouriteListPI() {
        favouriteListViewModel = ViewModelProvider(
            this,
            FactoryProvider(Repository(), this)
        ).get(FavouriteListViewModel::class.java)

        val categoryList = RequestBodies.FavouriteListBody(

            preferenceConnector.getValueString("USER_ID")!!,
            serviceType,
            Constant.categoryId,
            Constant.categoryName,
            ""
        )

        println("JSON===" + Gson().toJson(categoryList))
        favouriteListViewModel.getFavouriteList(categoryList)

        val loadingDialog = LoadingDialog.progressDialog(this)

        favouriteListViewModel.resultFavouriteList.observe(this) {

            when (it) {
                is Resources.Loading -> {
                    loadingDialog.show()
                }
                is Resources.NoInternet -> {
                    loadingDialog.hide()
                    Toast.makeText(this, it.noInternetMessage, Toast.LENGTH_SHORT).show()
                }
                is Resources.Success -> {
                    val dividerDrawable =
                        ContextCompat.getDrawable(applicationContext, R.drawable.line_divider)
                    recyCartList.addItemDecoration(DividerItemDecoration(dividerDrawable))

                     favoritesItmSize = it.data?.size!!

                    val adapter = FavouritesAdapter(it.data!!)
                    recyCartList.adapter = adapter
                    adapter.notifyDataSetChanged()

                    loadingDialog.hide()
                    this.viewModelStore.clear()


                    val total =  Constant.cartItem + favoritesItmSize
                    txtCartValue.text = total.toString()
                }

                is Resources.Error -> {
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
                    this.viewModelStore.clear()
                }
            }

        }
    }

    fun updateCartItem() {
        var itm = txtCartValue.text.toString().toInt()
        val total = 1 + itm
        txtCartValue.text = total.toString()
    }

    fun updateCartItemDecrement() {
        var itm = txtCartValue.text.toString().toInt()
        val total =  itm - 1
        txtCartValue.text = total.toString()
    }
}