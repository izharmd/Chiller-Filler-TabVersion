package com.bws.musclefood.orders

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bws.musclefood.R
import kotlinx.android.synthetic.main.activity_search_order.*
import kotlinx.android.synthetic.main.tool_bar_address.*

class SearchOrderActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_order)
        supportActionBar?.hide()
        txtTxtHeader.text = "Search Orders"
        imvSaveaddress.visibility = View.GONE

        btnSearchOrder.setOnClickListener(){
            startActivity(Intent(this@SearchOrderActivity,OrderActivity::class.java))
        }

        imvBack.setOnClickListener(){
            finish()
        }
    }
}