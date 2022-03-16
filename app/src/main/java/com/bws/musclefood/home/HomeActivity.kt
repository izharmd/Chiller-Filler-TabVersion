package com.bws.musclefood.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.databinding.ActivityHomeBinding
import com.bws.musclefood.itemcategory.productlist.ProductListActivity

class HomeActivity:AppCompatActivity() {

    lateinit var homeBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
       // setContentView(R.layout.activity_home)

        homeBinding = DataBindingUtil.setContentView(this,R.layout.activity_home)

        homeBinding.btnRetailReady.setOnClickListener(){

            Constant.serviceType = "Retail Ready Products"

            startActivity(Intent(this@HomeActivity,ProductListActivity::class.java))
        }

        homeBinding.btnFoodService.setOnClickListener(){

            Constant.serviceType = "Food Service"
            startActivity(Intent(this@HomeActivity,ProductListActivity::class.java))
        }
    }
}