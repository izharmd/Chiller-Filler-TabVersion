package com.bws.musclefood.productdetails

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bws.musclefood.R
import kotlinx.android.synthetic.main.activity_producct_details.*
import kotlinx.android.synthetic.main.tool_bar_cart_details.*
import kotlinx.android.synthetic.main.tool_bar_search_view.txtLogInSignUp

class ProductDetailsActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producct_details)
        supportActionBar?.hide()

        txtLogInSignUp.text = "Product Details"
        txtCartValue.text = "2"

        imvProduct.setImageResource(R.drawable.cheken1)


        imvBack.setOnClickListener(){
            finish()
        }
    }
}