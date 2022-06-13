package com.bws.musclefood.delivery

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.delivery.choosedeliveryaddress.ChooseDelModel
import com.bws.musclefood.delivery.choosedeliveryaddress.DeliveryAddress
import com.bws.musclefood.factory.FactoryProvider
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.AlertDialog
import com.bws.musclefood.utils.LoadingDialog
import com.bws.musclefood.utils.PreferenceConnector
import com.bws.musclefood.utils.Resources
import com.bws.musclefood.viewmodels.AddNewAddressViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_add_new_address.*
import kotlinx.android.synthetic.main.tool_bar_address.*

class AddNewAddressActivity : AppCompatActivity(){

    lateinit var addNewAddressViewModel: AddNewAddressViewModel
    lateinit var preferenceConnector: PreferenceConnector
    var defaultAddress: String = "N"

    var deliveryAddressName = "Home"

    lateinit var body: RequestBodies.AddEditDeliveryDetails
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_address)
        supportActionBar?.hide()


        edtHouseNo.setText(intent.getStringExtra("DeliveryAddressHouseNumber"))
        edtAddressLine1.setText(intent.getStringExtra("DeliveryAddressLine1"))
        edtAddressLine2.setText(intent.getStringExtra("DeliveryAddressLine2"))
        edtCity.setText(intent.getStringExtra("DeliveryCity"))
        edtZipCode.setText(intent.getStringExtra("DeliveryPostcode"))
        edtPhoneNo.setText(intent.getStringExtra("DeliveryContactNumber"))
        edtAddressName.setText(intent.getStringExtra("DeliveryAddressName"))

        val isDefaultAddress = intent.getStringExtra("DefaultAddressFlag")
        val addressType = intent.getStringExtra("DeliveryAddressName")

        if(isDefaultAddress == "Y"){
            checkDefalultAddress.isChecked = true
            defaultAddress = "Y"
        }else{
            checkDefalultAddress.isChecked = false
            defaultAddress = "N"
        }


        if(addressType == "Home"){
            txtHome.setBackgroundResource(R.drawable.round_address_selected)
            txtHome.setTextColor(Color.parseColor("#FFB300"))

            txtOffice.setBackgroundResource(R.drawable.round_address)
            txtOffice.setTextColor(Color.parseColor("#000000"))

            txtOthers.setBackgroundResource(R.drawable.round_address)
            txtOthers.setTextColor(Color.parseColor("#000000"))
        }
        if(addressType == "Office"){
            txtOffice.setBackgroundResource(R.drawable.round_address_selected)
            txtOffice.setTextColor(Color.parseColor("#FFB300"))

            txtHome.setBackgroundResource(R.drawable.round_address)
            txtHome.setTextColor(Color.parseColor("#000000"))

            txtOthers.setBackgroundResource(R.drawable.round_address)
            txtOthers.setTextColor(Color.parseColor("#000000"))
        }
        if(addressType == "Other"){
            txtOffice.setBackgroundResource(R.drawable.round_address)
            txtOffice.setTextColor(Color.parseColor("#000000"))

            txtHome.setBackgroundResource(R.drawable.round_address)
            txtHome.setTextColor(Color.parseColor("#000000"))

            txtOthers.setBackgroundResource(R.drawable.round_address_selected)
            txtOthers.setTextColor(Color.parseColor("#FFB300"))
        }

        preferenceConnector = PreferenceConnector(this)


        txtHome.setOnClickListener() {
            txtHome.setBackgroundResource(R.drawable.round_address_selected)
            txtHome.setTextColor(Color.parseColor("#FFB300"))

            txtOffice.setBackgroundResource(R.drawable.round_address)
            txtOffice.setTextColor(Color.parseColor("#000000"))

            txtOthers.setBackgroundResource(R.drawable.round_address)
            txtOthers.setTextColor(Color.parseColor("#000000"))

            deliveryAddressName = "Home"
        }

        txtOffice.setOnClickListener() {
            txtOffice.setBackgroundResource(R.drawable.round_address_selected)
            txtOffice.setTextColor(Color.parseColor("#FFB300"))

            txtHome.setBackgroundResource(R.drawable.round_address)
            txtHome.setTextColor(Color.parseColor("#000000"))

            txtOthers.setBackgroundResource(R.drawable.round_address)
            txtOthers.setTextColor(Color.parseColor("#000000"))
            deliveryAddressName = "Office"
        }

        txtOthers.setOnClickListener() {
            txtOffice.setBackgroundResource(R.drawable.round_address)
            txtOffice.setTextColor(Color.parseColor("#000000"))

            txtHome.setBackgroundResource(R.drawable.round_address)
            txtHome.setTextColor(Color.parseColor("#000000"))

            txtOthers.setBackgroundResource(R.drawable.round_address_selected)
            txtOthers.setTextColor(Color.parseColor("#FFB300"))
            deliveryAddressName = "Others"
        }


        checkDefalultAddress.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                defaultAddress = "Y"
            } else {
                defaultAddress = "N"
            }
        }


        imvSaveaddress.setOnClickListener {
           when {
                edtHouseNo.text.isEmpty() -> {
                    AlertDialog().dialog(this, "Please enter Address")
                }
                /*edtAddressLine1.text.isEmpty() -> {
                    AlertDialog().dialog(this,"Please enter last name")
                }
                edtAddressLine2.text.isEmpty() -> {
                    AlertDialog().dialog(this,"Please enter company name")
                }*/
                edtCity.text.isEmpty() -> {
                    AlertDialog().dialog(this, "Please enter city")
                }
               edtPhoneNo.text.isEmpty() -> {
                    AlertDialog().dialog(this, "Please enter contact no")
                }
               edtAddressName.text.isEmpty() -> {
                    AlertDialog().dialog(this, "Please enter address name")
                }
                edtZipCode.text.isEmpty() -> {
                    AlertDialog().dialog(this, "Please enter zipcode")
                }
                else -> {
                    if(Constant.addressType == "Edit"){
                        body =
                            RequestBodies.AddEditDeliveryDetails(
                                intent.getStringExtra("ID")!!,
                                preferenceConnector.getValueString("USER_ID").toString(),
                                edtAddressName.text.toString(),
                                edtHouseNo.text.toString(),
                                edtAddressLine1.text.toString(),
                                edtAddressLine2.text.toString(),
                                edtCity.text.toString(),
                                edtZipCode.text.toString(),
                                edtPhoneNo.text.toString(),
                                "",
                                "",
                                defaultAddress
                            )

                        addNewAddress()
                    }else{
                        body =
                            RequestBodies.AddEditDeliveryDetails(
                                "",
                                preferenceConnector.getValueString("USER_ID").toString(),
                                edtAddressName.text.toString(),
                                edtHouseNo.text.toString(),
                                edtAddressLine1.text.toString(),
                                edtAddressLine2.text.toString(),
                                edtCity.text.toString(),
                                edtZipCode.text.toString(),
                                edtPhoneNo.text.toString(),
                                "",
                                "",
                                defaultAddress
                            )
                        addNewAddress()
                    }
                }
            }


          // addNewAddress()
        }


        imvBack.setOnClickListener() {
            finish()
        }
    }

    fun addNewAddress() {

        addNewAddressViewModel = ViewModelProvider(
            this,
            FactoryProvider(Repository(), this)
        ).get(AddNewAddressViewModel::class.java)

        addNewAddressViewModel.AddEditDeliveryDetails(body)
        val loadingDialog = LoadingDialog.progressDialog(this)

        addNewAddressViewModel.response.observe(this) {

            when (it) {

                is Resources.Loading -> {
                    loadingDialog.show()
                }
                is Resources.NoInternet -> {
                    loadingDialog.dismiss()
                }
                is Resources.Success -> {

                    val status = it.data?.StatusCode

                    if (status == "200") {
                        AlertDialog().dialogPaymentDetailsAdd(
                            this@AddNewAddressActivity,
                            it.data?.StatusMSG.toString()
                        )
                    }else{
                        AlertDialog().dialogPaymentDetailsAdd(
                            this@AddNewAddressActivity,
                            it.data?.StatusMSG.toString()
                        )
                    }

                    loadingDialog.dismiss()

                }
                is Resources.Error -> {
                    loadingDialog.dismiss()
                }
            }
        }
    }


}