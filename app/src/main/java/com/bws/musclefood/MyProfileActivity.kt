package com.bws.musclefood

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.tool_bar_address.*

class MyProfileActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.hide()
        txtTxtHeader.text = "Edit Profile"
        imvSaveaddress.visibility = View.GONE

        imvBack.setOnClickListener(){
            finish()
        }
    }
}