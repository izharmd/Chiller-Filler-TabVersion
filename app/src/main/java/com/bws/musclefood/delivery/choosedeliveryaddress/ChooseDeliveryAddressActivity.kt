package com.bws.musclefood.delivery.choosedeliveryaddress

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.delivery.AddNewAddressActivity
import com.bws.musclefood.factory.FactoryProvider
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.LoadingDialog
import com.bws.musclefood.utils.PreferenceConnector
import com.bws.musclefood.utils.Resources
import com.bws.musclefood.viewmodels.DeliveryOptionViewModel
import kotlinx.android.synthetic.main.accitivity_delivery_option.*
import kotlinx.android.synthetic.main.activity_choose_delivery_address.*
import kotlinx.android.synthetic.main.activity_choose_delivery_address.txtFullAddress
import kotlinx.android.synthetic.main.tool_bar_address.*
import org.json.JSONObject

class ChooseDeliveryAddressActivity : AppCompatActivity() {

    lateinit var deliveryOptionViewModel: DeliveryOptionViewModel
    lateinit var preferenceConnector: PreferenceConnector


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_delivery_address)
        supportActionBar?.hide()

        preferenceConnector = PreferenceConnector(this)

        imvSaveaddress.visibility = View.GONE
        txtTxtHeader.text = Constant.deliveryAddress

        recyAddress.layoutManager = LinearLayoutManager(this)

        /*  val data = ArrayList<ChooseDelModel>()
          data.add(ChooseDelModel("Office","Mr Joseph","32, My Street, Kingston, UK 12401.","342603450"))
          data.add(ChooseDelModel("Home","Mr John Smith","132, My Street,Bigtown BG23 4YZ UK","2489003450"))

          val adapter = ChooseDelAdapter(data)
          recyAddress.adapter = adapter
          adapter.notifyDataSetChanged()*/

        txtAddNewAddress.setOnClickListener() {
            Constant.addressType = "NewAddress"
            startActivity(

                Intent(
                    this@ChooseDeliveryAddressActivity,
                    AddNewAddressActivity::class.java
                )
            )
        }


        rdDefaultAddress.setOnClickListener() {
            rdDefaultAddress.isChecked = true
            rdHomeAddress.isChecked = false
        }

        rdHomeAddress.setOnClickListener() {
            rdDefaultAddress.isChecked = false
            rdHomeAddress.isChecked = true
        }

        txtEditAddress.setOnClickListener() {
            startActivity(
                Intent(
                    this@ChooseDeliveryAddressActivity,
                    AddNewAddressActivity::class.java
                )
            )
        }

        txtEditAddress1.setOnClickListener() {
            startActivity(
                Intent(
                    this@ChooseDeliveryAddressActivity,
                    AddNewAddressActivity::class.java
                )
            )
        }


        imvBack.setOnClickListener() {
            finish()
        }
       // getDeliveryDetails()

    }

    override fun onResume() {
        super.onResume()
        getDeliveryDetails()
    }


    fun getDeliveryDetails() {

        deliveryOptionViewModel = ViewModelProvider(
            this,
            FactoryProvider(Repository(), this)
        ).get(DeliveryOptionViewModel::class.java)

        var body = RequestBodies.GetDeliveryDetails(preferenceConnector.getValueString("USER_ID").toString())

        deliveryOptionViewModel.getDeliveryList(body)
        val loadingDialog = LoadingDialog.progressDialog(this)

        deliveryOptionViewModel.addressList.observe(this) {

            when (it) {

                is Resources.Loading -> {
                    loadingDialog.show()
                }
                is Resources.NoInternet -> {
                    loadingDialog.dismiss()
                }
                is Resources.Success -> {
                    val data = ArrayList<ChooseDelModel>()
                    val size = it.data?.size!!
                    val listData = it.data!!
                    for (i in 0 until size) {
                        data.add(
                            ChooseDelModel(
                                listData[i].ID,
                                preferenceConnector.getValueString("USER_NAME").toString(),
                                listData[i].DeliveryAddressName,
                                listData[i].DeliveryAddressHouseNumber,
                                listData[i].DeliveryAddressLine1,
                                listData[i].DeliveryAddressLine2,
                                listData[i].DeliveryCity,
                                listData[i].DeliveryPostcode,
                                listData[i].DeliveryContactNumber,
                                listData[i].DeliveryDate,
                                listData[i].DeliverySlot,
                                listData[i].DefaultAddressFlag,
                            )
                        )
                    }

                    val adapter = ChooseDelAdapter(data)
                    recyAddress.adapter = adapter
                    adapter.notifyDataSetChanged()
                    loadingDialog.dismiss()

                }
                is Resources.Error -> {
                    loadingDialog.dismiss()
                }
            }
        }
    }
}