package com.bws.musclefood.login

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import com.bws.musclefood.R
import com.bws.musclefood.home.HomeActivity
import com.bws.musclefood.itemcategory.ItemCategotyActivity
import com.bws.musclefood.itemcategory.productlist.ProductListActivity
import com.bws.musclefood.signup.SignUpActivity
import com.bws.musclefood.urils.AlertDialog
import com.bws.musclefood.urils.Validator
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()


        val content = SpannableString("Guest User")
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        txtGuestUser.text = content


        // val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        //  val loginModel = LoginModel("Login Button",)
        //  binding.loginModel = loginModel

        /* val binding: ActivityLoginBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_databinding)

         val user = User("Test", "User")
         binding.user = user*/

        btnSignUp.setOnClickListener() {
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
        }

        btnLogin.setOnClickListener() {


           // val email = Validator.isValidEmail("nameg@mail.com",false)
           // val email = Validator.isValidEmail("nameg@mail.com",false)
            val pass = Validator.isValidPassword(edtPassword.text.toString(),true)
            val email = Validator.isValidEmail(edtEmailId.text.toString(),true)

           /* if(!email){
                AlertDialog().dialog(this,"Invalid Email Id")
            }else if(!pass){
                AlertDialog().dialog(this,"Password should be minimum 8 characters, a-Z,0-9 and one special character")
            }else{
                dialogOTPtoLogin()
            }*/

           startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
        }

        llForgotPassword.setOnClickListener() {
            dialogForgotPassword()
        }
    }

    fun dialogForgotPassword() {
        val dialog = Dialog(this, R.style.NewDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_forgot_password)
        dialog.getWindow()
            ?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val imv_cross: ImageView = dialog.findViewById(R.id.imv_cross)
        val edtEmailFroGotPassword: EditText = dialog.findViewById(R.id.edtEmailFroGotPassword)
        val edtCompName: EditText = dialog.findViewById(R.id.edtCompName)
        val edtVatNo: EditText = dialog.findViewById(R.id.edtVatNo)
        val edtOTP: EditText = dialog.findViewById(R.id.edtOTP)
        val txtResentOTP: TextView = dialog.findViewById(R.id.txtResentOTP)
        val btnGetOTP: Button = dialog.findViewById(R.id.btnGetOTP)
        val btnSubmit: Button = dialog.findViewById(R.id.btnSubmit)

        edtOTP.visibility = View.GONE
        txtResentOTP.visibility = View.GONE
        btnSubmit.visibility = View.GONE


        btnGetOTP.setOnClickListener(){
            edtEmailFroGotPassword.visibility = View.GONE
            edtCompName.visibility = View.GONE
            edtVatNo.visibility = View.GONE
            btnGetOTP.visibility = View.GONE
            edtOTP.visibility = View.VISIBLE
            txtResentOTP.visibility = View.VISIBLE
            btnSubmit.visibility = View.VISIBLE
        }

        btnSubmit.setOnClickListener(){
            AlertDialog().dialog(this,"Reset password link sent to your email id.")
        }


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


        btnSubmitToLogin.setOnClickListener(){
            startActivity(Intent(this@LoginActivity, ProductListActivity::class.java))
            dialog.dismiss()
        }

        imv_cross.setOnClickListener() {
            dialog.dismiss()
        }
        dialog.show()
    }
}