package com.bws.musclefood.payment

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.urils.AlertDialog
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.tool_bar_address.*

class PaymentActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        supportActionBar?.hide()

        txtTxtHeader.text = Constant.paymentDetails
        imvSaveaddress.visibility = View.INVISIBLE

        rdDebitCredit.setOnClickListener(){
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
        rdPayPal.setOnClickListener(){
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

        rdGpay.setOnClickListener(){
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

        rdCashOnDelivery.setOnClickListener(){
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
           // llAccountPaymet.visibility = View.VISIBLE
            txtPlaceOrder2.visibility = View.VISIBLE

            edtPayWithPayPal.visibility = View.GONE
            edtPayWithGpay.visibility = View.GONE

        }

        txtPlaceOrder.setOnClickListener(){
           // AlertDialog().dialogPaymentSuccessFull(this,"Your order has been placed successfully.","Total Order Price : £200.00")
            AlertDialog().dialog(this,"Your order has been placed successfully.\n Total Order Price : £200.00")
        }

        txtPlaceOrder2.setOnClickListener(){
            AlertDialog().dialog(this,"Your order has been placed successfully.\n Total Order Price : £200.00")
        }



        if(Constant.hidePaymentSection.equals("YES")){
            llPaymentSection.visibility = View.GONE
        }else{
            llPaymentSection.visibility = View.VISIBLE
        }

        imvBack.setOnClickListener(){
            finish()
        }
    }
}