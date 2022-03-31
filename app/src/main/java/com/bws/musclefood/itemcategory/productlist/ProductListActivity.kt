package com.bws.musclefood.itemcategory.productlist

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.bws.musclefood.Item
import com.bws.musclefood.NavigationAdapter
import com.bws.musclefood.NavigationAdapter2
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.common.Constant.Companion.pos
import com.bws.musclefood.common.Constant.Companion.totalCartItem
import com.bws.musclefood.favourites.FavouritesActivity
import com.bws.musclefood.itemcategory.ItemCategoryAdapter
import com.bws.musclefood.itemcategory.ItemCategoryModel
import com.bws.musclefood.itemcategory.basket.BasketsActivity
import com.bws.musclefood.itemcategory.cartlist.CartListActivity
import com.bws.musclefood.itemcategory.productlist.categorytop.TopCategoryAdapter
import com.bws.musclefood.itemcategory.productlist.categorytop.TopCategoryModel
import com.bws.musclefood.orders.SearchOrderActivity
import com.bws.musclefood.profile.MyProfileActivity
import com.bws.musclefood.urils.AlertDialog
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration
import com.volcaniccoder.bottomify.BottomifyNavigationView
import com.volcaniccoder.bottomify.OnNavigationItemChangeListener
import kotlinx.android.synthetic.main.a_profile_new.*
import kotlinx.android.synthetic.main.activity_productlist.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.drawer_layout.*
import kotlinx.android.synthetic.main.side_menu_drawer.*
import kotlinx.android.synthetic.main.tool_bar_search_view.*

class ProductListActivity : AppCompatActivity() {

    val dataMainCtegory = ArrayList<ItemCategoryModel>()
    var dataTopCategory: ArrayList<TopCategoryModel>? = null

    var items: ArrayList<Item> = ArrayList()

    lateinit var navigationAdapter: NavigationAdapter
    lateinit var navigationAdapter2: NavigationAdapter2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_drawer)
        supportActionBar?.hide()

        txtLogInSignUp.text = Constant.serviceType


        mainCategory()


        imvSearch.setOnClickListener() {
            //txtSearchProduct.visibility = View.GONE
            searchView.visibility = View.VISIBLE
        }

        imvCart.setOnClickListener() {

            if (txtTotalCartItem.text.equals("0")) {
                AlertDialog().dialog(this,"Please add at lease one product to cart.")
            } else {
                startActivity(Intent(this@ProductListActivity, CartListActivity::class.java))
            }
        }

        bottomify.setOnNavigationItemChangedListener(object : OnNavigationItemChangeListener {
            override fun onNavigationItemChanged(navigationItem: BottomifyNavigationView.NavigationItem) {
                val pos = navigationItem.position
                if (pos == 0) {

                } else if (pos == 1) {
                    startActivity(Intent(this@ProductListActivity, SearchOrderActivity::class.java))
                } else if (pos == 2) {
                    startActivity(Intent(this@ProductListActivity, BasketsActivity::class.java))
                } else if (pos == 3) {
                    // startActivity(Intent(this@ProductListActivity,EnotesActivity::class.java))
                    startActivity(Intent(this@ProductListActivity, FavouritesActivity::class.java))
                } else {
                    startActivity(Intent(this@ProductListActivity, MyProfileActivity::class.java))
                }
            }
        })


        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        navLV.layoutManager = LinearLayoutManager(this)
        navLV_2.layoutManager = LinearLayoutManager(this)

        drawer_layout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
            override fun onDrawerOpened(drawerView: View) {
                /*  items.clear()
                  items.add(Item("Beef"))
                  items.add(Item("Chicken"))
                  items.add(Item("Pork"))
                  items.add(Item("Turkey"))
                  items.add(Item("Fish"))

                  val dividerDrawable =
                      ContextCompat.getDrawable(applicationContext, R.drawable.line_divider)
                  navLV.addItemDecoration(DividerItemDecoration(dividerDrawable))

                  val adapter = NavigationAdapter(items)
                  navLV.adapter = adapter
                  adapter.notifyDataSetChanged()*/
            }

            override fun onDrawerClosed(drawerView: View) {}
            override fun onDrawerStateChanged(newState: Int) {

            }
        })


        burgerMenu.setOnClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }

        txtRetailReady.setOnClickListener {
            // drawer_layout.closeDrawer(Gravity.LEFT)
            txtLogInSignUp.text = "Retail Ready"

            txtRetailReady.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up_15, 0)
            txtFoodService.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.arrow_down_24,
                0
            )
            items.clear()
            items.add(Item("Beef"))
            items.add(Item("Chicken"))
            items.add(Item("Pork"))
            items.add(Item("Turkey"))
            items.add(Item("Fish"))

            navLV.visibility = View.VISIBLE
            navLV_2.visibility = View.GONE
            val dividerDrawable =
                ContextCompat.getDrawable(applicationContext, R.drawable.line_divider)
            navLV.addItemDecoration(DividerItemDecoration(dividerDrawable))

            navigationAdapter = NavigationAdapter(items)
            navLV.adapter = navigationAdapter
            navigationAdapter.notifyDataSetChanged()
        }

        txtFoodService.setOnClickListener {
            //  drawer_layout.closeDrawer(Gravity.LEFT)
            txtLogInSignUp.text = "Food Service"
            txtRetailReady.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.arrow_down_24,
                0
            )
            txtFoodService.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up_15, 0)

            items.clear()
            items.add(Item("Beef"))
            items.add(Item("Chicken"))
            items.add(Item("Pork"))
            items.add(Item("Turkey"))
            items.add(Item("Fish"))

            navLV.visibility = View.GONE
            navLV_2.visibility = View.VISIBLE
            val dividerDrawable =
                ContextCompat.getDrawable(applicationContext, R.drawable.line_divider)
            navLV_2.addItemDecoration(DividerItemDecoration(dividerDrawable))

            navigationAdapter2 = NavigationAdapter2(items)
            navLV_2.adapter = navigationAdapter2
            navigationAdapter2.notifyDataSetChanged()
        }
    }


    fun closeDrawer() {

        drawer_layout.closeDrawer(Gravity.LEFT)
    }

    fun yourDesiredMethod() {
        recyTopCategory.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
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
        arrChickenImg.add(R.drawable.beef_burgers)


        if (pos == 0) {
            val arrItemAll = ArrayList<String>()
            arrItemAll.add("Beef Burgers")
            arrItemAll.add("Beef Mince")
            arrItemAll.add("Beef Meatballs")
            arrItemAll.add("Ribeye Steak ")

            val arrAllImg = ArrayList<Int>()
            arrAllImg.add(R.drawable.beef_burgers)
            arrAllImg.add(R.drawable.beef_mince)
            arrAllImg.add(R.drawable.beef_meatballs)
            arrAllImg.add(R.drawable.ribeye_steaks)

            val arrChickenItem = ArrayList<String>()
            arrChickenItem.add("Premium Chicken Breast Fillets - 5kg")
            arrChickenItem.add("Pizza Stuffed Chicken Breasts - 2 x 175g")
            arrChickenItem.add("Premium Chicken Breast Fillets - 2.5kg")
            arrChickenItem.add("Supergreen Stuffed Chicken Breasts - 2 x 175g")

            val arrChickenImg = ArrayList<Int>()
            arrChickenImg.add(R.drawable.premium_cheken)
            arrChickenImg.add(R.drawable.chiken2)
            arrChickenImg.add(R.drawable.chiken3)
            arrChickenImg.add(R.drawable.cheken1)


            val arrBeefItem = ArrayList<String>()
            arrBeefItem.add("Heritage Fillet Steaks 2 x 140g")
            arrBeefItem.add("Heritage Beef Fillet Steak - 1 x 140g")
            arrBeefItem.add("The Heritage Range™ Sirloin Steaks - 2 x 170g")
            arrBeefItem.add("Free Range Big Daddy Beef Rump Steak - 1 x 908g")

            val arrBeefImage = ArrayList<Int>()
            arrBeefImage.add(R.drawable.beef_mince)
            arrBeefImage.add(R.drawable.beef2)
            arrBeefImage.add(R.drawable.beef3)
            arrBeefImage.add(R.drawable.beef4)

            val arrPorkItem = ArrayList<String>()
            arrPorkItem.add("2 x 350g Unsmoked Streaky Bacon Rashers")
            arrPorkItem.add("4 x 230g Sage, Onion & Bacon Stuffed Turkey Parcels")
            arrPorkItem.add("Low Fat Unsmoked Bacon Medallions - 10 x 35g")
            arrPorkItem.add("Prime Pork Loin Steaks - 4 x 135g")

            val arrPorkImg = ArrayList<Int>()
            arrPorkImg.add(R.drawable.pork1)
            arrPorkImg.add(R.drawable.pork2)
            arrPorkImg.add(R.drawable.pork3)
            arrPorkImg.add(R.drawable.pork4)

            dataTopCategory!!.add(
                TopCategoryModel(
                    R.drawable.premium_cheken,
                    "View All",
                    arrItemAll,
                    arrAllImg
                )
            )
            dataTopCategory!!.add(
                TopCategoryModel(
                    R.drawable.premium_cheken,
                    dataMainCtegory.get(pos).arrSubCategory?.get(0).toString(),
                    arrChickenItem,
                    arrChickenImg
                )
            )
            dataTopCategory!!.add(
                TopCategoryModel(
                    R.drawable.beef,
                    dataMainCtegory.get(pos).arrSubCategory?.get(1).toString(),
                    arrBeefItem,
                    arrBeefImage
                )
            )
            dataTopCategory!!.add(
                TopCategoryModel(
                    R.drawable.pork,
                    dataMainCtegory.get(pos).arrSubCategory?.get(2).toString(),
                    arrPorkItem,
                    arrPorkImg
                )
            )
            dataTopCategory!!.add(
                TopCategoryModel(
                    R.drawable.turkey,
                    dataMainCtegory.get(pos).arrSubCategory?.get(3).toString(),
                    arrChickenItem,
                    arrChickenImg
                )
            )
        } else if (pos == 1) {
            val arrItemAll = ArrayList<String>()
            arrItemAll.add("Meat Free Bean & Potato Pot - 318 kcal")
            arrItemAll.add("The Big Protein Flapjack - Peanut Butter 100g")
            arrItemAll.add("Tilda Microwave Spicy Mexican Rice 250g")
            arrItemAll.add("The Big Protein Flapjack - Mixed Berry 24 x Bars")

            val arrAllImg = ArrayList<Int>()
            arrAllImg.add(R.drawable.veg6)
            arrAllImg.add(R.drawable.veg1)
            arrAllImg.add(R.drawable.veg9)
            arrAllImg.add(R.drawable.veg15)

            val arrPlantBasedDietItem = ArrayList<String>()
            arrPlantBasedDietItem.add("The Big Protein Flapjack - Peanut Butter 100g")
            arrPlantBasedDietItem.add("BRAVE Roasted Chickpeas BBQ 5 x 35g")
            arrPlantBasedDietItem.add("BRAVE Roasted Chickpeas Cookies & Cream 5 x 30g")
            arrPlantBasedDietItem.add("Oatein Nutty Crisp - Caramel 60g")

            val arrPlantBasedDietImg = ArrayList<Int>()
            arrPlantBasedDietImg.add(R.drawable.veg1)
            arrPlantBasedDietImg.add(R.drawable.veg2)
            arrPlantBasedDietImg.add(R.drawable.veg3)
            arrPlantBasedDietImg.add(R.drawable.veg4)


            val arrMeatfreeReadyMealsItem = ArrayList<String>()
            arrMeatfreeReadyMealsItem.add("Levi Roots Veggie Veggie Pot - 431 kcal")
            arrMeatfreeReadyMealsItem.add("Meat Free Bean & Potato Pot - 318 kcal")
            arrMeatfreeReadyMealsItem.add("Curried Noodles with Meat-Free Chicken Pot - 314 kcal")
            arrMeatfreeReadyMealsItem.add("Meat-Free Cheeseburger Pizza")

            val arrMeatfreeReadyMealsImage = ArrayList<Int>()
            arrMeatfreeReadyMealsImage.add(R.drawable.veg5)
            arrMeatfreeReadyMealsImage.add(R.drawable.veg6)
            arrMeatfreeReadyMealsImage.add(R.drawable.veg7)
            arrMeatfreeReadyMealsImage.add(R.drawable.veg8)

            val arrRicePastaNoodlesItem = ArrayList<String>()
            arrRicePastaNoodlesItem.add("Tilda Microwave Spicy Mexican Rice 250g")
            arrRicePastaNoodlesItem.add("Tilda Microwave Hot Jalapeno Wholegrain Rice 250g")
            arrRicePastaNoodlesItem.add("Tilda Microwave Katsu Curry Jasmine Rice 250g")
            arrRicePastaNoodlesItem.add("Fullgreen Low Calorie Riced Cauliflower 6 x 200g")

            val arrRicePastaNoodlesImg = ArrayList<Int>()
            arrRicePastaNoodlesImg.add(R.drawable.veg9)
            arrRicePastaNoodlesImg.add(R.drawable.veg10)
            arrRicePastaNoodlesImg.add(R.drawable.veg11)
            arrRicePastaNoodlesImg.add(R.drawable.veg12)


            val arrMeatFreeSnaksItem = ArrayList<String>()
            arrMeatFreeSnaksItem.add("The Big Protein Flapjack - Mixed Berry 24 x Bars")
            arrMeatFreeSnaksItem.add("The Big Protein Flapjack - Peanut Butter 24 x Bars")
            arrMeatFreeSnaksItem.add("The Big Protein Flapjack - Mixed Berry 24 x Bars")
            arrMeatFreeSnaksItem.add("Pulsin High Fibre Brownie - Double Choc Dream 35g")

            val arrMeatFreeSnaksImg = ArrayList<Int>()
            arrMeatFreeSnaksImg.add(R.drawable.veg13)
            arrMeatFreeSnaksImg.add(R.drawable.veg14)
            arrMeatFreeSnaksImg.add(R.drawable.veg15)
            arrMeatFreeSnaksImg.add(R.drawable.veg16)

            dataTopCategory!!.add(
                TopCategoryModel(
                    R.drawable.premium_cheken,
                    "View All",
                    arrItemAll,
                    arrAllImg
                )
            )

            dataTopCategory!!.add(
                TopCategoryModel(
                    R.drawable.beef,
                    dataMainCtegory.get(pos).arrSubCategory?.get(1).toString(),
                    arrPlantBasedDietItem,
                    arrPlantBasedDietImg
                )
            )
            dataTopCategory!!.add(
                TopCategoryModel(
                    R.drawable.pork,
                    dataMainCtegory.get(pos).arrSubCategory?.get(2).toString(),
                    arrRicePastaNoodlesItem,
                    arrRicePastaNoodlesImg
                )
            )
            dataTopCategory!!.add(
                TopCategoryModel(
                    R.drawable.turkey,
                    dataMainCtegory.get(pos).arrSubCategory?.get(3).toString(),
                    arrMeatFreeSnaksItem,
                    arrMeatFreeSnaksImg
                )
            )
            dataTopCategory!!.add(
                TopCategoryModel(
                    R.drawable.premium_cheken,
                    "",
                    arrChickenItem,
                    arrChickenImg
                )
            )

        } else if (pos == 2) {

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

            dataTopCategory!!.add(
                TopCategoryModel(
                    R.drawable.premium_cheken,
                    "View All",
                    arrItemAll,
                    arrAllImg
                )
            )
            dataTopCategory!!.add(
                TopCategoryModel(
                    R.drawable.premium_cheken,
                    "Liquid Egg Whites",
                    arrItemLiquid,
                    arrLiquidImg
                )
            )
            dataTopCategory!!.add(
                TopCategoryModel(
                    R.drawable.premium_cheken,
                    "",
                    arrItemLiquid,
                    arrLiquidImg
                )
            )
            dataTopCategory!!.add(
                TopCategoryModel(
                    R.drawable.premium_cheken,
                    "",
                    arrItemLiquid,
                    arrLiquidImg
                )
            )
            dataTopCategory!!.add(
                TopCategoryModel(
                    R.drawable.premium_cheken,
                    "",
                    arrItemLiquid,
                    arrLiquidImg
                )
            )

        } else if (pos == 3) {


            val arrAllSnacksItem = ArrayList<String>()
            arrAllSnacksItem.add("Spicy Marinated Chicken on a Stick - 75g")
            arrAllSnacksItem.add("Oatein Cookie - White Choc Celebration 75g")
            arrAllSnacksItem.add("Pulsin Keto Bar - Choc Mint")
            arrAllSnacksItem.add("The Big Protein Flapjack - Peanut Butte")

            val arrAllSnacksImg = ArrayList<Int>()
            arrAllSnacksImg.add(R.drawable.snacks13)
            arrAllSnacksImg.add(R.drawable.snacks8)
            arrAllSnacksImg.add(R.drawable.snackes4)
            arrAllSnacksImg.add(R.drawable.snackes1)

            val arrProtineBiteItem = ArrayList<String>()
            arrProtineBiteItem.add("The Big Protein Flapjack - Peanut Butte")
            arrProtineBiteItem.add("GATO Mini Cookies - Peanut Butter Choc Chip")
            arrProtineBiteItem.add("GATO Cookies n Cream - Peanut Butter")
            arrProtineBiteItem.add("Pulsin Keto Bar - Choc Mint")

            val arrProtineBiteImg = ArrayList<Int>()
            arrProtineBiteImg.add(R.drawable.snackes1)
            arrProtineBiteImg.add(R.drawable.snacks2)
            arrProtineBiteImg.add(R.drawable.snackes3)
            arrProtineBiteImg.add(R.drawable.snackes4)


            val arrChocolateSweetsItem = ArrayList<String>()
            arrChocolateSweetsItem.add("Organic Chocolate Covered Hazelnuts")
            arrChocolateSweetsItem.add("Oatein Cookie - Choc Chip 75g")
            arrChocolateSweetsItem.add("Oatein Cookie - Salted Caramel 75g")
            arrChocolateSweetsItem.add("Oatein Cookie - White Choc Celebration 75g")

            val arrChocolateSweetsImage = ArrayList<Int>()
            arrChocolateSweetsImage.add(R.drawable.snacks5)
            arrChocolateSweetsImage.add(R.drawable.snacks6)
            arrChocolateSweetsImage.add(R.drawable.snacks7)
            arrChocolateSweetsImage.add(R.drawable.snacks8)

            val arrCrispsPopcornItem = ArrayList<String>()
            arrCrispsPopcornItem.add("Eat Real Quinoa Sundried Tomato & Garlic Grab Bag 30g")
            arrCrispsPopcornItem.add("Eat Real Veggie Straws Grab Bag 45g")
            arrCrispsPopcornItem.add("Eat Real Quinoa Sour Cream & Chive Grab Bag 30g")
            arrCrispsPopcornItem.add("Eat Real Quinoa Chilli & Lime Grab Bag 30g")

            val arrCrispsPopcornImg = ArrayList<Int>()
            arrCrispsPopcornImg.add(R.drawable.snacks9)
            arrCrispsPopcornImg.add(R.drawable.snacks10)
            arrCrispsPopcornImg.add(R.drawable.snacks11)
            arrCrispsPopcornImg.add(R.drawable.snacks12)


            val arrMeatySnacksItem = ArrayList<String>()
            arrMeatySnacksItem.add("Spicy Marinated Chicken on a Stick - 75g")
            arrMeatySnacksItem.add("The Curators - Smoky Bacon Pork Puffs 22g")
            arrMeatySnacksItem.add("Jack Links Beef Bar - Original 22.5g")
            arrMeatySnacksItem.add("The Curators - Original Salted Pork Puffs 22g")

            val arrMeatySnacksImg = ArrayList<Int>()
            arrMeatySnacksImg.add(R.drawable.snacks13)
            arrMeatySnacksImg.add(R.drawable.snacks14)
            arrMeatySnacksImg.add(R.drawable.snacks15)
            arrMeatySnacksImg.add(R.drawable.snacks16)



            dataTopCategory!!.add(
                TopCategoryModel(
                    R.drawable.premium_cheken,
                    "View All",
                    arrAllSnacksItem,
                    arrAllSnacksImg
                )
            )
            dataTopCategory!!.add(
                TopCategoryModel(
                    R.drawable.premium_cheken,
                    dataMainCtegory.get(pos).arrSubCategory?.get(0).toString(),
                    arrProtineBiteItem,
                    arrProtineBiteImg
                )
            )
            dataTopCategory!!.add(
                TopCategoryModel(
                    R.drawable.beef,
                    dataMainCtegory.get(pos).arrSubCategory?.get(1).toString(),
                    arrChocolateSweetsItem,
                    arrChocolateSweetsImage
                )
            )
            dataTopCategory!!.add(
                TopCategoryModel(
                    R.drawable.pork,
                    dataMainCtegory.get(pos).arrSubCategory?.get(2).toString(),
                    arrCrispsPopcornItem,
                    arrCrispsPopcornImg
                )
            )
            dataTopCategory!!.add(
                TopCategoryModel(
                    R.drawable.turkey,
                    dataMainCtegory.get(pos).arrSubCategory?.get(3).toString(),
                    arrMeatySnacksItem,
                    arrMeatySnacksImg
                )
            )


        }

        if (pos == 4) {


            val arrAllrinkItem = ArrayList<String>()
            arrAllrinkItem.add("Reign Orange Dreamsicle - 12 x 500ml")
            arrAllrinkItem.add("Monster Ultra Paradise - 500ml")
            arrAllrinkItem.add("Fuel 10k Strawberry Breakfast Drink")
            arrAllrinkItem.add("Melon Mania Reign Zero Calorie BCAA Energy Drink 6 x 500ml")

            val arrAllDrinkImg = ArrayList<Int>()
            arrAllDrinkImg.add(R.drawable.drink1)
            arrAllDrinkImg.add(R.drawable.drink4)
            arrAllDrinkImg.add(R.drawable.drink7)
            arrAllDrinkImg.add(R.drawable.drink10)


            val arrBCCAEnrgyDrinkItem = ArrayList<String>()
            arrBCCAEnrgyDrinkItem.add("Reign Orange Dreamsicle - 12 x 500ml")
            arrBCCAEnrgyDrinkItem.add("Monster Ultra Paradise - 12 x 500ml")
            arrBCCAEnrgyDrinkItem.add("Reign Peach Fizz - 12 x 500ml")
            arrBCCAEnrgyDrinkItem.add("Monster Ultra Paradise - 500ml")

            val arrBCCAEnrgyDrinkImg = ArrayList<Int>()
            arrBCCAEnrgyDrinkImg.add(R.drawable.drink1)
            arrBCCAEnrgyDrinkImg.add(R.drawable.drink2)
            arrBCCAEnrgyDrinkImg.add(R.drawable.drink3)
            arrBCCAEnrgyDrinkImg.add(R.drawable.drink4)


            val arrProtienItem = ArrayList<String>()
            arrProtienItem.add("Fuel 10k Chocolate Breakfast Drink")
            arrProtienItem.add("Fuel 10k Strawberry Breakfast Drink")
            arrProtienItem.add("Fuel 10k Chocolate Breakfast Drink")
            arrProtienItem.add("Fuel 10k Strawberry Breakfast Drink")

            val arrProtienImage = ArrayList<Int>()
            arrProtienImage.add(R.drawable.drink5)
            arrProtienImage.add(R.drawable.drink6)
            arrProtienImage.add(R.drawable.drink5)
            arrProtienImage.add(R.drawable.drink6)

            val arrReignItem = ArrayList<String>()
            arrReignItem.add("Fuel 10k Strawberry Breakfast Drink")
            arrReignItem.add("Lemon Headz Reign Zero Calorie BCAA Energy Drink 6 x 500ml")
            arrReignItem.add("Razzle Berry Reign Zero Calorie BCAA Energy Drink 6 x 500ml")
            arrReignItem.add("Melon Mania Reign Zero Calorie BCAA Energy Drink 6 x 500ml")

            val arrReignImg = ArrayList<Int>()
            arrReignImg.add(R.drawable.drink7)
            arrReignImg.add(R.drawable.drink8)
            arrReignImg.add(R.drawable.drink9)
            arrReignImg.add(R.drawable.drink10)


            val arrMeatySnacksItem = ArrayList<String>()
            arrMeatySnacksItem.add("Spicy Marinated Chicken on a Stick - 75g")
            arrMeatySnacksItem.add("The Curators - Smoky Bacon Pork Puffs 22g")
            arrMeatySnacksItem.add("Jack Links Beef Bar - Original 22.5g")
            arrMeatySnacksItem.add("The Curators - Original Salted Pork Puffs 22g")

            val arrMeatySnacksImg = ArrayList<Int>()
            arrMeatySnacksImg.add(R.drawable.snacks13)
            arrMeatySnacksImg.add(R.drawable.snacks14)
            arrMeatySnacksImg.add(R.drawable.snacks15)
            arrMeatySnacksImg.add(R.drawable.snacks16)



            dataTopCategory!!.add(
                TopCategoryModel(
                    R.drawable.premium_cheken,
                    "View All",
                    arrAllrinkItem,
                    arrAllDrinkImg
                )
            )
            dataTopCategory!!.add(
                TopCategoryModel(
                    R.drawable.premium_cheken,
                    dataMainCtegory.get(pos).arrSubCategory?.get(0).toString(),
                    arrBCCAEnrgyDrinkItem,
                    arrBCCAEnrgyDrinkImg
                )
            )
            dataTopCategory!!.add(
                TopCategoryModel(
                    R.drawable.beef,
                    dataMainCtegory.get(pos).arrSubCategory?.get(1).toString(),
                    arrProtienItem,
                    arrProtienImage
                )
            )
            dataTopCategory!!.add(
                TopCategoryModel(
                    R.drawable.pork,
                    dataMainCtegory.get(pos).arrSubCategory?.get(2).toString(),
                    arrReignItem,
                    arrReignImg
                )
            )
            dataTopCategory!!.add(
                TopCategoryModel(
                    R.drawable.turkey,
                    "",
                    arrMeatySnacksItem,
                    arrMeatySnacksImg
                )
            )


        }

        val adapterTop = TopCategoryAdapter(dataTopCategory!!)
        recyTopCategory.adapter = adapterTop
        adapterTop.notifyDataSetChanged()




        recyProductList.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<ProductListModel>()
        if (pos == 0) {
            data.add(
                ProductListModel(
                    R.drawable.premium_cheken,
                    dataTopCategory!!.get(pos).arrChicken.get(0),
                    "£2.50",
                    "£10.50",
                    "4 hrs",
                    "FRESHO",
                    "5 kg",
                    "£ 13",
                    "4.4",
                    "1003 Ratings",
                    "YES"
                )
            )
            data.add(
                ProductListModel(
                    R.drawable.beef_burgers,
                    dataTopCategory!!.get(pos).arrChicken.get(1),
                    "£2.50",
                    "£12.00",
                    "7 hrs",
                    "Blue Flame",
                    "5 kg",
                    "£ 5",
                    "4.1",
                    "12203 Ratings",
                    "YES"
                )
            )
            data.add(
                ProductListModel(
                    R.drawable.chiken2,
                    dataTopCategory!!.get(pos).arrChicken.get(2),
                    "£120.00",
                    "£230.00",
                    "10 hrs",
                    "Bon Appetit",
                    "5 kg",
                    "£ 20 ",
                    "4.4",
                    "3403 Ratings",
                    "NO"
                )
            )
            data.add(
                ProductListModel(
                    R.drawable.chiken3,
                    dataTopCategory!!.get(pos).arrChicken.get(3),
                    "£90.00",
                    "£140.00",
                    "4 hrs",
                    "Fresho",
                    "5 kg",
                    "£ 15",
                    "2.4",
                    "1433 Ratings",
                    "YES"
                )
            )
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


        subMenuCategory()


    }


    fun subMenuCategory() {

        recyProductList.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<ProductListModel>()

        if (pos == 0) {

            data.add(
                ProductListModel(
                    dataTopCategory!!.get(pos).arrChickenImg.get(0),
                    dataTopCategory!!.get(pos).arrChicken.get(0),
                    "£2.50",
                    "£4.50",
                    "4 hrs",
                    "FRESHO",
                    "5 kg",
                    "£ 2",
                    "4.4",
                    "1003 Ratings",
                    "YES"
                )
            )
            data.add(
                ProductListModel(
                    dataTopCategory!!.get(pos).arrChickenImg.get(1),
                    dataTopCategory!!.get(pos).arrChicken.get(1),
                    "£2.50",
                    "£12.00",
                    "2 hrs",
                    "Blue Flame",
                    "5 kg",
                    "£ 5",
                    "4.1",
                    "12203 Ratings",
                    "YES"
                )
            )
            data.add(
                ProductListModel(
                    dataTopCategory!!.get(pos).arrChickenImg.get(2),
                    dataTopCategory!!.get(pos).arrChicken.get(2),
                    "£2.50",
                    "£20.00",
                    "10 hrs",
                    "Bon Appetit",
                    "5 kg",
                    "£ 5",
                    "4.4",
                    "3403 Ratings",
                    "NO"
                )
            )
            data.add(
                ProductListModel(
                    dataTopCategory!!.get(pos).arrChickenImg.get(3),
                    dataTopCategory!!.get(pos).arrChicken.get(3),
                    "£2.50",
                    "£5.00",
                    "4 hrs",
                    "Fresho",
                    "5 kg",
                    "£ 15",
                    "2.4",
                    "1433 Ratings",
                    "YES"
                )
            )
        }


        if (pos == 1) {
            data.add(
                ProductListModel(
                    dataTopCategory!!.get(pos).arrChickenImg.get(0),
                    dataTopCategory!!.get(pos).arrChicken.get(0),
                    "£1.55",
                    "£10.50",
                    "4 hrs",
                    "FRESHO",
                    "5 kg",
                    "£ 20",
                    "4.1",
                    "1003 Ratings",
                    "YES"
                )
            )
            data.add(
                ProductListModel(
                    dataTopCategory!!.get(pos).arrChickenImg.get(1),
                    dataTopCategory!!.get(pos).arrChicken.get(1),
                    "£6.55",
                    "£8.00",
                    "8 hrs",
                    "Blue Flame",
                    "5 kg",
                    "£ 5",
                    "3.1",
                    "103 Ratings",
                    "YES"
                )
            )
            data.add(
                ProductListModel(
                    dataTopCategory!!.get(pos).arrChickenImg.get(2),
                    dataTopCategory!!.get(pos).arrChicken.get(2),
                    "£5.50",
                    "£10.20",
                    "10 hrs",
                    "Bon Appetit",
                    "5 kg",
                    "£ 5",
                    "5.0",
                    "3403 Ratings",
                    "NO"
                )
            )
            data.add(
                ProductListModel(
                    dataTopCategory!!.get(pos).arrChickenImg.get(3),
                    dataTopCategory!!.get(pos).arrChicken.get(3),
                    "£3.00",
                    "£7.70",
                    "11 hrs",
                    "Fresho",
                    "5 kg",
                    "£ 5",
                    "2.4",
                    "1433 Ratings",
                    "YES"
                )
            )
        }

        if (pos == 2) {
            data.add(
                ProductListModel(
                    dataTopCategory!!.get(pos).arrChickenImg.get(0),
                    dataTopCategory!!.get(pos).arrChicken.get(0),
                    "£8.55",
                    "£10.50",
                    "4 hrs",
                    "FRESHO",
                    "5 kg",
                    "£ 5",
                    "4.0",
                    "13 Ratings",
                    "YES"
                )
            )
            data.add(
                ProductListModel(
                    dataTopCategory!!.get(pos).arrChickenImg.get(1),
                    dataTopCategory!!.get(pos).arrChicken.get(1),
                    "£2.55",
                    "£5.50",
                    "6 hrs",
                    "Blue Flame",
                    "5 kg",
                    "£ 5",
                    "3.1",
                    "1203 Ratings",
                    "YES"
                )
            )
            data.add(
                ProductListModel(
                    dataTopCategory!!.get(pos).arrChickenImg.get(2),
                    dataTopCategory!!.get(pos).arrChicken.get(2),
                    "£20.30",
                    "£30.00",
                    "5 hrs",
                    "Bon Appetit",
                    "5 kg",
                    "£ 20 ",
                    "4.4",
                    "34053 Ratings",
                    "NO"
                )
            )
            data.add(
                ProductListModel(
                    dataTopCategory!!.get(pos).arrChickenImg.get(3),
                    dataTopCategory!!.get(pos).arrChicken.get(3),
                    "£10.80",
                    "£14.20",
                    "4 hrs",
                    "Fresho",
                    "5 kg",
                    "£ 5",
                    "2.4",
                    "143 Ratings",
                    "YES"
                )
            )
        }

        if (pos == 3) {
            val ss = dataTopCategory!!.get(pos).arrChicken.get(0)
            val img = dataTopCategory!!.get(pos).arrChickenImg.get(0)
            data.add(
                ProductListModel(
                    dataTopCategory!!.get(pos).arrChickenImg.get(0),
                    dataTopCategory!!.get(pos).arrChicken.get(0),
                    "£2.55",
                    "£4.50",
                    "4 hrs",
                    "FRESHO",
                    "5 kg",
                    "£ 5",
                    "2.4",
                    "10443 Ratings",
                    "YES"
                )
            )
            data.add(
                ProductListModel(
                    dataTopCategory!!.get(pos).arrChickenImg.get(1),
                    dataTopCategory!!.get(pos).arrChicken.get(1),
                    "£4.55",
                    "£8.20",
                    "7 hrs",
                    "Blue Flame",
                    "5 kg",
                    "£ 5",
                    "5.0",
                    "103 Ratings",
                    "YES"
                )
            )
            data.add(
                ProductListModel(
                    dataTopCategory!!.get(pos).arrChickenImg.get(2),
                    dataTopCategory!!.get(pos).arrChicken.get(2),
                    "£20.00",
                    "£23.00",
                    "10 hrs",
                    "Bon Appetit",
                    "5 kg",
                    "£ 5",
                    "4.4",
                    "3353 Ratings",
                    "NO"
                )
            )
            data.add(
                ProductListModel(
                    dataTopCategory!!.get(pos).arrChickenImg.get(3),
                    dataTopCategory!!.get(pos).arrChicken.get(3),
                    "£14.00",
                    "£20.00",
                    "4 hrs",
                    "Fresho",
                    "5 kg",
                    "£ 15",
                    "2.4",
                    "1454 Ratings",
                    "YES"
                )
            )
        }

        if (pos == 4) {
            val ss = dataTopCategory!!.get(pos).arrChicken.get(0)
            val img = dataTopCategory!!.get(pos).arrChickenImg.get(0)
            data.add(
                ProductListModel(
                    dataTopCategory!!.get(pos).arrChickenImg.get(0),
                    dataTopCategory!!.get(pos).arrChicken.get(0),
                    "£0.99.00",
                    "£10.50",
                    "4 hrs",
                    "FRESHO",
                    "5 kg",
                    "£ 13",
                    "5.0",
                    "1003 Ratings",
                    "YES"
                )
            )
            data.add(
                ProductListModel(
                    dataTopCategory!!.get(pos).arrChickenImg.get(1),
                    dataTopCategory!!.get(pos).arrChicken.get(1),
                    "£2.55",
                    "£3.00",
                    "8 hrs",
                    "Blue Flame",
                    "5 kg",
                    "£ 5",
                    "4.1",
                    "12203 Ratings",
                    "YES"
                )
            )
            data.add(
                ProductListModel(
                    dataTopCategory!!.get(pos).arrChickenImg.get(2),
                    dataTopCategory!!.get(pos).arrChicken.get(2),
                    "£7.20",
                    "£12.20",
                    "9 hrs",
                    "Bon Appetit",
                    "5 kg",
                    "£ 5",
                    "4.4",
                    "3403 Ratings",
                    "NO"
                )
            )
            data.add(
                ProductListModel(
                    dataTopCategory!!.get(pos).arrChickenImg.get(3),
                    dataTopCategory!!.get(pos).arrChicken.get(3),
                    "£2.60",
                    "£4.40",
                    "5 hrs",
                    "Fresho",
                    "5 kg",
                    "£ 15",
                    "5.0",
                    "1433 Ratings",
                    "YES"
                )
            )
        }

        val dividerDrawable =
            ContextCompat.getDrawable(applicationContext, R.drawable.line_divider)
        recyProductList.addItemDecoration(DividerItemDecoration(dividerDrawable))

        val adapter = ProductListAdapter(data)
        recyProductList.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    fun mainCategory() {
        val arrayListFish = ArrayList<String>()
        arrayListFish.add("Chicken")
        arrayListFish.add("Beef")
        arrayListFish.add("Pork")
        arrayListFish.add("Turkey")


        val arrayListFriudts = ArrayList<String>()
        arrayListFriudts.add("Vegan/Plant Based Diet")
        arrayListFriudts.add("Vegan/Plant Based Diet")
        arrayListFriudts.add("Rice, Pasta & Noodles")
        arrayListFriudts.add("Meat-free Alternatives")


        val arrayListDairy = ArrayList<String>()
        // arrayListDairy.add("View All")
        arrayListDairy.add("Liquid Egg Whites")
        /*arrayListDairy.add("Kissan Mixed Fruit Jam")
        arrayListDairy.add("Mother Dairy Toned Milk")
        arrayListDairy.add("UPF Healthy Brown Eggs")
        arrayListDairy.add("OVO Top Pick White Eggs")*/


        val arrayListGrosries = ArrayList<String>()
        arrayListGrosries.add("Protein Bars & Bites")
        arrayListGrosries.add("Chocolate & Sweets")
        arrayListGrosries.add("Crisps & Popcorn")
        arrayListGrosries.add("Meaty Snacks")


        val arrayDringkList = ArrayList<String>()
        arrayDringkList.add("BCAA & Energy Drinks")
        arrayDringkList.add("Protein Drinks")
        arrayDringkList.add("Reign")
        // arrayListGrosries.add("Meaty Snacks")


        recyMainCategory.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        dataMainCtegory.add(ItemCategoryModel(R.drawable.meat, "Meat", arrayListFish))
        dataMainCtegory.add(ItemCategoryModel(R.drawable.fruts, "Veg & Vegan", arrayListFriudts))
        dataMainCtegory.add(ItemCategoryModel(R.drawable.egg, "Eggs & Dairy", arrayListDairy))
        dataMainCtegory.add(ItemCategoryModel(R.drawable.gro, "Snacks", arrayListGrosries))
        dataMainCtegory.add(ItemCategoryModel(R.drawable.drink, "DRINKS", arrayDringkList))

        val adapter = ItemCategoryAdapter(dataMainCtegory)
        recyMainCategory.adapter = adapter
        adapter.notifyDataSetChanged()

        yourDesiredMethod()

    }

    fun updateCartItem(cartItem: Int) {
        txtTotalCartItem.text = cartItem.toString()
    }

    override fun onResume() {
        super.onResume()
        txtTotalCartItem.text = totalCartItem.toString()
    }

}