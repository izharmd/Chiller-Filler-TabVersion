package com.bws.musclefood.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.itemcategory.productlist.ProductListActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_home)

        // homeBinding = DataBindingUtil.setContentView(this,R.layout.activity_home)

        btnRetailReady.setOnClickListener() {

            Constant.serviceType = "Retail Ready Products"

            startActivity(Intent(this@HomeActivity, ProductListActivity::class.java))
        }

        btnFoodService.setOnClickListener() {

            Constant.serviceType = "Food Service"
            startActivity(Intent(this@HomeActivity, ProductListActivity::class.java))
        }
    }
}