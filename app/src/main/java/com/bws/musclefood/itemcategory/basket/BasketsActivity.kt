package com.bws.musclefood.itemcategory.basket

import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import com.bws.musclefood.viewmodels.AddFavouriteViewModel
import com.bws.musclefood.viewmodels.CartListViewModel
import com.bws.musclefood.viewmodels.RemoveFavoriteViewModel
import com.bws.musclefood.viewmodels.RemoveProductViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_basket.*
import kotlinx.android.synthetic.main.activity_cart_list.*
import kotlinx.android.synthetic.main.tool_bar_address.*
import kotlinx.android.synthetic.main.tool_bar_address.imvBack
import kotlinx.android.synthetic.main.tool_bar_cart_details.*
import org.json.JSONObject
import java.text.NumberFormat

class BasketsActivity : AppCompatActivity() {

    //  var totalPrice = 0f
    var cartItem: Int = 0

    lateinit var cartListViewModel: CartListViewModel
    lateinit var preferenceConnector: PreferenceConnector
    lateinit var cartListAdapter: BasketAdapter

    lateinit var removeProductViewModel: RemoveProductViewModel
    lateinit var removeFavoriteViewModel: RemoveFavoriteViewModel
    lateinit var addFavouriteViewModel: AddFavouriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)
        supportActionBar?.hide()
         txtTxtHeader.text = "Baskets"
        imvSaveaddress.visibility = View.GONE


        imvBack.setOnClickListener{
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

                    if (it.data.size > 0) {
                        txtSavedBasket.visibility = View.GONE
                    } else {
                        txtSavedBasket.visibility = View.VISIBLE
                    }
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


    fun calRemoveFavouritePI(productId: String) {

        removeFavoriteViewModel = ViewModelProvider(
            this,
            FactoryProvider(Repository(), this)
        ).get(RemoveFavoriteViewModel::class.java)

        val removeFavourite = RequestBodies.RemoveFavoriteProductBody(
            productId,
            preferenceConnector.getValueString("USER_ID")!!,

            )

        println("JSON===" + Gson().toJson(removeFavourite))
        removeFavoriteViewModel.getRemoveFavorite(removeFavourite)

        val loadingDialog = LoadingDialog.progressDialog(this)

        removeFavoriteViewModel.resultRemoveRemoveFavorite.observe(this) {

            when (it) {
                is Resources.Loading -> {
                    loadingDialog.show()
                }
                is Resources.NoInternet -> {
                    loadingDialog.hide()
                }
                is Resources.Success -> {
                    loadingDialog.hide()

                    this.viewModelStore.clear()
                }

                is Resources.Error -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }


    fun calAddFavouritePI(productId: String) {

        addFavouriteViewModel = ViewModelProvider(
            this,
            FactoryProvider(Repository(), this)
        ).get(AddFavouriteViewModel::class.java)

        val addFavourite = RequestBodies.AddFavouriteListBody(
            productId,
            preferenceConnector.getValueString("USER_ID")!!,

            )

        println("JSON===" + Gson().toJson(addFavourite))
        addFavouriteViewModel.getAddFavourite(addFavourite)

        val loadingDialog = LoadingDialog.progressDialog(this)

        addFavouriteViewModel.resultAddFavourite.observe(this) {

            when (it) {
                is Resources.Loading -> {
                    loadingDialog.show()
                }
                is Resources.NoInternet -> {
                    loadingDialog.hide()
                }
                is Resources.Success -> {

                    Toast.makeText(this, it.data?.StatusMSG, Toast.LENGTH_SHORT).show()
                    loadingDialog.hide()

                    this.viewModelStore.clear()
                }

                is Resources.Error -> {
                    Toast.makeText(this, "reeeee", Toast.LENGTH_SHORT).show()
                }
            }

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
                    loadingDialog.dismiss()

                    //RELOAD CART LIST AFTER REMOVE PRODUCT
                    getAllProductList()
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