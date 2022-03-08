package com.bws.musclefood.signup

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.delivery.deliveryoption.viewcartItems.ViewCartItemAdapter
import com.bws.musclefood.itemcategory.productlist.ProductListActivity
import com.bws.musclefood.urils.AlertDialog
import com.bws.musclefood.urils.Validator
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity:AppCompatActivity() {


    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()

        val content = SpannableString("LOGIN")
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        txtAllreadyAccount.text = content

        if(Validator.isValidEmail("name@gmail.com",false)){
            Toast.makeText(this,"TTTTT",Toast.LENGTH_SHORT).show()
        }else {
            Toast.makeText(this,"FFFFF",Toast.LENGTH_SHORT).show()
        }

        btnRegister.setOnClickListener(){
          //  dialogViewProduct("")
            dialogOTPtoLogin()
        }
    }


    fun dialogViewProduct(pName:String) {
        val dialog = Dialog(this,R.style.NewDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dailog_alert)
        dialog.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val imv_cross: ImageView = dialog.findViewById(R.id.imv_cross)
        imv_cross.setOnClickListener(){
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


        btnSubmitToLogin.setOnClickListener(){
            AlertDialog().dialog(this,"Request sent successfully, will get a activation email once activated.")
            dialog.dismiss()
        }

        imv_cross.setOnClickListener() {
            dialog.dismiss()
        }
        dialog.show()
    }
}