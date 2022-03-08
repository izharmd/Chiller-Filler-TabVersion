package com.bws.musclefood.profile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bws.musclefood.R
import kotlinx.android.synthetic.main.tool_bar_address.*

class ContactPreferenceActivity:AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)

        supportActionBar?.hide()
        txtTxtHeader.text = "Contact Preference"
        imvSaveaddress.visibility = View.GONE

        imvBack.setOnClickListener(){
            finish()
        }

    }
}