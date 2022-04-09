package com.bws.musclefood.productdetails

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.factory.FactoryProvider
import com.bws.musclefood.itemcategory.productlist.ProductListAdapter
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.urils.LoadingDialog
import com.bws.musclefood.urils.PreferenceConnector
import com.bws.musclefood.urils.Resources
import com.bws.musclefood.viewmodels.ProductDetailsViewModel
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_producct_details.*
import kotlinx.android.synthetic.main.activity_productlist.*
import kotlinx.android.synthetic.main.tool_bar_cart_details.*
import kotlinx.android.synthetic.main.tool_bar_search_view.txtLogInSignUp

class ProductDetailsActivity : AppCompatActivity() {
    var myInt: Int = 1

    lateinit var productDetailsViewModel: ProductDetailsViewModel
    lateinit var preferenceConnector: PreferenceConnector
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producct_details)
        supportActionBar?.hide()

        preferenceConnector = PreferenceConnector(this)

        txtLogInSignUp.text = "Product Details"
        txtCartValue.text = "2"

       // imvProduct.setImageResource(R.drawable.cheken1)

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
                myInt--
                txtTotalQuentity.text = myInt.toString()
            }
        }

        txtInrement.setOnClickListener {
            myInt = txtTotalQuentity.text.toString().toInt()
            myInt++
            txtTotalQuentity.text = myInt.toString()
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
            "2",
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

                    var gradient = result?.Ingredients
                    if(gradient.equals("")){
                        txtGradient.visibility = View.GONE
                        txtMinus1.visibility = View.GONE
                        txtPlus1.visibility = View.VISIBLE
                    }

                    var nuttitional = result?.Nutritionals
                    if(nuttitional.equals("")){
                        txtNutritionals.visibility = View.GONE
                        txtMinus2.visibility = View.GONE
                        txtPlus2.visibility = View.VISIBLE
                    }

                    var storage = result?.StorageInstructions
                    if(storage.equals("")){
                        txtStorageIntuction.visibility = View.GONE
                        txtMinus3.visibility = View.GONE
                        txtPlus3.visibility = View.VISIBLE
                    }

                    var shelLife = result?.ShelfLife
                    if(shelLife.equals("")){
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
}