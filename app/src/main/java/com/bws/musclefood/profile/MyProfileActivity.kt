package com.bws.musclefood.profile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bws.musclefood.R
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration
import kotlinx.android.synthetic.main.a_profile_new.*
import kotlinx.android.synthetic.main.tool_bar_address.*

class MyProfileActivity:AppCompatActivity() {

    var arrProfile = ArrayList<ProfileModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_profile_new)
        supportActionBar?.hide()
        txtTxtHeader.text = "Profile"
        imvSaveaddress.visibility = View.GONE

        recyProfile.layoutManager = LinearLayoutManager(this)

        val dividerDrawable =
            ContextCompat.getDrawable(applicationContext, R.drawable.line_divider)
        recyProfile.addItemDecoration(DividerItemDecoration(dividerDrawable))

        arrProfile.add(ProfileModel("Name"))
        arrProfile.add(ProfileModel("Delivery Address"))
        arrProfile.add(ProfileModel("Orders"))
        arrProfile.add(ProfileModel("Payment Details"))
        arrProfile.add(ProfileModel("Contact Preference"))
        arrProfile.add(ProfileModel("Help"))


        val adapter = ProfileAdapter(arrProfile)
        recyProfile.adapter = adapter
        adapter.notifyDataSetChanged()


        imvBack.setOnClickListener(){
            finish()
        }
    }
}