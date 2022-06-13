package com.bws.musclefood.payment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.common.Constant.Companion.GET_PAYMENT_DETAILS
import com.bws.musclefood.common.Constant.Companion.INSERTPLACEORDERDETAILS
import com.bws.musclefood.common.Constant.Companion.orderType
import com.bws.musclefood.common.Constant.Companion.totalBasketValue
import com.bws.musclefood.database.AppDatabase
import com.bws.musclefood.factory.FactoryProvider
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.AlertDialog
import com.bws.musclefood.utils.LoadingDialog
import com.bws.musclefood.utils.PreferenceConnector
import com.bws.musclefood.utils.Resources
import com.bws.musclefood.viewmodels.DeliveryOptionViewModel
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
    lateinit var deliveryOptionViewModel: DeliveryOptionViewModel
    var jsonObj = JSONObject()
    var jsonarr = JSONArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        supportActionBar?.hide()

        db = AppDatabase(this)

        preferenceConnector = PreferenceConnector(this)

        txtTxtHeader.text = Constant.paymentDetails
        imvSaveaddress.visibility = View.INVISIBLE

        txtBasketValue.text = "£$totalBasketValue"
        txtTotalPayable.text = "£$totalBasketValue"

        getDeliveryDetails()

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
            txtAccoutPayment.visibility = View.GONE
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
            txtAccoutPayment.visibility = View.GONE

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
            txtAccoutPayment.visibility = View.GONE

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
            txtAccoutPayment.visibility = View.GONE
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
            txtAccoutPayment.visibility = View.VISIBLE

            edtPayWithPayPal.visibility = View.GONE
            edtPayWithGpay.visibility = View.GONE

        }


        ll_PayWithPayPal.setOnClickListener() {
            Toast.makeText(this@PaymentActivity, "work in progress", Toast.LENGTH_SHORT).show()
        }

        ll_PayWithGpay.setOnClickListener() {
            Toast.makeText(this@PaymentActivity, "work in progress", Toast.LENGTH_SHORT).show()
        }

        txtPlaceOrder.setOnClickListener() {

            when {
                edtCardNO.text!!.isEmpty() -> {
                    AlertDialog().dialog(this, "Please enter card no")
                }

                edtCardExpDate.text.isEmpty() -> {
                    AlertDialog().dialog(this, "Please enter cart expiry date")
                }
                edtCVVNo.text.isEmpty() -> {
                    AlertDialog().dialog(this, "Please enter cvv no")
                }
                edtCardName.text.isEmpty() -> {
                    AlertDialog().dialog(this, "Please enter card holder name no")
                }
                else -> {
                   // preferenceConnector.saveString("FIRST_NAME",it.data?.FirstName.toString())
                    //preferenceConnector.saveString("LAST_NAME",it.data?.LastName.toString())
                    jsonObj.put("UserID", preferenceConnector.getValueString("USER_ID").toString())
                    jsonObj.put("FirstName", preferenceConnector.getValueString("FIRST_NAME").toString())
                    jsonObj.put("LastName", preferenceConnector.getValueString("LAST_NAME").toString())

                    jsonObj.put("SessionID", Constant.sessionID)
                    jsonObj.put(
                        "EmailID",
                        preferenceConnector.getValueString("EMAIL_ID").toString()
                    )
                    jsonObj.put("PaymentType", "Card")
                    jsonObj.put("DeliveryAddressType", Constant.dFaultFlag)
                    jsonObj.put("DeliveryDate", Constant.deliveryDate)
                    jsonObj.put("DeliveryTime", Constant.deliveryTime)
                    jsonObj.put("DeliveryCharge", "")
                    jsonObj.put("DeliverySlot", "")
                    jsonObj.put("CardNumber", edtCardNO.text.toString())
                    jsonObj.put("OnCardName", edtCardName.text.toString())
                    jsonObj.put("CardCVVNumber", edtCVVNo.text.toString())
                    jsonObj.put("CardExpiryDate", edtCardExpDate.text.toString())
                    jsonObj.put("PaymentGatewayID", "Card Payment")
                    jsonObj.put("TotalAmount", Constant.TotalPrice.toString())


                    if (orderType != "REORDER") {
                        GlobalScope.launch(Dispatchers.Main) {
                            val productList = db.contactDao().getProductItem()

                            for (i in productList.indices) {

                                val totalPrice = productList[i].FormattedProductTotalPrice.drop(2)
                                    .toFloat() * productList[i].Quantity.toInt()

                                var innerJoson = JSONObject()
                                innerJoson.put("CartItemID", productList[i].CartItemID)
                                innerJoson.put("ProductID", productList[i].ProductID)
                                innerJoson.put("ProductName", productList[i].ProductName)
                                innerJoson.put("ProductQuantity", productList[i].Quantity)
                                innerJoson.put("ProductPrice", productList[i].Price)
                                innerJoson.put("ProductTotalPrice", totalPrice)
                                jsonarr.put(innerJoson)

                            }

                            val finalJson = jsonObj.put("OrderItems", jsonarr)
                            Log.d("ALL DATA===", finalJson.toString())
                            //PLACE ORDER
                            placeOrder(finalJson)
                        }
                    } else {

                        GlobalScope.launch(Dispatchers.Main) {
                            val itmProduct = db.contactDao().getReorderItems()
                            var jsonArray = JSONArray()
                            for (i in itmProduct.indices) {
                                var jsonObject = JSONObject()
                                try {
                                    jsonObject.put("CartItemID", "")
                                    jsonObject.put("Price", itmProduct[i].ItemRate)
                                    jsonObject.put("ProductID", itmProduct[i].ProductID)
                                    jsonObject.put("Quantity", itmProduct[i].ItemQty)
                                    jsonObject.put("SessionID", Constant.sessionID);
                                    jsonObject.put("TotalPrice", itmProduct[i].ItemPrice)
                                    jsonObject.put(
                                        "UserID",
                                        preferenceConnector.getValueString("USER_ID").toString()
                                    )
                                    jsonArray.put(jsonObject)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }

                            updateInsertCart(jsonArray)
                        }




                        /*GlobalScope.launch(Dispatchers.Main) {
                            val productList = db.contactDao().getReorderItems()

                            for (i in productList.indices) {
                                var innerJoson = JSONObject()
                                innerJoson.put("CartItemID", "")
                                innerJoson.put("ProductID", productList[i].ProductID)
                                innerJoson.put("ProductName", productList[i].OrderItem)
                                innerJoson.put("ProductQuantity", productList[i].ItemQty)
                                innerJoson.put("ProductPrice", productList[i].ItemRate)
                                innerJoson.put("ProductTotalPrice", productList[i].ItemPrice)
                                jsonarr.put(innerJoson)

                            }

                            val finalJson = jsonObj.put("OrderItems", jsonarr)
                            Log.d("ALL DATA===", finalJson.toString())
                            //PLACE ORDER
                            placeOrder(finalJson)
                        }*/
                    }

                }

            }

            if (Constant.hidePaymentSection.equals("YES")) {
                llPaymentSection.visibility = View.GONE
            } else {
                llPaymentSection.visibility = View.VISIBLE
            }


        }


        txtAccoutPayment.setOnClickListener() {
            var jsonObj = JSONObject()
            var jsonarr = JSONArray()
            jsonObj.put("UserID", preferenceConnector.getValueString("USER_ID").toString())
            jsonObj.put("FirstName", preferenceConnector.getValueString("FIRST_NAME").toString())
            jsonObj.put("LastName", preferenceConnector.getValueString("LAST_NAME").toString())

            jsonObj.put("SessionID", Constant.sessionID)
            jsonObj.put(
                "EmailID",
                preferenceConnector.getValueString("EMAIL_ID").toString()
            )
            jsonObj.put("PaymentType", "Card")
            jsonObj.put("DeliveryAddressType", Constant.dFaultFlag)
            jsonObj.put("DeliveryDate", Constant.deliveryDate)
            jsonObj.put("DeliveryTime", Constant.deliveryTime)
            jsonObj.put("DeliveryCharge", "")
            jsonObj.put("DeliverySlot", "")
            jsonObj.put("CardNumber", "")
            jsonObj.put("OnCardName", "")
            jsonObj.put("CardCVVNumber", "")
            jsonObj.put("CardExpiryDate", "")
            jsonObj.put("PaymentGatewayID", "Account")
            jsonObj.put("TotalAmount", Constant.TotalPrice.toString())


            GlobalScope.launch(Dispatchers.Main) {
                val productList = db.contactDao().getProductItem()

                for (i in productList.indices) {

                    val totalPrice = productList[i].FormattedProductTotalPrice.drop(2)
                        .toFloat() * productList[i].Quantity.toInt()

                    var innerJoson = JSONObject()
                    innerJoson.put("CartItemID", productList[i].CartItemID)
                    innerJoson.put("ProductID", productList[i].ProductID)
                    innerJoson.put("ProductName", productList[i].ProductName)
                    innerJoson.put("ProductQuantity", productList[i].Quantity)
                    innerJoson.put("ProductPrice", productList[i].Price)
                    innerJoson.put("ProductTotalPrice", totalPrice)
                    jsonarr.put(innerJoson)

                }

                val finalJson = jsonObj.put("OrderItems", jsonarr)
                Log.d("ALL DATA===", finalJson.toString())
                //PLACE ORDER
                placeOrder(finalJson)
            }
        }

        imvBack.setOnClickListener() {
            finish()
        }

        GetPaymentDetails()

    }


    //PLACE ORDER
    fun placeOrder(body: JSONObject) {
        val loadingDialog = LoadingDialog.progressDialog(this)
        val client = AsyncHttpClient()

        println("ODER PLACE BODY==="+body)

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
                        Constant.row_index = 0
                        val msg = result.get("StatusMSG").toString()


                        val successMsg =
                            msg.split("\\.".toRegex()).toTypedArray()[0]

                        val orderNo =
                            msg.split("\\.".toRegex()).toTypedArray()[1]
                        AlertDialog().dialogPaymentSuccessFull(
                            this@PaymentActivity,
                            successMsg + "\n" + orderNo
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
                    Toast.makeText(
                        this@PaymentActivity,
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


    fun GetPaymentDetails() {

        val jsonBody = JSONObject()

        jsonBody.put("UserID", preferenceConnector.getValueString("USER_ID").toString())
        val loadingDialog = LoadingDialog.progressDialog(this)
        val client = AsyncHttpClient()
        val entity: HttpEntity = try {
            StringEntity(jsonBody.toString(), "UTF-8")
        } catch (e: IllegalArgumentException) {
            Log.d("HTTP", "StringEntity: IllegalArgumentException")
            return
        }
        val contentType = "application/json; charset=utf-8"
        client.post(
            this,
            Constant.BASE_URL + GET_PAYMENT_DETAILS,
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
                    val result = JSONArray(asyncResult)
                    if (statusCode == 200) {
                        val ID = result.getJSONObject(0).getString("ID")
                        val PaymentType = result.getJSONObject(0).getString("PaymentType")
                        val CardNumber = result.getJSONObject(0).getString("CardNumber")
                        val CardCVVNumber = result.getJSONObject(0).getString("CardCVVNumber")
                        val OnCardName = result.getJSONObject(0).getString("OnCardName")
                        val CardExpiryDate = result.getJSONObject(0).getString("CardExpiryDate")
                        val PaymentGatewayID = result.getJSONObject(0).getString("PaymentGatewayID")

                        edtCardNO.setText(CardNumber)
                        edtCardExpDate.setText(CardExpiryDate)
                        edtCVVNo.setText(CardCVVNumber)
                        edtCardName.setText(OnCardName)

                        val msg = result.toString()



                    } else {
                        Toast.makeText(
                            this@PaymentActivity,
                            "Error. $statusCode",
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
                    Toast.makeText(
                        this@PaymentActivity,
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


    fun getDeliveryDetails() {

        deliveryOptionViewModel = ViewModelProvider(
            this,
            FactoryProvider(Repository(), this)
        ).get(DeliveryOptionViewModel::class.java)

        var body = RequestBodies.GetDeliveryDetails(
            preferenceConnector.getValueString("USER_ID").toString()
        )

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
                    //  val data = ArrayList<ChooseDelModel>()
                    val size = it.data?.size!!
                    val listData = it.data!!
                    for (i in 0 until size) {
                        val default = listData[i].DefaultAddressFlag
                        if (default == "Y") {
                            Constant.dFaultFlag = listData[i].DefaultAddressFlag
                            break
                        }
                    }

                    loadingDialog.dismiss()

                }
                is Resources.Error -> {
                    loadingDialog.dismiss()
                }
            }
        }
    }


    fun updateInsertCart(jsonArray: JSONArray) {
        val client = AsyncHttpClient()
        /*val jsonArray = JSONArray()
        GlobalScope.launch(Dispatchers.Main) {
            val itmProduct = db.contactDao().getReorderItems()

            for (i in itmProduct.indices) {
                var jsonObject = JSONObject()
                try {
                    jsonObject.put("CartItemID", "")
                    jsonObject.put("Price", itmProduct[i].ItemRate)
                    jsonObject.put("ProductID", itmProduct[i].ProductID)
                    jsonObject.put("Quantity", itmProduct[i].ItemQty)
                    jsonObject.put("SessionID", Constant.sessionID);
                    jsonObject.put("TotalPrice", itmProduct[i].ItemPrice)
                    jsonObject.put(
                        "UserID",
                        preferenceConnector.getValueString("USER_ID").toString()
                    )
                    jsonArray.put(jsonObject)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }*/
        println("CART INSERT ==" + jsonArray.toString())

        val entity: HttpEntity
        entity = try {
            StringEntity(jsonArray.toString(), "UTF-8")
        } catch (e: IllegalArgumentException) {
            Log.d("HTTP", "StringEntity: IllegalArgumentException")
            return
        }
        val contentType = "application/json; charset=utf-8"
        client.post(
            this,
            Constant.BASE_URL + "InsertUpdateCartDetails",
            entity,
            contentType,
            object : AsyncHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<Header>,
                    responseBody: ByteArray
                ) {
                    val asyncResult = String(responseBody)
                    getAllCartItem()
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<Header>,
                    responseBody: ByteArray,
                    error: Throwable
                ) {
                    Toast.makeText(
                        this@PaymentActivity,
                        statusCode.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }


    //get all item after insert to cart list for reorder product
    fun getAllCartItem() {
        val client = AsyncHttpClient()

        var jsonObject = JSONObject()

        jsonObject.put("SessionID", Constant.sessionID)
        jsonObject.put("UserID", preferenceConnector.getValueString("USER_ID").toString())



        val entity: HttpEntity
        entity = try {
            StringEntity(jsonObject.toString(), "UTF-8")
        } catch (e: IllegalArgumentException) {
            Log.d("HTTP", "StringEntity: IllegalArgumentException")
            return
        }
        val contentType = "application/json; charset=utf-8"
        client.post(
            this,
            Constant.BASE_URL + "GetAllCartDetails",
            entity,
            contentType,
            object : AsyncHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<Header>,
                    responseBody: ByteArray
                ) {
                    val asyncResult = String(responseBody)

                    val jsonArray = JSONArray(asyncResult)

                    for (i in 0..jsonArray!!.length()-1){
                        val categories = jsonArray.getJSONObject(i)

                        val ptoductTotalPrice = categories.getString("Price").toString().toFloat()

                        var tPrce = ptoductTotalPrice * categories.getString("Quantity").toString().toFloat()

                        var innerJoson = JSONObject()
                        innerJoson.put("CartItemID", categories.getString("CartItemID"))
                        innerJoson.put("ProductID", categories.getString("ProductID"))
                        innerJoson.put("ProductName", categories.getString("ProductName"))
                        innerJoson.put("ProductQuantity", categories.getString("Quantity"))
                        innerJoson.put("ProductPrice", categories.getString("Price"))
                        innerJoson.put("ProductTotalPrice", tPrce)
                        jsonarr.put(innerJoson)

                    }

                    val finalJson = jsonObj.put("OrderItems", jsonarr)
                    Log.d("ALL DATA===", finalJson.toString())

                    placeOrder(finalJson)

                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<Header>,
                    responseBody: ByteArray,
                    error: Throwable
                ) {
                    Toast.makeText(
                        this@PaymentActivity,
                        statusCode.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}