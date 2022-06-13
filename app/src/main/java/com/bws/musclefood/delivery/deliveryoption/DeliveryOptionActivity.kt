package com.bws.musclefood.delivery.deliveryoption

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.common.Constant.Companion.deliveryDate
import com.bws.musclefood.delivery.choosedeliveryaddress.ChooseDeliveryAddressActivity
import com.bws.musclefood.delivery.deliveryoption.viewcartItems.ViewCartItemAdapter
import com.bws.musclefood.delivery.deliveryoption.viewcartItems.ViewItemActivity
import com.bws.musclefood.factory.FactoryProvider
import com.bws.musclefood.itemcategory.cartlist.CartListActivity
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.payment.PaymentActivity
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.AlertDialog
import com.bws.musclefood.utils.LoadingDialog
import com.bws.musclefood.utils.PreferenceConnector
import com.bws.musclefood.utils.Resources
import com.bws.musclefood.viewmodels.DeliveryOptionViewModel
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration
import com.google.gson.Gson
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.HttpEntity
import cz.msebera.android.httpclient.entity.StringEntity
import kotlinx.android.synthetic.main.accitivity_delivery_option.*
import kotlinx.android.synthetic.main.tool_bar_address.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class DeliveryOptionActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    val myCalendar = Calendar.getInstance()
    var year: Int = 0
    var month: Int = 0
    var day: Int = 0
    lateinit var deliveryOptionViewModel: DeliveryOptionViewModel
    lateinit var preferenceConnector: PreferenceConnector
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.accitivity_delivery_option)
        supportActionBar?.hide()

        preferenceConnector = PreferenceConnector(this)
        imvSaveaddress.visibility = View.GONE
        txtTxtHeader.text = "Delivery Options"


        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        txtDeliveryDate.text = currentDate

        val date =
            OnDateSetListener { view, year, month, dayOfMonth ->
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = month
                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth

                val myFormat = "dd-MM-yyyy" //put your date format in which you need to display
                val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)
                txtDeliveryDate.setText(sdf.format(myCalendar.time))
            }


        txtDeliveryDate.setOnClickListener {
            val datePicker: DatePickerDialog
            year = myCalendar[Calendar.YEAR]
            month = myCalendar[Calendar.MONTH]
            day = myCalendar[Calendar.DAY_OF_MONTH]
            datePicker = DatePickerDialog(this@DeliveryOptionActivity, date, year, month, day)
            datePicker.datePicker.minDate = System.currentTimeMillis()
            // datePicker.datePicker.calendarViewShown = false
            datePicker.show()
        }

        txtChangeAddress.setOnClickListener {
            Constant.deliveryAddress = "Choose Delivery Address"

            startActivity(
                Intent(
                    this@DeliveryOptionActivity,
                    ChooseDeliveryAddressActivity::class.java
                )
            )
        }

        txtAddAddress.setOnClickListener {
            startActivity(
                Intent(
                    this@DeliveryOptionActivity,
                    ChooseDeliveryAddressActivity::class.java
                )
            )
        }

        txtViewAllItems.setOnClickListener {
            startActivity(Intent(this@DeliveryOptionActivity, ViewItemActivity::class.java))
        }

        txtProceedToPay.setOnClickListener {
            Constant.hidePaymentSection = "NO"
            Constant.paymentDetails = "Payment"
            val myFormat = "dd MMM yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            deliveryDate = sdf.format(myCalendar.getTime())

            startActivity(Intent(this@DeliveryOptionActivity, PaymentActivity::class.java))
        }

        imvBack.setOnClickListener {
            finish()
        }


        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.arrDeliveryTime,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spDeliveryTime.adapter = adapter
        spDeliveryTime.onItemSelectedListener = this


        var body = JSONObject()
        body.put("UserID", preferenceConnector.getValueString("USER_ID"))


    }


    override fun onNothingSelected(parent: AdapterView<*>?) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Constant.deliveryTime = parent?.getItemAtPosition(position).toString()

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


        var body = RequestBodies.GetDeliveryDetails(
            preferenceConnector.getValueString("USER_ID").toString()
        )
        // body.put("UserID", preferenceConnector.getValueString("USER_ID").toString())

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

                    if (it.data?.size != 0) {

                        val fullAddress = it.data?.get(0)?.DeliveryAddressHouseNumber + " " +
                                it.data?.get(0)?.DeliveryAddressLine1 + " " +
                                it.data?.get(0)?.DeliveryAddressLine2 + " " +
                                it.data?.get(0)?.DeliveryCity + " " +
                                it.data?.get(0)?.DeliveryPostcode + " " +
                                it.data?.get(0)?.DeliveryContactNumber
                        txtFullAddress.text = fullAddress
                        txtDeliveredTo.text =
                            "Delivery to : " + it.data?.get(0)?.DeliveryAddressHouseNumber + "(Default)"

                        txtAddAddress.visibility = View.GONE
                        txtChangeAddress.visibility = View.VISIBLE

                    } else {
                        txtAddAddress.visibility = View.VISIBLE
                        txtChangeAddress.visibility = View.GONE

                        // txtFullAddress.visibility = View.GONE
                        // txtDeliveredTo.visibility = View.GONE
                    }

                    loadingDialog.dismiss()
                }
                is Resources.Error -> {
                    loadingDialog.dismiss()
                }
            }
        }
    }


    fun placeOrder(body: JSONObject) {
        val loadingDialog = LoadingDialog.progressDialog(this)
        val client = AsyncHttpClient()
        val entity: HttpEntity = try {
            StringEntity(body.toString(), "UTF-8")
        } catch (e: IllegalArgumentException) {
            Log.d("HTTP", "StringEntity: IllegalArgumentException")
            return
        }
        val contentType = "application/json; charset=utf-8"
        client.post(
            this,
            Constant.BASE_URL + "GetDeliveryDetails",
            entity,
            contentType,
            object : AsyncHttpResponseHandler() {

                override fun onStart() {
                    super.onStart()
                    loadingDialog.show()
                }

                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<Header>,
                    responseBody: ByteArray
                ) {
                    val asyncResult = String(responseBody)


                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<Header>,
                    responseBody: ByteArray,
                    error: Throwable
                ) {
                    Toast.makeText(
                        this@DeliveryOptionActivity,
                        statusCode.toString(),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onFinish() {
                    super.onFinish()
                    loadingDialog.dismiss()
                }
            })
    }
}