package com.bws.musclefood.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import com.bws.musclefood.R
import com.bws.musclefood.itemcategory.ItemCategotyActivity
import com.bws.musclefood.itemcategory.productlist.ProductListActivity
import com.bws.musclefood.signup.SignUpActivity
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

        btnSignUp.setOnClickListener(){
            startActivity(Intent(this@LoginActivity,SignUpActivity::class.java))
        }

        btnLogin.setOnClickListener(){
            startActivity(Intent(this@LoginActivity,ProductListActivity::class.java))
        }
    }
}