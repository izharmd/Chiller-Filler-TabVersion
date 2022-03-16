package com.bws.musclefood.orders

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import com.bws.musclefood.R
import kotlinx.android.synthetic.main.accitivity_delivery_option.*
import kotlinx.android.synthetic.main.activity_search_order.*
import kotlinx.android.synthetic.main.tool_bar_address.*
import java.text.SimpleDateFormat
import java.util.*

class SearchOrderActivity:AppCompatActivity() {

    var calFrom = Calendar.getInstance()
    var calTo = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_order)
        supportActionBar?.hide()
        txtTxtHeader.text = "Search Orders"
        imvSaveaddress.visibility = View.GONE

        btnSearchOrder.setOnClickListener(){
            startActivity(Intent(this@SearchOrderActivity,OrderActivity::class.java))
        }


        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                calFrom.set(Calendar.YEAR, year)
                calFrom.set(Calendar.MONTH, monthOfYear)
                calFrom.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        // create an OnDateSetListener
        val dateSetListenerTo = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                calTo.set(Calendar.YEAR, year)
                calTo.set(Calendar.MONTH, monthOfYear)
                calTo.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateToDate()
            }
        }


        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        txtFromDate.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@SearchOrderActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    calFrom.get(Calendar.YEAR),
                    calFrom.get(Calendar.MONTH),
                    calFrom.get(Calendar.DAY_OF_MONTH)).show()
            }
        })


        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        txtToDate.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@SearchOrderActivity,
                    dateSetListenerTo,
                    // set DatePickerDialog to point to today's date when it loads up
                    calTo.get(Calendar.YEAR),
                    calTo.get(Calendar.MONTH),
                    calTo.get(Calendar.DAY_OF_MONTH)).show()
            }
        })

        imvBack.setOnClickListener(){
            finish()
        }
    }

    private fun updateDateInView() {
        val myFormat = "dd-MM-yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        txtFromDate.text = sdf.format(calFrom.getTime())
    }

    private fun updateToDate() {
        val myFormat = "dd-MM-yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        txtToDate.text = sdf.format(calTo.getTime())
    }
}