package com.bws.musclefood.delivery.choosedeliveryaddress

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.delivery.AddNewAddressActivity
import kotlinx.android.synthetic.main.activity_choose_delivery_address.*
import kotlinx.android.synthetic.main.tool_bar_address.*

class ChooseDeliveryAddressActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_delivery_address)
        supportActionBar?.hide()

        imvSaveaddress.visibility = View.GONE
        txtTxtHeader.text = Constant.deliveryAddress

        recyAddress.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<ChooseDelModel>()
       // data.add(ChooseDelModel("Home","Mr James","RG6 6AH UK","2489003450"))
        data.add(ChooseDelModel("Office","Mr Joseph","32, My Street, Kingston, UK 12401.","342603450"))
        data.add(ChooseDelModel("Home","Mr John Smith","132, My Street,Bigtown BG23 4YZ UK","2489003450"))

        val adapter = ChooseDelAdapter(data)
        recyAddress.adapter = adapter
        adapter.notifyDataSetChanged()

        txtAddNewAddress.setOnClickListener(){
            startActivity(Intent(this@ChooseDeliveryAddressActivity,AddNewAddressActivity::class.java))
        }


        rdDefaultAddress.setOnClickListener(){
            rdDefaultAddress.isChecked = true
            rdHomeAddress.isChecked = false
        }

        rdHomeAddress.setOnClickListener(){
            rdDefaultAddress.isChecked = false
            rdHomeAddress.isChecked = true
        }

        txtEditAddress.setOnClickListener(){
           startActivity(Intent(this@ChooseDeliveryAddressActivity,AddNewAddressActivity::class.java))
        }

        txtEditAddress1.setOnClickListener(){
           startActivity(Intent(this@ChooseDeliveryAddressActivity,AddNewAddressActivity::class.java))
        }


        imvBack.setOnClickListener(){
            finish()
        }

    }
}