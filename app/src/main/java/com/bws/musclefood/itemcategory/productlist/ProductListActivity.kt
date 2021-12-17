package com.bws.musclefood.itemcategory.productlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bws.musclefood.R
import com.bws.musclefood.itemcategory.cartlist.CartListActivity
import com.bws.musclefood.itemcategory.productlist.categorytop.TopCategoryAdapter
import com.bws.musclefood.itemcategory.productlist.categorytop.TopCategoryModel
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_productlist.*
import kotlinx.android.synthetic.main.tool_bar_search_view.*

class ProductListActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productlist)
        supportActionBar?.hide()

        val name = intent.getStringArrayListExtra("Data")
        val pos = intent.getIntExtra("POSITION",0)

        txtCategory.text = intent.getStringExtra("CATEGORY")

        recyTopCategory.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        val dataTopCategory = ArrayList<TopCategoryModel>()

        if(pos == 0){
            dataTopCategory.add(TopCategoryModel(R.drawable.premium_cheken,name?.get(0).toString()))
            dataTopCategory.add(TopCategoryModel(R.drawable.beef,name?.get(1).toString()))
            dataTopCategory.add(TopCategoryModel(R.drawable.pork,name?.get(2).toString()))
            dataTopCategory.add(TopCategoryModel(R.drawable.turkey,name?.get(3).toString()))
        }else if(pos == 1){

        dataTopCategory.add(TopCategoryModel(R.drawable.fruits_and_vagetable,name?.get(0).toString()))
        dataTopCategory.add(TopCategoryModel(R.drawable.herbs_seasonings,name?.get(1).toString()))
        dataTopCategory.add(TopCategoryModel(R.drawable.fresh_fruits,name?.get(2).toString()))
        dataTopCategory.add(TopCategoryModel(R.drawable.cuts_prouts,name?.get(3).toString()))
       // dataTopCategory.add(TopCategoryModel(R.drawable.potato,name?.get(4).toString()))
        }

        val adapterTop = TopCategoryAdapter(dataTopCategory)
        recyTopCategory.adapter = adapterTop
        adapterTop.notifyDataSetChanged()
        
        

        recyProductList.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<ProductListModel>()



        if(pos == 0){

       data.add(ProductListModel(R.drawable.premium_cheken,"Premium Chicken Breast Fillets","£28.00","£41.00","4 hrs","FRESHO","5 kg","£ 13","4.4","1003 Ratings","YES"))
       data.add(ProductListModel(R.drawable.chicken2,"Meatzza Fresh Chicken Mince","£30.00","£130.00","7 hrs","Blue Flame","5 kg","£ 5","4.1","12203 Ratings","YES"))
       data.add(ProductListModel(R.drawable.chicken3,"Meatzza Chicken","£120.00","£230.00","10 hrs","Bon Appetit","5 kg","£ 20 ","4.4","3403 Ratings","NO"))
       data.add(ProductListModel(R.drawable.chicken4,"Prasuma Chicken - Momos","£90.00","£140.00","4 hrs","Fresho","5 kg","£ 15","2.4","1433 Ratings","YES"))
       }else{

            data.add(ProductListModel(R.drawable.potato,"Potato","£50.00","£150.00","5 hrs","Fresho","5 kg","£ 10","5.0","17603 Ratings","YES"))
            data.add(ProductListModel(R.drawable.sweet_corn,"Sweet Corn","£40.00","£120.00","2 hrs","CP Easy Snack","2 kg","£ 30","4.1","106603 Ratings","NO"))
            data.add(ProductListModel(R.drawable.baby_potato,"Baby potato","£110.50","£250.00","3 hrs","La Carne","2 kg","£ 40","1.0","3 Ratings","YES"))
            data.add(ProductListModel(R.drawable.banana_nendran,"Banana - Nendran","£150.00","£50.00","5 hrs","Fresho","10 kg","£ 15","4.4","100563 Ratings","NO"))
            data.add(ProductListModel(R.drawable.mosambi_economy,"Mosambi - Economy","£150.00","£50.00","5 hrs","Fresho","3 kg","£ 10","4.3","106503 Ratings","NO"))

        }
        val dividerDrawable =
            ContextCompat.getDrawable(applicationContext, R.drawable.line_divider)
        recyProductList.addItemDecoration(DividerItemDecoration(dividerDrawable))

        val adapter = ProductListAdapter(data)
        recyProductList.adapter = adapter
        adapter.notifyDataSetChanged()

        imvSearch.setOnClickListener(){
            //txtSearchProduct.visibility = View.GONE
            searchView.visibility = View.VISIBLE
        }

        imvCart.setOnClickListener(){
           startActivity(Intent(this@ProductListActivity,CartListActivity::class.java))
        }
    }

}