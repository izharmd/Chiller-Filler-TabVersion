package com.bws.musclefood.signup

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.databinding.ActivityHomeBinding
import com.bws.musclefood.databinding.ActivitySignUpBinding
import com.bws.musclefood.delivery.deliveryoption.viewcartItems.ViewCartItemAdapter
import com.bws.musclefood.itemcategory.productlist.ProductListActivity
import com.bws.musclefood.urils.AlertDialog
import com.bws.musclefood.urils.Validator
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration
import kotlinx.android.synthetic.main.accitivity_delivery_option.*
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_sign_up)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up)
        supportActionBar?.hide()

        val content = SpannableString("LOGIN")
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        txtAllreadyAccount.text = content



        binding.btnRegister.setOnClickListener() {

            if (binding.edtFirstName.text.isEmpty()) {
                AlertDialog().dialog(this,"Please enter first name")
            } else if(binding.edtLastName.text.isEmpty()) {
                AlertDialog().dialog(this,"Please enter last name")
            }else if(binding.edtCompName.text.isEmpty()) {
                AlertDialog().dialog(this,"Please enter company name")
            }else if(binding.edtWatNo.text.isEmpty()) {
                AlertDialog().dialog(this,"Please enter VAT number")
            }/*else if(Validator.isValidEmail(binding.edtEmail.text.toString(),false)) {
                AlertDialog().dialog(this,"Email id not valid")
            }*/else if(binding.edtMobNo.text.isEmpty() || binding.edtMobNo.text.length < 10) {
                AlertDialog().dialog(this,"Mobile id not valid")
            }else{
                dialogOTPtoLogin()
            }

         // dialogOTPtoLogin()
        }


        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.arrTitle,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spTitle.adapter = adapter
        binding.spTitle.onItemSelectedListener = this

    }


    override fun onNothingSelected(parent: AdapterView<*>?) {
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val text: String = parent?.getItemAtPosition(position).toString()

    }


    fun dialogViewProduct(pName: String) {
        val dialog = Dialog(this, R.style.NewDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dailog_alert)
        dialog.getWindow()
            ?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val imv_cross: ImageView = dialog.findViewById(R.id.imv_cross)
        imv_cross.setOnClickListener() {
            dialog.dismiss()
        }

        dialog.show()
    }

    fun dialogOTPtoLogin() {
        val dialog = Dialog(this, R.style.NewDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)

        dialog.setContentView(R.layout.dialog_login_to_otp)
        dialog.getWindow()
            ?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val imv_cross: ImageView = dialog.findViewById(R.id.imv_cross)

        val edtOTP: EditText = dialog.findViewById(R.id.edtOTP)
        val txtResentOTP: TextView = dialog.findViewById(R.id.txtResentOTP)
        val btnSubmitToLogin: Button = dialog.findViewById(R.id.btnSubmitToLogin)


        btnSubmitToLogin.setOnClickListener() {

            if(edtOTP.text.isEmpty()) {
                AlertDialog().dialog(
                    this,
                    "Please enter OTP"
                )
            }else{
                dialog(
                    this,
                    "Request sent successfully, will get a activation email once activated."
                )
            }
            dialog.dismiss()
        }

        imv_cross.setOnClickListener() {
            dialog.dismiss()
        }
        dialog.show()
    }


    fun dialog(activity: Activity, message:String){
        val dialog = Dialog(activity, R.style.NewDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dailog_alert)
        dialog.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        val txt_ok: TextView = dialog.findViewById(R.id.txt_ok)
        val txtMessage: TextView = dialog.findViewById(R.id.txtMessage)
        txtMessage.text = message

        txt_ok.setOnClickListener(){
            dialog.dismiss()
            activity.finish()
        }
        dialog.show()
    }
}