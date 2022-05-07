package com.bws.musclefood.delivery.deliveryoption

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.common.Constant.Companion.deliveryDate
import com.bws.musclefood.delivery.choosedeliveryaddress.ChooseDeliveryAddressActivity
import com.bws.musclefood.delivery.deliveryoption.viewcartItems.ViewCartItemAdapter
import com.bws.musclefood.itemcategory.cartlist.CartListActivity
import com.bws.musclefood.payment.PaymentActivity
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration
import kotlinx.android.synthetic.main.accitivity_delivery_option.*
import kotlinx.android.synthetic.main.tool_bar_address.*
import java.text.SimpleDateFormat
import java.util.*


class DeliveryOptionActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    val myCalendar = Calendar.getInstance()
    var year:Int = 0
    var month:Int = 0
    var day:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.accitivity_delivery_option)
        supportActionBar?.hide()
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

        txtViewAllItems.setOnClickListener {
            startActivity(Intent(this@DeliveryOptionActivity, CartListActivity::class.java))
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
    }


    override fun onNothingSelected(parent: AdapterView<*>?) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Constant.deliveryTime = parent?.getItemAtPosition(position).toString()

    }

    fun dialogViewProduct(pName: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_view_cart_item)
        dialog.getWindow()
            ?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val recyQuentity: RecyclerView = dialog.findViewById(R.id.recyQuentity)
        val imv_cross: ImageView = dialog.findViewById(R.id.imv_cross)
        val txtProductName: TextView = dialog.findViewById(R.id.txtProductName)
        txtProductName.text = Constant.addDataToCart.size.toString() + " Items" + "Total : £ 450.90"

        recyQuentity.layoutManager = LinearLayoutManager(this)

        val dividerDrawable =
            ContextCompat.getDrawable(this, R.drawable.line_divider)
        recyQuentity.addItemDecoration(DividerItemDecoration(dividerDrawable))


        val adapterTop = ViewCartItemAdapter(Constant.addDataToCart)
        recyQuentity.adapter = adapterTop
        adapterTop.notifyDataSetChanged()

        imv_cross.setOnClickListener() {
            dialog.dismiss()
        }

        dialog.show()
    }
}