package com.bws.musclefood.itemcategory.rating

import android.os.Bundle
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bws.musclefood.R
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_rating.*
import kotlinx.android.synthetic.main.tool_bar_address.*
import com.bws.musclefood.utils.AlertDialog

class RatingActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)
        supportActionBar?.hide()
        imvSaveaddress.visibility = View.GONE
        txtTxtHeader.text = "Product Review"

        val txtProductName: TextView = findViewById(R.id.txtProductName)
        val txtSubmitReview: TextView = findViewById(R.id.txtSubmitReview)
        val txtProductName_1: TextView = findViewById(R.id.txtProductName_1)
        val txtRatingCount: TextView = findViewById(R.id.txtRatingCount)
        val reviewRating: RatingBar = findViewById(R.id.reviewRating)
        val reviewRating_1: RatingBar = findViewById(R.id.reviewRating_1)

        reviewRating.rating = 4f
        reviewRating_1.rating = 4f

        txtProductName.text = "Turkey"
        txtProductName_1.text = "Turkey"
        txtRatingCount.text = "4"


        recvRating.layoutManager = LinearLayoutManager(this)
        val ratingModel = ArrayList<RatingModel>()
        ratingModel.add(RatingModel("Turkey", 4, "Nice Product like it", "4 Mar 2022"))
        ratingModel.add(RatingModel("Heritage Beef Filler Steak - 140g", 4, "Good..", "8 Feb 2022"))
        val dividerDrawable =
            ContextCompat.getDrawable(this!!, R.drawable.line_divider)
        recvRating.addItemDecoration(DividerItemDecoration(dividerDrawable))


        val adapterTop = RatingAdapter(ratingModel)
        recvRating.adapter = adapterTop
        adapterTop.notifyDataSetChanged()


        txtSubmitReview.setOnClickListener(){
            AlertDialog().dialogPaymentSuccessFull(this,"Thank you for your review.","")
        }
    }
}