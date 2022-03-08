package com.bws.musclefood.payment

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.bws.musclefood.R
import com.bws.musclefood.urils.AlertDialog
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.tool_bar_address.*

class PaymentActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        supportActionBar?.hide()

        txtTxtHeader.text = "Payment"
        imvSaveaddress.visibility = View.INVISIBLE

        rdDebitCredit.setOnClickListener(){
            rdDebitCredit.isChecked = true
            rdPayPal.isChecked = false
            rdGpay.isChecked = false
            rdAccountPayment.isChecked = false
            rdCashOnDelivery.isChecked = false

            ll_CardDetails.visibility = View.VISIBLE
            txtPayToCard.visibility = View.VISIBLE
            ll_PayWithGpay.visibility = View.GONE
            ll_PayWithPayPal.visibility = View.GONE
            llAccountPaymet.visibility = View.GONE
            txtPlaceOrder.visibility = View.GONE
        }
        rdPayPal.setOnClickListener(){
            rdDebitCredit.isChecked = false
            rdPayPal.isChecked = true
            rdGpay.isChecked = false
            rdAccountPayment.isChecked = false
            rdCashOnDelivery.isChecked = false

            ll_CardDetails.visibility = View.GONE
            txtPayToCard.visibility = View.GONE
            llAccountPaymet.visibility = View.GONE
            ll_PayWithGpay.visibility = View.GONE
            ll_PayWithPayPal.visibility = View.VISIBLE
            txtPlaceOrder.visibility = View.GONE
        }

        rdGpay.setOnClickListener(){
            rdDebitCredit.isChecked = false
            rdPayPal.isChecked = false
            rdAccountPayment.isChecked = false
            rdGpay.isChecked = true
            rdCashOnDelivery.isChecked = false

            ll_CardDetails.visibility = View.GONE
            txtPayToCard.visibility = View.GONE
            ll_PayWithGpay.visibility = View.VISIBLE
            llAccountPaymet.visibility = View.GONE
            ll_PayWithPayPal.visibility = View.GONE
            txtPlaceOrder.visibility = View.GONE
        }

        rdCashOnDelivery.setOnClickListener(){
            rdDebitCredit.isChecked = false
            rdPayPal.isChecked = false
            rdGpay.isChecked = false
            rdAccountPayment.isChecked = false
            rdCashOnDelivery.isChecked = true

            ll_CardDetails.visibility = View.GONE
            txtPayToCard.visibility = View.GONE
            ll_PayWithGpay.visibility = View.GONE
            llAccountPaymet.visibility = View.GONE
            ll_PayWithPayPal.visibility = View.GONE
            txtPlaceOrder.visibility = View.VISIBLE
        }

        rdAccountPayment.setOnClickListener(){
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
            llAccountPaymet.visibility = View.VISIBLE

        }

        txtPlaceOrder.setOnClickListener(){
            AlertDialog().dialogPaymentSuccessFull(this,"Your order has been placed successfully.")
        }

        txtPlaceOrder2.setOnClickListener(){
            AlertDialog().dialogPaymentSuccessFull(this,"Your order has been placed successfully.")
        }

        imvBack.setOnClickListener(){
            finish()
        }
    }
}