package com.bws.musclefood.payment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.common.Constant.Companion.INSERTPLACEORDERDETAILS
import com.bws.musclefood.common.Constant.Companion.totalBasketValue
import com.bws.musclefood.database.AppDatabase
import com.bws.musclefood.itemcategory.productlist.ProductListActivity
import com.bws.musclefood.utils.AlertDialog
import com.bws.musclefood.utils.CreditCardTextFormatter
import com.bws.musclefood.utils.LoadingDialog
import com.bws.musclefood.utils.PreferenceConnector
import com.bws.musclefood.viewmodels.OrderPlaceViewModel
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

class PaymentActivity : AppCompatActivity() {

    lateinit var preferenceConnector: PreferenceConnector

    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        supportActionBar?.hide()

        db = AppDatabase(this)

        // edtCard.addTextChangedListener(CreditCardTextFormatter())

        preferenceConnector = PreferenceConnector(this)

        txtTxtHeader.text = Constant.paymentDetails
        imvSaveaddress.visibility = View.INVISIBLE

        txtBasketValue.text = "£$totalBasketValue"
        txtTotalPayable.text = "£$totalBasketValue"



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

        }


        txtPlaceOrder.setOnClickListener() {
          /*  val jsonObject = JSONObject()
            jsonObject.put("UserID", preferenceConnector.getValueString("USER_ID").toString())
            jsonObject.put("SessionID", Constant.sessionID)
            jsonObject.put("EmailID", preferenceConnector.getValueString("EMAIL_ID").toString())
            jsonObject.put("PaymentType", "Card")
            jsonObject.put("DeliveryAddressType", "Office")
            jsonObject.put("DeliveryDate", Constant.deliveryDate)
            jsonObject.put("DeliveryTime", Constant.deliveryTime)
            jsonObject.put("DeliveryCharge", "")
            jsonObject.put("TotalAmount", Constant.TotalPrice.toString())
            jsonObject.put("OrderItems", Constant.jsonOrder)

            println("PAYMENT JSON==" + jsonObject)*/


            var jsonObj = JSONObject()
            var jsonarr = JSONArray()
            jsonObj.put("UserID", preferenceConnector.getValueString("USER_ID").toString())
            jsonObj.put("SessionID", Constant.sessionID)
            jsonObj.put("EmailID", preferenceConnector.getValueString("EMAIL_ID").toString())
            jsonObj.put("PaymentType", "Card")
            jsonObj.put("DeliveryAddressType", "Office")
            jsonObj.put("DeliveryDate", Constant.deliveryDate)
            jsonObj.put("DeliveryTime", Constant.deliveryTime)
            jsonObj.put("DeliveryCharge", "")
            jsonObj.put("DeliverySlot", "Pre 12")
            jsonObj.put("CardNumber", edtCardNO.text.toString())
            jsonObj.put("OnCardName", edtCardName.text.toString())
            jsonObj.put("CardCVVNumber", edtCVVNo.text.toString())
            jsonObj.put("CardExpiryDate", edtCardExpDate.text.toString())
            jsonObj.put("PaymentGatewayID", "Card Payment")
            jsonObj.put("TotalAmount", Constant.TotalPrice.toString())


            GlobalScope.launch(Dispatchers.Main) {
                val productList = db.contactDao().getProductItem()

                for (i in productList.indices) {
                    var innerJoson = JSONObject()
                    innerJoson.put("CartItemID", productList[i].CartItemID)
                    innerJoson.put("ProductID", productList[i].ProductID)
                    innerJoson.put("ProductName", productList[i].ProductName)
                    innerJoson.put("ProductQuantity", productList[i].Quantity)
                    innerJoson.put("ProductPrice", productList[i].Price)
                    innerJoson.put("ProductTotalPrice", productList[i].FormattedProductTotalPrice)
                    jsonarr.put(innerJoson)

                }


                val finalJson = jsonObj.put("OrderItems", jsonarr)
                Log.d("ALL DATA===", finalJson.toString())
            }





            //PLACE ORDER
          //  placeOrder(jsonObject)

        }

        txtPlaceOrder2.setOnClickListener() {
            val jsonObject = JSONObject()
            jsonObject.put("UserID", preferenceConnector.getValueString("USER_ID").toString())
            jsonObject.put("SessionID", Constant.sessionID)
            jsonObject.put("EmailID", preferenceConnector.getValueString("EMAIL_ID").toString())
            jsonObject.put("PaymentType", "Card")
            jsonObject.put("DeliveryAddressType", "Office")
            jsonObject.put("DeliveryDate", Constant.deliveryDate)
            jsonObject.put("DeliveryTime", Constant.deliveryTime)
            jsonObject.put("DeliveryCharge", "")
            jsonObject.put("TotalAmount", Constant.TotalPrice)
            jsonObject.put("OrderItems", Constant.jsonOrder)

            println("PAYMENT JSON==" + jsonObject)

            //PLACE ORDER
            placeOrder(jsonObject)
        }



        if (Constant.hidePaymentSection.equals("YES")) {
            llPaymentSection.visibility = View.GONE
        } else {
            llPaymentSection.visibility = View.VISIBLE
        }

        imvBack.setOnClickListener() {
            finish()
        }
    }


    //PLACE ORDER
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
            Constant.BASE_URL + INSERTPLACEORDERDETAILS,
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
                    val result = JSONObject(asyncResult)
                    if (statusCode == 200) {
                        AlertDialog().dialogPaymentSuccessFull(
                            this@PaymentActivity,
                            result.get("StatusMSG").toString()
                        )
                    } else {
                        Toast.makeText(
                            this@PaymentActivity,
                            result.get("StatusMSG").toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<Header>,
                    responseBody: ByteArray,
                    error: Throwable
                ) {
                    Toast.makeText(this@PaymentActivity, statusCode.toString(), Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onFinish() {
                    super.onFinish()
                    loadingDialog.dismiss()
                }
            })
    }
}