package com.bws.musclefood.delivery

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bws.musclefood.R
import com.bws.musclefood.factory.FactoryProvider
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.AlertDialog
import com.bws.musclefood.utils.LoadingDialog
import com.bws.musclefood.utils.PreferenceConnector
import com.bws.musclefood.utils.Resources
import com.bws.musclefood.viewmodels.AddNewAddressViewModel
import kotlinx.android.synthetic.main.activity_add_new_address.*
import kotlinx.android.synthetic.main.tool_bar_address.*

class AddNewAddressActivity : AppCompatActivity() {

    lateinit var addNewAddressViewModel: AddNewAddressViewModel
    lateinit var preferenceConnector: PreferenceConnector
    var defaultAddress: String = "N"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_address)
        supportActionBar?.hide()

        preferenceConnector = PreferenceConnector(this)


        txtHome.setOnClickListener() {
            txtHome.setBackgroundResource(R.drawable.round_address_selected)
            txtHome.setTextColor(Color.parseColor("#FFB300"))

            txtOffice.setBackgroundResource(R.drawable.round_address)
            txtOffice.setTextColor(Color.parseColor("#000000"))

            txtOthers.setBackgroundResource(R.drawable.round_address)
            txtOthers.setTextColor(Color.parseColor("#000000"))
        }

        txtOffice.setOnClickListener() {
            txtOffice.setBackgroundResource(R.drawable.round_address_selected)
            txtOffice.setTextColor(Color.parseColor("#FFB300"))

            txtHome.setBackgroundResource(R.drawable.round_address)
            txtHome.setTextColor(Color.parseColor("#000000"))

            txtOthers.setBackgroundResource(R.drawable.round_address)
            txtOthers.setTextColor(Color.parseColor("#000000"))
        }

        txtOthers.setOnClickListener() {
            txtOffice.setBackgroundResource(R.drawable.round_address)
            txtOffice.setTextColor(Color.parseColor("#000000"))

            txtHome.setBackgroundResource(R.drawable.round_address)
            txtHome.setTextColor(Color.parseColor("#000000"))

            txtOthers.setBackgroundResource(R.drawable.round_address_selected)
            txtOthers.setTextColor(Color.parseColor("#FFB300"))
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
                edtZipCode.text.isEmpty() -> {
                    AlertDialog().dialog(this, "Please enter zipcode")
                }
                else -> {
                    addNewAddress()
                }
            }
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


        val body =
            RequestBodies.AddEditDeliveryDetails(
                "",
                preferenceConnector.getValueString("USER_ID").toString(),
                preferenceConnector.getValueString("FULL_NAME").toString(),
                edtHouseNo.text.toString(),
                edtAddressLine1.text.toString(),
                edtAddressLine2.text.toString(),
                edtCity.text.toString(),
                edtZipCode.text.toString(),
                "9163252224",
                "",
                "",
                defaultAddress
            )

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
                        AlertDialog().dialogPaymentSuccessFull(
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