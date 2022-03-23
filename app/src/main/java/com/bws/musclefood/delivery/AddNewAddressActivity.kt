package com.bws.musclefood.delivery

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bws.musclefood.R
import com.bws.musclefood.urils.AlertDialog
import kotlinx.android.synthetic.main.activity_add_new_address.*
import kotlinx.android.synthetic.main.tool_bar_address.*

class AddNewAddressActivity:AppCompatActivity() {

    val ine:Int = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_address)
        supportActionBar?.hide()


        txtHome.setOnClickListener(){
            txtHome.setBackgroundResource(R.drawable.round_address_selected)
            txtHome.setTextColor(Color.parseColor("#FFB300"))

            txtOffice.setBackgroundResource(R.drawable.round_address)
            txtOffice.setTextColor(Color.parseColor("#000000"))

            txtOthers.setBackgroundResource(R.drawable.round_address)
            txtOthers.setTextColor(Color.parseColor("#000000"))
        }

        txtOffice.setOnClickListener(){
            txtOffice.setBackgroundResource(R.drawable.round_address_selected)
            txtOffice.setTextColor(Color.parseColor("#FFB300"))

            txtHome.setBackgroundResource(R.drawable.round_address)
            txtHome.setTextColor(Color.parseColor("#000000"))

            txtOthers.setBackgroundResource(R.drawable.round_address)
            txtOthers.setTextColor(Color.parseColor("#000000"))
        }

        txtOthers.setOnClickListener(){
            txtOffice.setBackgroundResource(R.drawable.round_address)
            txtOffice.setTextColor(Color.parseColor("#000000"))

            txtHome.setBackgroundResource(R.drawable.round_address)
            txtHome.setTextColor(Color.parseColor("#000000"))

            txtOthers.setBackgroundResource(R.drawable.round_address_selected)
            txtOthers.setTextColor(Color.parseColor("#FFB300"))
        }



        imvSaveaddress.setOnClickListener {
            when {
                edtHouseNo.text.isEmpty() -> {
                    AlertDialog().dialog(this,"Please enter Address")
                }
                /*edtAddressLine1.text.isEmpty() -> {
                    AlertDialog().dialog(this,"Please enter last name")
                }
                edtAddressLine2.text.isEmpty() -> {
                    AlertDialog().dialog(this,"Please enter company name")
                }*/
                edtCity.text.isEmpty() -> {
                    AlertDialog().dialog(this,"Please enter city")
                }
                edtZipCode.text.isEmpty() -> {
                    AlertDialog().dialog(this,"Please enter zipcode")
                }
                else -> {
                    finish()
                }
            }
        }


        imvBack.setOnClickListener(){
            finish()
        }
    }
}