package com.bws.musclefood.orders.searchorder

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant.Companion.fromDate
import com.bws.musclefood.common.Constant.Companion.searchOrderNo
import com.bws.musclefood.common.Constant.Companion.toDate
import com.bws.musclefood.factory.FactoryProvider
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.orders.OrderActivity
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.AlertDialog
import com.bws.musclefood.utils.LoadingDialog
import com.bws.musclefood.utils.PreferenceConnector
import com.bws.musclefood.utils.Resources
import com.bws.musclefood.viewmodels.SearchOrderViewModel
import kotlinx.android.synthetic.main.activity_search_order.*
import kotlinx.android.synthetic.main.tool_bar_address.*
import java.text.SimpleDateFormat
import java.util.*

class SearchOrderActivity : AppCompatActivity() {

    var calFrom = Calendar.getInstance()
    var calTo = Calendar.getInstance()

    val dateFormat = "dd-MMM-yyyy"

    lateinit var searchOrderViewModel: SearchOrderViewModel
    lateinit var preferenceConnector: PreferenceConnector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_order)
        supportActionBar?.hide()
        txtTxtHeader.text = "Search Orders"
        imvSaveaddress.visibility = View.GONE

        preferenceConnector = PreferenceConnector(this)

        btnSearchOrder.setOnClickListener() {
            if (edtOrderNo.text.isNotEmpty() && txtFromDate.text.toString()
                    .isEmpty() && txtToDate.text.toString().isEmpty()
            ) {
                searchOrderNo = edtOrderNo.text.toString()
                fromDate = ""
                toDate = ""
                startActivity(Intent(this@SearchOrderActivity, OrderActivity::class.java))
            } else if (edtOrderNo.text.isEmpty() && txtFromDate.text.toString()
                    .isNotEmpty() && txtToDate.text.toString().isNotEmpty()
            ) {
                searchOrderNo = ""
                val sdf = SimpleDateFormat(dateFormat, Locale.US)
                fromDate = sdf.format(calFrom.time)
                toDate = sdf.format(calTo.time)
                startActivity(Intent(this@SearchOrderActivity, OrderActivity::class.java))
            } else {
                AlertDialog().dialog(this, "Please enter order number or select date")
            }

           // startActivity(Intent(this@SearchOrderActivity, OrderActivity::class.java))
        }


        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                calFrom.set(Calendar.YEAR, year)
                calFrom.set(Calendar.MONTH, monthOfYear)
                calFrom.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        // create an OnDateSetListener
        val dateSetListenerTo = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                calTo.set(Calendar.YEAR, year)
                calTo.set(Calendar.MONTH, monthOfYear)
                calTo.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateToDate()
            }
        }


        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        txtFromDate.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(
                    this@SearchOrderActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    calFrom.get(Calendar.YEAR),
                    calFrom.get(Calendar.MONTH),
                    calFrom.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        })


        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        txtToDate.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(
                    this@SearchOrderActivity,
                    dateSetListenerTo,
                    // set DatePickerDialog to point to today's date when it loads up
                    calTo.get(Calendar.YEAR),
                    calTo.get(Calendar.MONTH),
                    calTo.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        })

        imvBack.setOnClickListener() {
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


   /* fun searchOrderAPI() {
        searchOrderViewModel = ViewModelProvider(
            this,
            FactoryProvider(Repository(), this)
        ).get(SearchOrderViewModel::class.java)

        val body = RequestBodies.SearchOrdersBody(preferenceConnector.getValueString("USER_ID").toString(), "1", "", "3 March 2022", "15 May 2022","CURRENT")

        searchOrderViewModel.getSearchOrder(body)

        val loadingDialog = LoadingDialog.progressDialog(this)

        searchOrderViewModel.resultSearchOrder.observe(this) {
            when (it) {
                is Resources.Loading -> {
                    loadingDialog.show()
                }
                is Resources.NoInternet -> {
                    loadingDialog.dismiss()
                }
                is Resources.Success -> {


                }
                is Resources.Error -> {

                }
            }
        }

    }*/
}