package com.bws.musclefood.signup

import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.style.UnderlineSpan
import androidx.appcompat.app.AppCompatActivity
import com.bws.musclefood.R
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()

        val content = SpannableString("LOGIN")
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        txtAllreadyAccount.text = content


    }
}