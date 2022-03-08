package com.bws.musclefood.delivery.deliveryoption

import android.app.DatePickerDialog
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
import com.bws.musclefood.delivery.AddNewAddressActivity
import com.bws.musclefood.delivery.choosedeliveryaddress.ChooseDeliveryAddressActivity
import com.bws.musclefood.delivery.deliveryoption.viewcartItems.ViewCartItemAdapter
import com.bws.musclefood.itemcategory.cartlist.CartListActivity
import com.bws.musclefood.itemcategory.cartlist.CartListModel
import com.bws.musclefood.itemcategory.productlist.addquentity.QuentityAdapater
import com.bws.musclefood.itemcategory.productlist.addquentity.QuentityModel
import com.bws.musclefood.payment.PaymentActivity
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration
import kotlinx.android.synthetic.main.accitivity_delivery_option.*
import kotlinx.android.synthetic.main.tool_bar_address.*
import java.text.SimpleDateFormat
import java.util.*

class DeliveryOptionActivity:AppCompatActivity(), AdapterView.OnItemSelectedListener{

    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.accitivity_delivery_option)
        supportActionBar?.hide()
        imvSaveaddress.visibility = View.GONE
        txtTxtHeader.text = "Delivery Options"



        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }


        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        txtDeliveryDate.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@DeliveryOptionActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })



        txtChangeAddress.setOnClickListener(){
            startActivity(Intent(this@DeliveryOptionActivity, ChooseDeliveryAddressActivity::class.java))
        }

        txtViewAllItems.setOnClickListener(){
            //dialogViewProduct("qwertyu")
            startActivity(Intent(this@DeliveryOptionActivity, CartListActivity::class.java))
        }

        txtProceedToPay.setOnClickListener(){
            startActivity(Intent(this@DeliveryOptionActivity, PaymentActivity::class.java))
        }

        imvBack.setOnClickListener(){
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val text: String = parent?.getItemAtPosition(position).toString()

    }



    private fun updateDateInView() {
        val myFormat = "dd-MM-yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        txtDeliveryDate.text = sdf.format(cal.getTime())
    }


    fun dialogViewProduct(pName:String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_view_cart_item)
        dialog.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val recyQuentity: RecyclerView = dialog.findViewById(R.id.recyQuentity)
        val imv_cross: ImageView = dialog.findViewById(R.id.imv_cross)
        val txtProductName: TextView = dialog.findViewById(R.id.txtProductName)
        txtProductName.text = Constant.addDataToCart.size.toString() +" Items" + "Total : £ 450.90"

        recyQuentity.layoutManager = LinearLayoutManager(this)

        val dividerDrawable =
            ContextCompat.getDrawable(this, R.drawable.line_divider)
        recyQuentity.addItemDecoration(DividerItemDecoration(dividerDrawable))


        val adapterTop = ViewCartItemAdapter(Constant.addDataToCart)
        recyQuentity.adapter = adapterTop
        adapterTop.notifyDataSetChanged()

        imv_cross.setOnClickListener(){
            dialog.dismiss()
        }

        dialog.show()
    }
}