package com.bws.musclefood.productdetails

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.factory.FactoryProvider
import com.bws.musclefood.itemcategory.cartlist.CartListModel
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.AlertDialog
import com.bws.musclefood.utils.LoadingDialog
import com.bws.musclefood.utils.PreferenceConnector
import com.bws.musclefood.utils.Resources
import com.bws.musclefood.viewmodels.InsertUpdateCartViewModel
import com.bws.musclefood.viewmodels.ProductDetailsViewModel
import kotlinx.android.synthetic.main.activity_producct_details.*
import kotlinx.android.synthetic.main.tool_bar_cart_details.*
import kotlinx.android.synthetic.main.tool_bar_search_view.txtLogInSignUp
import org.json.JSONArray
import org.json.JSONObject

class ProductDetailsActivity : AppCompatActivity() {
    var myInt: Int = 1


    var productId = ""
    var productName = ""
    var productPrice = ""
    var productImage = ""

    lateinit var insertUpdateCartViewModel: InsertUpdateCartViewModel
    lateinit var jo: JSONObject

    lateinit var productDetailsViewModel: ProductDetailsViewModel
    lateinit var preferenceConnector: PreferenceConnector
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producct_details)
        supportActionBar?.hide()

        preferenceConnector = PreferenceConnector(this)

        txtLogInSignUp.text = "Product Details"
        txtCartValue.text = Constant.cartItem.toString()

        myInt = Constant.quantity.toInt()

        txtTotalQuentity.text = Constant.quantity



        txtPlus1.setOnClickListener {
            txtGradient.visibility = View.VISIBLE
            txtMinus1.visibility = View.VISIBLE
        }

        txtMinus1.setOnClickListener {
            txtGradient.visibility = View.GONE
            txtPlus1.visibility = View.VISIBLE
            txtMinus1.visibility = View.GONE
        }

        txtPlus2.setOnClickListener {
            txtNutritionals.visibility = View.VISIBLE
            txtMinus2.visibility = View.VISIBLE
            txtPlus2.visibility = View.GONE
        }

        txtMinus2.setOnClickListener {
            txtNutritionals.visibility = View.GONE
            txtPlus2.visibility = View.VISIBLE
            txtMinus2.visibility = View.GONE
        }


        txtPlus3.setOnClickListener {
            txtStorageIntuction.visibility = View.VISIBLE
            txtMinus3.visibility = View.VISIBLE
            txtPlus3.visibility = View.GONE
        }

        txtMinus3.setOnClickListener {
            txtStorageIntuction.visibility = View.GONE
            txtPlus3.visibility = View.VISIBLE
            txtMinus3.visibility = View.GONE
        }


        txtPlus4.setOnClickListener {
            txtShelfLife.visibility = View.VISIBLE
            txtMinus4.visibility = View.VISIBLE
            txtPlus4.visibility = View.GONE
        }

        txtMinus4.setOnClickListener {
            txtShelfLife.visibility = View.GONE
            txtPlus4.visibility = View.VISIBLE
            txtMinus4.visibility = View.GONE
        }


        txtDecrement.setOnClickListener {
            myInt = txtTotalQuentity.text.toString().toInt()
            if (myInt <= 1) {
                myInt = 1
            } else {
                Constant.cartItem --
                myInt--
                txtTotalQuentity.text = myInt.toString()

                txtCartValue.text = Constant.cartItem.toString()
            }
        }

        txtInrement.setOnClickListener {

            if(myInt <= 9) {
                myInt = txtTotalQuentity.text.toString().toInt()
                myInt++
                Constant.cartItem++
                txtTotalQuentity.text = myInt.toString()
                txtCartValue.text = Constant.cartItem.toString()
            }else{
                AlertDialog().dialog(this, resources.getString(R.string.CANT_ADD_MORE_THAN_10))
            }
        }

        txtAdd.setOnClickListener {
            if (Constant.hashMap.containsKey(productId)) {
                Constant.hashMap.replace(
                    productId, CartListModel(
                        productImage,
                        productName,
                        txtTotalQuentity.text.toString(),
                        productPrice,
                        productId
                    )
                )
            } else {
                Constant.hashMap.put(
                    productId, CartListModel(
                        productImage,
                        productName,
                        txtTotalQuentity.text.toString(),
                        productPrice,
                        productId
                    )
                )
            }

            //USE FOR ADD PRODUCT IN TO BASKET
            updateInsertCart()
        }

        imvBack.setOnClickListener() {
            finish()
        }

        callProductDetailApi()
    }

    fun callProductDetailApi() {

        productDetailsViewModel = ViewModelProvider(
            this,
            FactoryProvider(Repository(), this)
        ).get(ProductDetailsViewModel::class.java)

        val loadingDialog = LoadingDialog.progressDialog(this)

        val pramProductDetails = RequestBodies.PopulateProductDetailsByProductIDBody(
            preferenceConnector.getValueString("USER_ID")!!,
            "Retail Ready",
            "1",//Constant.subCategoryId,        //NEED TO SET ID LIKE 1,2,3 NOW IS COMMING Beef.. like that
            "",
            ""
        )
        productDetailsViewModel.getProductDetails(pramProductDetails)

        productDetailsViewModel.resultProductDetails.observe(this) {

            when (it) {
                is Resources.Loading -> {
                    loadingDialog.show()
                }
                is Resources.NoInternet -> {
                    loadingDialog.hide()
                }
                is Resources.Success -> {
                    val result = it.data?.get(0)
                    txtPName.text = result?.ProductName
                    txtPrice.text = result?.ProductPriceFormatted
                    txtPSize.text = result?.ProductSize
                    txtGradient.text = result?.Ingredients
                    txtNutritionals.text = result?.Nutritionals
                    txtStorageIntuction.text = result?.StorageInstructions
                    txtShelfLife.text = result?.ShelfLife

                    productId = result?.ProductID!!
                    productName = result?.ProductName!!
                    productPrice = result?.ProductPrice!!
                    productImage = result?.ProductImage!!

                    var gradient = result?.Ingredients
                    if (gradient.equals("")) {
                        txtGradient.visibility = View.GONE
                        txtMinus1.visibility = View.GONE
                        txtPlus1.visibility = View.VISIBLE
                    }

                    var nuttitional = result?.Nutritionals
                    if (nuttitional.equals("")) {
                        txtNutritionals.visibility = View.GONE
                        txtMinus2.visibility = View.GONE
                        txtPlus2.visibility = View.VISIBLE
                    }

                    var storage = result?.StorageInstructions
                    if (storage.equals("")) {
                        txtStorageIntuction.visibility = View.GONE
                        txtMinus3.visibility = View.GONE
                        txtPlus3.visibility = View.VISIBLE
                    }

                    var shelLife = result?.ShelfLife
                    if (shelLife.equals("")) {
                        txtShelfLife.visibility = View.GONE
                        txtMinus4.visibility = View.GONE
                        txtPlus4.visibility = View.VISIBLE
                    }


                    var productImage = result?.ProductImage

                    if (productImage !== null) {
                        Glide.with(this)
                            .load(result?.ProductImage)
                            .into(imvProduct)
                    } else {
                        imvProduct.setImageResource(R.drawable.ic_launcher_background)
                    }

                    loadingDialog.hide()

                    this.viewModelStore.clear()
                }
                is Resources.Error -> {
                    loadingDialog.hide()
                }
            }
        }
    }


    fun updateInsertCart() {

        insertUpdateCartViewModel =
            ViewModelProvider(this, FactoryProvider(Repository(), this)).get(
                InsertUpdateCartViewModel::class.java
            )

        val ja = JSONArray()
        var keys = Constant.hashMap.keys
        for (key in keys) {
            jo = JSONObject()
            var cartModel = Constant.hashMap.get(key)
            jo.put("CartItemID", cartModel?.productId)
            jo.put("Price", cartModel?.price)
            jo.put("ProductID", cartModel?.productId)
            jo.put("Quantity", cartModel?.quantity)
            jo.put("SessionID", Constant.sessionID)
            jo.put("TotalPrice", cartModel?.price)
            jo.put("UserID", preferenceConnector.getValueString("USER_ID"))
            ja.put(jo)
        }
        println("JSON=====" + ja)
        val loadingDialog = LoadingDialog.progressDialog(this)
        insertUpdateCartViewModel.insertUpdateCart(ja)
        insertUpdateCartViewModel.resultInsertUpdate.observe(this) {

            when (it) {
                is Resources.Loading -> {
                    loadingDialog.show()
                }
                is Resources.NoInternet -> {
                    loadingDialog.hide()
                }
                is Resources.Success -> {
                    Toast.makeText(this, "ProductItem Updated successfully", Toast.LENGTH_SHORT).show()
                    //startActivity(Intent(this@ProductDetailsActivity, CartListActivity::class.java))
                    loadingDialog.hide()
                    this.viewModelStore.clear()
                }

                is Resources.Error -> {
                    loadingDialog.hide()
                }
            }
        }
    }
}