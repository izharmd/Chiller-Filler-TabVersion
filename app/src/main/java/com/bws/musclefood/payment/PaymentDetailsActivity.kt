package com.bws.musclefood.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.database.AppDatabase
import com.bws.musclefood.factory.FactoryProvider
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.AlertDialog
import com.bws.musclefood.utils.LoadingDialog
import com.bws.musclefood.utils.PreferenceConnector
import com.bws.musclefood.utils.Resources
import com.bws.musclefood.viewmodels.PaymentDetailsViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.HttpEntity
import cz.msebera.android.httpclient.entity.StringEntity
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.tool_bar_address.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class PaymentDetailsActivity : AppCompatActivity() {
    lateinit var preferenceConnector: PreferenceConnector
    lateinit var db: AppDatabase

    lateinit var paymentDetailsViewModel: PaymentDetailsViewModel

    var paymentType = "Card"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_details)
        supportActionBar?.hide()

        db = AppDatabase(this)



        preferenceConnector = PreferenceConnector(this)

        txtTxtHeader.text = Constant.paymentDetails
        imvSaveaddress.visibility = View.INVISIBLE



        rdDebitCredit.setOnClickListener() {
            rdDebitCredit.isChecked = true
            rdPayPal.isChecked = false
            rdGpay.isChecked = false
            rdAccountPayment.isChecked = false
            rdCashOnDelivery.isChecked = false

            ll_CardDetails.visibility = View.VISIBLE
            txtPayToCard.visibility = View.GONE
            txtPayToCard.visibility = View.GONE
            ll_PayWithGpay.visibility = View.GONE
            ll_PayWithPayPal.visibility = View.GONE
            //  llAccountPaymet.visibility = View.GONE
            txtPlaceOrder.visibility = View.VISIBLE
            txtPlaceOrder2.visibility = View.GONE
            edtPayWithPayPal.visibility = View.GONE
            edtPayWithGpay.visibility = View.GONE

            paymentType = "Card"
        }
        rdPayPal.setOnClickListener() {
            rdDebitCredit.isChecked = false
            rdPayPal.isChecked = true
            rdGpay.isChecked = false
            rdAccountPayment.isChecked = false
            rdCashOnDelivery.isChecked = false

            ll_CardDetails.visibility = View.GONE
            txtPayToCard.visibility = View.GONE
            //  llAccountPaymet.visibility = View.GONE
            ll_PayWithGpay.visibility = View.GONE
            ll_PayWithPayPal.visibility = View.VISIBLE
            txtPlaceOrder.visibility = View.GONE
            txtPlaceOrder2.visibility = View.GONE

            edtPayWithPayPal.visibility = View.VISIBLE
            edtPayWithGpay.visibility = View.GONE

            paymentType = "Paypal"
        }

        rdGpay.setOnClickListener() {
            rdDebitCredit.isChecked = false
            rdPayPal.isChecked = false
            rdAccountPayment.isChecked = false
            rdGpay.isChecked = true
            rdCashOnDelivery.isChecked = false

            ll_CardDetails.visibility = View.GONE
            txtPayToCard.visibility = View.GONE
            ll_PayWithGpay.visibility = View.VISIBLE
            // llAccountPaymet.visibility = View.GONE
            ll_PayWithPayPal.visibility = View.GONE
            txtPlaceOrder.visibility = View.GONE
            txtPlaceOrder2.visibility = View.GONE

            edtPayWithPayPal.visibility = View.GONE
            edtPayWithGpay.visibility = View.VISIBLE

            paymentType = "GPay"

        }

        rdCashOnDelivery.setOnClickListener() {
            rdDebitCredit.isChecked = false
            rdPayPal.isChecked = false
            rdGpay.isChecked = false
            rdAccountPayment.isChecked = false
            rdCashOnDelivery.isChecked = true

            ll_CardDetails.visibility = View.GONE
            txtPayToCard.visibility = View.GONE
            ll_PayWithGpay.visibility = View.GONE
            // llAccountPaymet.visibility = View.GONE
            ll_PayWithPayPal.visibility = View.GONE
            txtPlaceOrder.visibility = View.VISIBLE
            txtPlaceOrder2.visibility = View.GONE
            edtPayWithPayPal.visibility = View.GONE
            edtPayWithGpay.visibility = View.GONE
        }

        rdAccountPayment.setOnClickListener() {
            rdDebitCredit.isChecked = false
            rdPayPal.isChecked = false
            rdGpay.isChecked = false
            rdCashOnDelivery.isChecked = false
            rdAccountPayment.isChecked = true


            ll_CardDetails.visibility = View.GONE
            txtPayToCard.visibility = View.GONE
            ll_PayWithGpay.visibility = View.GONE
            ll_PayWithPayPal.visibility = View.GONE
            txtPlaceOrder.visibility = View.GONE
            // llAccountPaymet.visibility = View.VISIBLE
            txtPlaceOrder2.visibility = View.VISIBLE

            edtPayWithPayPal.visibility = View.GONE
            edtPayWithGpay.visibility = View.GONE

            paymentType = "Account"

        }


        txtPlaceOrder.setOnClickListener() {
            addPaymentDetails()
        }

        txtPlaceOrder2.setOnClickListener() {


        }

        imvBack.setOnClickListener() {
            finish()
        }
    }

    fun addPaymentDetails() {

        paymentDetailsViewModel = ViewModelProvider(
            this,
            FactoryProvider(Repository(), this)
        ).get(PaymentDetailsViewModel::class.java)

        val body =
            RequestBodies.AddEditPaymentDetails(
                "",
                preferenceConnector.getValueString("USER_ID").toString(),
                paymentType,
                edtCardNO.text.toString(),
                edtCardName.text.toString(),
                edtCVVNo.text.toString(),
                edtCardExpDate.text.toString(),
                "1"
            )

        paymentDetailsViewModel.getPaymentDetails(body)

        val loadingDialog = LoadingDialog.progressDialog(this)

        paymentDetailsViewModel.response.observe(this) {

            when (it) {

                is Resources.Loading -> {
                    loadingDialog.show()
                }
                is Resources.NoInternet -> {

                    loadingDialog.dismiss()

                }
                is Resources.Success -> {

                    val statusCode = it.data?.StatusCode

                    if(statusCode == "200"){
                        AlertDialog().dialogPaymentDetailsAdd(
                            this@PaymentDetailsActivity,
                            it.data?.StatusMSG.toString()
                        )
                    }else{
                        Toast.makeText(
                            this@PaymentDetailsActivity,
                            it.data?.StatusCode.toString(),
                            Toast.LENGTH_SHORT
                        ).show()

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