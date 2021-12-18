package com.bws.musclefood.itemcategory.productlist

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant.Companion.pos
import com.bws.musclefood.itemcategory.ItemCategoryAdapter
import com.bws.musclefood.itemcategory.ItemCategoryModel
import com.bws.musclefood.itemcategory.cartlist.CartListActivity
import com.bws.musclefood.itemcategory.productlist.categorytop.TopCategoryAdapter
import com.bws.musclefood.itemcategory.productlist.categorytop.TopCategoryModel
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_item_categoty.*
import kotlinx.android.synthetic.main.activity_productlist.*
import kotlinx.android.synthetic.main.tool_bar_search_view.*

class ProductListActivity:AppCompatActivity() {

    val dataMainCtegory = ArrayList<ItemCategoryModel>()


    var dataTopCategory: ArrayList<TopCategoryModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productlist)
        supportActionBar?.hide()


        mainCategory()


        imvSearch.setOnClickListener(){
            //txtSearchProduct.visibility = View.GONE
            searchView.visibility = View.VISIBLE
        }

        imvCart.setOnClickListener(){
           startActivity(Intent(this@ProductListActivity,CartListActivity::class.java))
        }
    }

    fun yourDesiredMethod(){
        recyTopCategory.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
         dataTopCategory = ArrayList<TopCategoryModel>()

        val arrChickenItem = ArrayList<String>()
        arrChickenItem.add("Supergreen Stuffed Chicken Breasts")
        arrChickenItem.add("Pizza Stuffed Chicken Breasts")
        arrChickenItem.add("Chicken Breast Mini Fillets")
        arrChickenItem.add("Extra Lean Diced Chicken Breast")

        val arrChickenImg = ArrayList<Int>()
        arrChickenImg.add(R.drawable.premium_cheken)
        arrChickenImg.add(R.drawable.chiken2)
        arrChickenImg.add(R.drawable.chiken3)
        arrChickenImg.add(R.drawable.cheken1)


        if(pos == 0){
            val arrItemAll = ArrayList<String>()
            arrItemAll.add("Supergreen Stuffed Chicken Breasts")
            arrItemAll.add("Heritage Fillet Steaks")
            arrItemAll.add("Unsmoked Streaky Bacon Rashers")
            arrItemAll.add("Turkey")

            val arrAllImg = ArrayList<Int>()
            arrAllImg.add(R.drawable.cheken1)
            arrAllImg.add(R.drawable.beef1)
            arrAllImg.add(R.drawable.pork1)
            arrAllImg.add(R.drawable.pork1)

            val arrChickenItem = ArrayList<String>()
            arrChickenItem.add("Supergreen Stuffed Chicken Breasts")
            arrChickenItem.add("Pizza Stuffed Chicken Breasts")
            arrChickenItem.add("Chicken Breast Mini Fillets")
            arrChickenItem.add("Extra Lean Diced Chicken Breast")

            val arrChickenImg = ArrayList<Int>()
            arrChickenImg.add(R.drawable.premium_cheken)
            arrChickenImg.add(R.drawable.chiken2)
            arrChickenImg.add(R.drawable.chiken3)
            arrChickenImg.add(R.drawable.cheken1)


            val arrBeefItem = ArrayList<String>()
            arrBeefItem.add("Heritage Fillet Steaks")
            arrBeefItem.add("Heritage Beef Fillet Steak")
            arrBeefItem.add("The Heritage Range™ Sirloin Steaks")
            arrBeefItem.add("Free Range Big Daddy Beef Rump Steak")

           val arrBeefImage = ArrayList<Int>()
            arrBeefImage.add(R.drawable.beef1)
            arrBeefImage.add(R.drawable.beef2)
            arrBeefImage.add(R.drawable.beef3)
            arrBeefImage.add(R.drawable.beef4)

            val arrPorkItem = ArrayList<String>()
            arrPorkItem.add("Unsmoked Streaky Bacon Rashers")
            arrPorkItem.add("Onion & Bacon Stuffed Turkey Parcels")
            arrPorkItem.add("Low Fat Unsmoked Bacon Medallions")
            arrPorkItem.add("Pork Loin Steaks")

            val arrPorkImg = ArrayList<Int>()
            arrPorkImg.add(R.drawable.pork1)
            arrPorkImg.add(R.drawable.pork2)
            arrPorkImg.add(R.drawable.pork3)
            arrPorkImg.add(R.drawable.pork4)

            dataTopCategory!!.add(TopCategoryModel(R.drawable.premium_cheken,"View All",arrItemAll,arrAllImg))
            dataTopCategory!!.add(TopCategoryModel(R.drawable.premium_cheken,dataMainCtegory.get(pos).arrSubCategory?.get(0).toString(),arrChickenItem,arrChickenImg))
            dataTopCategory!!.add(TopCategoryModel(R.drawable.beef,dataMainCtegory.get(pos).arrSubCategory?.get(1).toString(),arrBeefItem,arrBeefImage))
            dataTopCategory!!.add(TopCategoryModel(R.drawable.pork,dataMainCtegory.get(pos).arrSubCategory?.get(2).toString(),arrPorkItem,arrPorkImg))
            dataTopCategory!!.add(TopCategoryModel(R.drawable.turkey,dataMainCtegory.get(pos).arrSubCategory?.get(3).toString(),arrChickenItem,arrChickenImg))
        }else if(pos == 1){
            val arrItemAll = ArrayList<String>()
            arrItemAll.add("Supergreen Stuffed Chicken Breasts")
            arrItemAll.add("Heritage Fillet Steaks")
            arrItemAll.add("Unsmoked Streaky Bacon Rashers")
            arrItemAll.add("Turkey")

            val arrAllImg = ArrayList<Int>()
            arrAllImg.add(R.drawable.cheken1)
            arrAllImg.add(R.drawable.beef1)
            arrAllImg.add(R.drawable.pork1)
            arrAllImg.add(R.drawable.pork1)

            val arrChickenItem = ArrayList<String>()
            arrChickenItem.add("Supergreen Stuffed Chicken Breasts")
            arrChickenItem.add("Pizza Stuffed Chicken Breasts")
            arrChickenItem.add("Chicken Breast Mini Fillets")
            arrChickenItem.add("Extra Lean Diced Chicken Breast")

            val arrChickenImg = ArrayList<Int>()
            arrChickenImg.add(R.drawable.premium_cheken)
            arrChickenImg.add(R.drawable.chiken2)
            arrChickenImg.add(R.drawable.chiken3)
            arrChickenImg.add(R.drawable.cheken1)


            val arrBeefItem = ArrayList<String>()
            arrBeefItem.add("Heritage Fillet Steaks")
            arrBeefItem.add("Heritage Beef Fillet Steak")
            arrBeefItem.add("The Heritage Range™ Sirloin Steaks")
            arrBeefItem.add("Free Range Big Daddy Beef Rump Steak")

            val arrBeefImage = ArrayList<Int>()
            arrBeefImage.add(R.drawable.beef1)
            arrBeefImage.add(R.drawable.beef2)
            arrBeefImage.add(R.drawable.beef3)
            arrBeefImage.add(R.drawable.beef4)

            val arrPorkItem = ArrayList<String>()
            arrPorkItem.add("Unsmoked Streaky Bacon Rashers")
            arrPorkItem.add("Onion & Bacon Stuffed Turkey Parcels")
            arrPorkItem.add("Low Fat Unsmoked Bacon Medallions")
            arrPorkItem.add("Pork Loin Steaks")

            val arrPorkImg = ArrayList<Int>()
            arrPorkImg.add(R.drawable.pork1)
            arrPorkImg.add(R.drawable.pork2)
            arrPorkImg.add(R.drawable.pork3)
            arrPorkImg.add(R.drawable.pork4)

            dataTopCategory!!.add(TopCategoryModel(R.drawable.premium_cheken,"View All",arrItemAll,arrAllImg))
            dataTopCategory!!.add(TopCategoryModel(R.drawable.premium_cheken,dataMainCtegory.get(pos).arrSubCategory?.get(0).toString(),arrChickenItem,arrChickenImg))
            dataTopCategory!!.add(TopCategoryModel(R.drawable.beef,dataMainCtegory.get(pos).arrSubCategory?.get(1).toString(),arrBeefItem,arrBeefImage))
            dataTopCategory!!.add(TopCategoryModel(R.drawable.pork,dataMainCtegory.get(pos).arrSubCategory?.get(2).toString(),arrPorkItem,arrPorkImg))
            dataTopCategory!!.add(TopCategoryModel(R.drawable.turkey,dataMainCtegory.get(pos).arrSubCategory?.get(3).toString(),arrChickenItem,arrChickenImg))
        }else if(pos == 2){

            val arrItemAll = ArrayList<String>()
            arrItemAll.add("Liquid Egg Whites Cartons")
            arrItemAll.add("Liquid Egg Whites Cartons")
            arrItemAll.add("Peeled Boiled Eggs")
            arrItemAll.add("Everest HiPro Pudding - Salted Caramel")

            val arrAllImg = ArrayList<Int>()
            arrAllImg.add(R.drawable.egg1)
            arrAllImg.add(R.drawable.egg2)
            arrAllImg.add(R.drawable.egg3)
            arrAllImg.add(R.drawable.egg4)


            val arrItemLiquid = ArrayList<String>()
            arrItemLiquid.add("Liquid Egg Whites Cartons")
            arrItemLiquid.add("Liquid Egg Whites Cartons")
            arrItemLiquid.add("Peeled Boiled Eggs")
            arrItemLiquid.add("Scrambled Eggs")

            val arrLiquidImg = ArrayList<Int>()
            arrLiquidImg.add(R.drawable.egg1)
            arrLiquidImg.add(R.drawable.egg2)
            arrLiquidImg.add(R.drawable.egg3)
            arrLiquidImg.add(R.drawable.egg5)

            dataTopCategory!!.add(TopCategoryModel(R.drawable.premium_cheken,"View All",arrItemAll,arrAllImg))
            dataTopCategory!!.add(TopCategoryModel(R.drawable.premium_cheken,dataMainCtegory.get(pos).arrSubCategory?.get(0).toString(),arrItemLiquid,arrLiquidImg))


        }

        val adapterTop = TopCategoryAdapter(dataTopCategory!!)
        recyTopCategory.adapter = adapterTop
        adapterTop.notifyDataSetChanged()

        recyProductList.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<ProductListModel>()
        if(pos == 0){
            data.add(ProductListModel(R.drawable.premium_cheken,dataTopCategory!!.get(pos).arrChicken.get(0),"£3.55","£10.50","4 hrs","FRESHO","5 kg","£ 13","4.4","1003 Ratings","YES"))
            data.add(ProductListModel(R.drawable.cheken1,dataTopCategory!!.get(pos).arrChicken.get(1),"£3.55","£12.00","7 hrs","Blue Flame","5 kg","£ 5","4.1","12203 Ratings","YES"))
            data.add(ProductListModel(R.drawable.chiken2,dataTopCategory!!.get(pos).arrChicken.get(2),"£120.00","£230.00","10 hrs","Bon Appetit","5 kg","£ 20 ","4.4","3403 Ratings","NO"))
            data.add(ProductListModel(R.drawable.chiken3,dataTopCategory!!.get(pos).arrChicken.get(3),"£90.00","£140.00","4 hrs","Fresho","5 kg","£ 15","2.4","1433 Ratings","YES"))
        }/*else{
            data.add(ProductListModel(R.drawable.potato,"Potato","£50.00","£150.00","5 hrs","Fresho","5 kg","£ 10","5.0","17603 Ratings","YES"))
            data.add(ProductListModel(R.drawable.sweet_corn,"Sweet Corn","£40.00","£120.00","2 hrs","CP Easy Snack","2 kg","£ 30","4.1","106603 Ratings","NO"))
            data.add(ProductListModel(R.drawable.baby_potato,"Baby potato","£110.50","£250.00","3 hrs","La Carne","2 kg","£ 40","1.0","3 Ratings","YES"))
            data.add(ProductListModel(R.drawable.banana_nendran,"Banana - Nendran","£150.00","£50.00","5 hrs","Fresho","10 kg","£ 15","4.4","100563 Ratings","NO"))
            data.add(ProductListModel(R.drawable.mosambi_economy,"Mosambi - Economy","£150.00","£50.00","5 hrs","Fresho","3 kg","£ 10","4.3","106503 Ratings","NO"))

        }*/
        val dividerDrawable =
            ContextCompat.getDrawable(applicationContext, R.drawable.line_divider)
        recyProductList.addItemDecoration(DividerItemDecoration(dividerDrawable))

        val adapter = ProductListAdapter(data)
        recyProductList.adapter = adapter
        adapter.notifyDataSetChanged()
    }


    fun subMenuCategory(){

        recyProductList.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<ProductListModel>()

        if(pos == 0){

            data.add(ProductListModel(dataTopCategory!!.get(pos).arrChickenImg.get(0),dataTopCategory!!.get(pos).arrChicken.get(0),"£3.55","£10.50","4 hrs","FRESHO","5 kg","£ 13","4.4","1003 Ratings","YES"))
            data.add(ProductListModel(dataTopCategory!!.get(pos).arrChickenImg.get(1),dataTopCategory!!.get(pos).arrChicken.get(1),"£3.55","£12.00","7 hrs","Blue Flame","5 kg","£ 5","4.1","12203 Ratings","YES"))
            data.add(ProductListModel(dataTopCategory!!.get(pos).arrChickenImg.get(2),dataTopCategory!!.get(pos).arrChicken.get(2),"£120.00","£230.00","10 hrs","Bon Appetit","5 kg","£ 20 ","4.4","3403 Ratings","NO"))
            data.add(ProductListModel(dataTopCategory!!.get(pos).arrChickenImg.get(3),dataTopCategory!!.get(pos).arrChicken.get(3),"£90.00","£140.00","4 hrs","Fresho","5 kg","£ 15","2.4","1433 Ratings","YES"))
        }


        if(pos == 1){
            val ss = dataTopCategory!!.get(pos).arrChicken.get(0)
            val img = dataTopCategory!!.get(pos).arrChickenImg.get(0)
            data.add(ProductListModel(dataTopCategory!!.get(pos).arrChickenImg.get(0),dataTopCategory!!.get(pos).arrChicken.get(0),"£3.55","£10.50","4 hrs","FRESHO","5 kg","£ 13","4.4","1003 Ratings","YES"))
            data.add(ProductListModel(dataTopCategory!!.get(pos).arrChickenImg.get(1),dataTopCategory!!.get(pos).arrChicken.get(1),"£3.55","£12.00","7 hrs","Blue Flame","5 kg","£ 5","4.1","12203 Ratings","YES"))
            data.add(ProductListModel(dataTopCategory!!.get(pos).arrChickenImg.get(2),dataTopCategory!!.get(pos).arrChicken.get(2),"£120.00","£230.00","10 hrs","Bon Appetit","5 kg","£ 20 ","4.4","3403 Ratings","NO"))
            data.add(ProductListModel(dataTopCategory!!.get(pos).arrChickenImg.get(3),dataTopCategory!!.get(pos).arrChicken.get(3),"£90.00","£140.00","4 hrs","Fresho","5 kg","£ 15","2.4","1433 Ratings","YES"))
        }

        if(pos == 2){
            val ss = dataTopCategory!!.get(pos).arrChicken.get(0)
            val img = dataTopCategory!!.get(pos).arrChickenImg.get(0)
            data.add(ProductListModel(dataTopCategory!!.get(pos).arrChickenImg.get(0),dataTopCategory!!.get(pos).arrChicken.get(0),"£3.55","£10.50","4 hrs","FRESHO","5 kg","£ 13","4.4","1003 Ratings","YES"))
            data.add(ProductListModel(dataTopCategory!!.get(pos).arrChickenImg.get(1),dataTopCategory!!.get(pos).arrChicken.get(1),"£3.55","£12.00","7 hrs","Blue Flame","5 kg","£ 5","4.1","12203 Ratings","YES"))
            data.add(ProductListModel(dataTopCategory!!.get(pos).arrChickenImg.get(2),dataTopCategory!!.get(pos).arrChicken.get(2),"£120.00","£230.00","10 hrs","Bon Appetit","5 kg","£ 20 ","4.4","3403 Ratings","NO"))
            data.add(ProductListModel(dataTopCategory!!.get(pos).arrChickenImg.get(3),dataTopCategory!!.get(pos).arrChicken.get(3),"£90.00","£140.00","4 hrs","Fresho","5 kg","£ 15","2.4","1433 Ratings","YES"))
        }

        if(pos == 3){
            val ss = dataTopCategory!!.get(pos).arrChicken.get(0)
            val img = dataTopCategory!!.get(pos).arrChickenImg.get(0)
            data.add(ProductListModel(dataTopCategory!!.get(pos).arrChickenImg.get(0),dataTopCategory!!.get(pos).arrChicken.get(0),"£3.55","£10.50","4 hrs","FRESHO","5 kg","£ 13","4.4","1003 Ratings","YES"))
            data.add(ProductListModel(dataTopCategory!!.get(pos).arrChickenImg.get(1),dataTopCategory!!.get(pos).arrChicken.get(1),"£3.55","£12.00","7 hrs","Blue Flame","5 kg","£ 5","4.1","12203 Ratings","YES"))
            data.add(ProductListModel(dataTopCategory!!.get(pos).arrChickenImg.get(2),dataTopCategory!!.get(pos).arrChicken.get(2),"£120.00","£230.00","10 hrs","Bon Appetit","5 kg","£ 20 ","4.4","3403 Ratings","NO"))
            data.add(ProductListModel(dataTopCategory!!.get(pos).arrChickenImg.get(3),dataTopCategory!!.get(pos).arrChicken.get(3),"£90.00","£140.00","4 hrs","Fresho","5 kg","£ 15","2.4","1433 Ratings","YES"))
        }

        val dividerDrawable =
            ContextCompat.getDrawable(applicationContext, R.drawable.line_divider)
        recyProductList.addItemDecoration(DividerItemDecoration(dividerDrawable))

        val adapter = ProductListAdapter(data)
        recyProductList.adapter = adapter
        adapter.notifyDataSetChanged()

    }

     fun mainCategory(){
        val arrayListFish = ArrayList<String>()
        arrayListFish.add("Chicken")
        arrayListFish.add("Beef")
        arrayListFish.add("Pork")
        arrayListFish.add("Turkey")



        val arrayListFriudts = ArrayList<String>()
        arrayListFriudts.add("Fresh Vegetables")
        arrayListFriudts.add("Herbs & Seasonings")
        arrayListFriudts.add("Fresh Fruits")
        arrayListFriudts.add("Cuts & Sprouts")


        val arrayListDairy = ArrayList<String>()
       // arrayListDairy.add("View All")
        arrayListDairy.add("Liquid Egg Whites")
        /*arrayListDairy.add("Kissan Mixed Fruit Jam")
        arrayListDairy.add("Mother Dairy Toned Milk")
        arrayListDairy.add("UPF Healthy Brown Eggs")
        arrayListDairy.add("OVO Top Pick White Eggs")*/


        val arrayListGrosries = ArrayList<String>()
        arrayListGrosries.add("Grocery Split Yellow Moong Dal")
        arrayListGrosries.add("Grocery Small Chatti Malka Masoor")
        arrayListGrosries.add("Basic Moong Dal")
        arrayListGrosries.add("Dhara Kachi Ghani Mustard Oil")
        arrayListGrosries.add("Engine Brand Kachi Ghani Mustard Oil")



        recyMainCategory.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)


         dataMainCtegory.add(ItemCategoryModel(R.drawable.meat,"Meat",arrayListFish))
         dataMainCtegory.add(ItemCategoryModel(R.drawable.fruts,"Veg & Vegan",arrayListFriudts))
         dataMainCtegory.add(ItemCategoryModel(R.drawable.egg,"Eggs & Diary",arrayListDairy))
         dataMainCtegory.add(ItemCategoryModel(R.drawable.gro,"Snacks",arrayListGrosries))
         dataMainCtegory.add(ItemCategoryModel(R.drawable.drink,"DRINKS",arrayListFish))

        val adapter = ItemCategoryAdapter(dataMainCtegory)
        recyMainCategory.adapter = adapter
        adapter.notifyDataSetChanged()

    }

}