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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bws.musclefood.InsertUpdateProduct
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.factory.FactoryProvider
import com.bws.musclefood.itemcategory.productlist.ProductListActivity
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.signup.SignUpActivity
import com.bws.musclefood.utils.*
import com.bws.musclefood.viewmodels.LoginViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*


class LoginActivity : AppCompatActivity() {

    lateinit var loginViewModel: LoginViewModel
    lateinit var preferenceConnector: PreferenceConnector
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

       // InsertUpdateProduct().callApi(this)


        val content = SpannableString("Guest User")
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        txtGuestUser.text = content


        preferenceConnector = PreferenceConnector(this)



        btnSignUp.setOnClickListener() {
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
        }

        btnLogin.setOnClickListener() {


            // val email = Validator.isValidEmail("nameg@mail.com",false)
            // val email = Validator.isValidEmail("nameg@mail.com",false)
            val pass = Validator.isValidPassword(edtPassword.text.toString(), true)
            val email = Validator.isValidEmail(edtEmailId.text.toString(), true)

             if(edtEmailId.text.isEmpty()){
                AlertDialog().dialog(this,"Please enter email id")
            }else if(!email){
               AlertDialog().dialog(this,"Invalid email id")
            }else if(!pass){
                AlertDialog().dialog(this,"Password should be minimum 8 characters, a-Z,0-9 and one special character")
            }else{
               val loginRepository = Repository()
               val loginFactory = FactoryProvider(loginRepository, this)
               loginViewModel =
                   ViewModelProvider(this, loginFactory).get(LoginViewModel::class.java)

               callLoginAPI(edtEmailId.text.toString(),edtPassword.text.toString())

            }

           /* val loginRepository = Repository()
            val loginFactory = FactoryProvider(loginRepository, this)
            loginViewModel =
                ViewModelProvider(this, loginFactory).get(LoginViewModel::class.java)
            val dt = loginViewModel.loginDta("Hi how r u")
            println("DTA==="+dt)

            callLoginAPI(edtEmailId.text.toString(),edtPassword.text.toString())*/
        }

        llForgotPassword.setOnClickListener() {
            dialogForgotPassword()
        }
    }

    private fun callLoginAPI(email:String, password:String){

        Constant.sessionID = Random().nextFloat().toString()

       // val loginPram = RequestBodies.LoginBody("mk9026125@gmail.com", "Test321@","Android","12345")
        val loginPram = RequestBodies.LoginBody(email, password,"Android","122345")

        val jso = Gson()

        println("LOGIN JSON == "+Gson().toJson(loginPram))
        val loadingDialog = LoadingDialog.progressDialog(this)
        loginViewModel.loginUser(loginPram)
        loginViewModel.loginResult.observe(this, Observer {
            when (it) {
                is Resources.NoInternet -> {
                    loadingDialog.hide()
                    AlertDialog().dialog(this,it.noInternetMessage.toString())
                    this.viewModelStore.clear()
                }
                is Resources.Loading -> {
                    loadingDialog.show()
                }
                is Resources.Success -> {
                    loadingDialog.dismiss()
                    val statusCode = it.data?.StatusCode
                    if (statusCode.equals("200")){
                        preferenceConnector.saveString("USER_ID",it.data?.UserId.toString())
                        preferenceConnector.saveString("TITLE",it.data?.Title.toString())
                        preferenceConnector.saveString("FIRST_NAME",it.data?.FirstName.toString())
                        preferenceConnector.saveString("LAST_NAME",it.data?.LastName.toString())
                        preferenceConnector.saveString("FULL_NAME",it.data?.FullName.toString())
                        preferenceConnector.saveString("COMPANY_NAME",it.data?.CompanyName.toString())
                        preferenceConnector.saveString("VAT_NO",it.data?.VATNo.toString())
                        preferenceConnector.saveString("EMAIL_ID",it.data?.EmailID.toString())
                        preferenceConnector.saveString("PROFILE_URL",it.data?.ProfileImage.toString())
                        preferenceConnector.saveString("MOBILE_NO",it.data?.MobileNo.toString())
                        dialogOTPtoLogin()
                        this.viewModelStore.clear()
                    }else{
                        Toast.makeText(this,it.data?.StatusMSG,Toast.LENGTH_LONG).show()
                        this.viewModelStore.clear()
                    }
                    //Toast.makeText(this,it.data?.StatusMSG,Toast.LENGTH_LONG).show()
                }
                is Resources.Error -> {
                    loadingDialog.dismiss()
                    AlertDialog().dialog(this,it.errorMessage.toString())
                //    Toast.makeText(this,it.data?.StatusMSG,Toast.LENGTH_LONG).show()
                    this.viewModelStore.clear()
                }
            }
        })

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
            ?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
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