package com.bws.musclefood

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.bws.musclefood.login.LoginActivity


class SplashActivity:AppCompatActivity() {
    private val SPLASH_DISPLAY_LENGTH = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        val user = User("1233")


        user.uniqueIdentifier = "name==="
        user.age = "32"

        Handler().postDelayed(Runnable {
            val mainIntent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(mainIntent)
            finish()
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }
}