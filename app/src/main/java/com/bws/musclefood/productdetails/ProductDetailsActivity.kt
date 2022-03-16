package com.bws.musclefood.productdetails

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bws.musclefood.R
import kotlinx.android.synthetic.main.activity_producct_details.*
import kotlinx.android.synthetic.main.tool_bar_cart_details.*
import kotlinx.android.synthetic.main.tool_bar_search_view.txtLogInSignUp

class ProductDetailsActivity:AppCompatActivity() {
    var myInt: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producct_details)
        supportActionBar?.hide()

        txtLogInSignUp.text = "Product Details"
        txtCartValue.text = "2"

        imvProduct.setImageResource(R.drawable.cheken1)

        txtPlus1.setOnClickListener{
            txtGradient.visibility = View.VISIBLE
            txtMinus1.visibility = View.VISIBLE
        }

        txtMinus1.setOnClickListener{
            txtGradient.visibility = View.GONE
            txtPlus1.visibility = View.VISIBLE
            txtMinus1.visibility = View.GONE
        }

        txtDecrement.setOnClickListener{
            myInt = txtTotalQuentity.text.toString().toInt()
            if (myInt <= 1) {
                myInt = 1
            } else {
                myInt--
                txtTotalQuentity.text = myInt.toString()
            }
        }

        txtInrement.setOnClickListener{
            myInt = txtTotalQuentity.text.toString().toInt()
            myInt++
            txtTotalQuentity.text = myInt.toString()
        }

        imvBack.setOnClickListener(){
            finish()
        }
    }
}