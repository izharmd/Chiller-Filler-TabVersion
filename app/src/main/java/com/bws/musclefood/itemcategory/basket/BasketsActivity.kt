package com.bws.musclefood.itemcategory.basket

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bws.musclefood.R
import kotlinx.android.synthetic.main.activity_basket.*
import kotlinx.android.synthetic.main.tool_bar_address.*

class BasketsActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)
        supportActionBar?.hide()
        txtTxtHeader.text = "Saved Baskets"
        imvSaveaddress.visibility = View.GONE
        txtSavedBasket.visibility = View.VISIBLE

        imvBack.setOnClickListener(){
            finish()
        }

       /* recyBasketList.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<BasketModel>()
        data.add(BasketModel("10/11/2021","22846748","Mr.Johnny ","£105.40","Shipped"))
        data.add(BasketModel("15/12/2021","74620748","Mr.Charlie ","£302.50","Shipped"))
        data.add(BasketModel("10/11/2021","89048672","Mr. Martin","£50.20","Shipped"))

        val adapter = BasketAdapter(data)
        recyBasketList.adapter = adapter
        adapter.notifyDataSetChanged()*/
    }
}