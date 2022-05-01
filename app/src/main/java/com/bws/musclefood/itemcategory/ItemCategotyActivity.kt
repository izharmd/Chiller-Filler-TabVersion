package com.bws.musclefood.itemcategory

import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bws.musclefood.R
import kotlinx.android.synthetic.main.activity_item_categoty.*

class ItemCategotyActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_categoty)
        supportActionBar?.hide()



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
        arrayListDairy.add("Amul Butter")
        arrayListDairy.add("Kissan Mixed Fruit Jam")
        arrayListDairy.add("Mother Dairy Toned Milk")
        arrayListDairy.add("UPF Healthy Brown Eggs")
        arrayListDairy.add("OVO Top Pick White Eggs")


        val arrayListGrosries = ArrayList<String>()
        arrayListGrosries.add("Grocery Split Yellow Moong Dal")
        arrayListGrosries.add("Grocery Small Chatti Malka Masoor")
        arrayListGrosries.add("Basic Moong Dal")
        arrayListGrosries.add("Dhara Kachi Ghani Mustard Oil")
        arrayListGrosries.add("Engine Brand Kachi Ghani Mustard Oil")



        recyCategory.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<ItemCategoryModel>()

        data.add(ItemCategoryModel(R.drawable.meat,"Meat",arrayListFish))
        data.add(ItemCategoryModel(R.drawable.fruts,"Veg & Vegan",arrayListFriudts))
        data.add(ItemCategoryModel(R.drawable.fruts,"Eggs & Diary",arrayListDairy))
        data.add(ItemCategoryModel(R.drawable.gro,"Snacks",arrayListGrosries))
        data.add(ItemCategoryModel(R.drawable.fruts,"DRINKS",arrayListFish))

        val adapter = ItemCategoryAdapter(data)
        recyCategory.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}