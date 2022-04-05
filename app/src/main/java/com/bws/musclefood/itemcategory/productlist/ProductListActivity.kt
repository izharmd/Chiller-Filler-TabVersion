package com.bws.musclefood.itemcategory.productlist

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bws.musclefood.Item
import com.bws.musclefood.NavigationAdapter
import com.bws.musclefood.NavigationAdapter2
import com.bws.musclefood.R
import com.bws.musclefood.common.Constant
import com.bws.musclefood.common.Constant.Companion.pos
import com.bws.musclefood.common.Constant.Companion.totalCartItem
import com.bws.musclefood.factory.FactoryProvider
import com.bws.musclefood.favourites.FavouritesActivity
import com.bws.musclefood.itemcategory.ItemCategoryAdapter
import com.bws.musclefood.itemcategory.ItemCategoryModel
import com.bws.musclefood.itemcategory.basket.BasketsActivity
import com.bws.musclefood.itemcategory.cartlist.CartListActivity
import com.bws.musclefood.itemcategory.productlist.categorytop.TopCategoryAdapter
import com.bws.musclefood.itemcategory.productlist.categorytop.TopCategoryModel
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.orders.SearchOrderActivity
import com.bws.musclefood.profile.MyProfileActivity
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.urils.AlertDialog
import com.bws.musclefood.urils.LoadingDialog
import com.bws.musclefood.urils.PreferenceConnector
import com.bws.musclefood.urils.Resources
import com.bws.musclefood.viewmodels.CategoryViewModel
import com.bws.musclefood.viewmodels.ProductListViewModel
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration
import com.google.gson.Gson
import com.volcaniccoder.bottomify.BottomifyNavigationView
import com.volcaniccoder.bottomify.OnNavigationItemChangeListener
import kotlinx.android.synthetic.main.a_profile_new.*
import kotlinx.android.synthetic.main.activity_productlist.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.drawer_layout.*
import kotlinx.android.synthetic.main.side_menu_drawer.*
import kotlinx.android.synthetic.main.tool_bar_search_view.*
import kotlinx.coroutines.channels.Channel

class ProductListActivity : AppCompatActivity() {

    lateinit var navigationAdapter: NavigationAdapter
    lateinit var navigationAdapter2: NavigationAdapter2

    lateinit var categoryViewModel: CategoryViewModel

    lateinit var productListViewModel: ProductListViewModel

    lateinit var preferenceConnector: PreferenceConnector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_drawer)
        supportActionBar?.hide()
        txtLogInSignUp.text = Constant.serviceType

        preferenceConnector = PreferenceConnector(this)

        val factory = FactoryProvider(Repository(), this)
        categoryViewModel = ViewModelProvider(this, factory).get(CategoryViewModel::class.java)
        val pram = RequestBodies.GetMenu()
        val loadingDialog = LoadingDialog.progressDialog(this)
        categoryViewModel.getCategory(pram)

        categoryViewModel.menuResult.observe(this, Observer {
            when (it) {
                is Resources.Loading -> {
                    loadingDialog.show()
                }
                is Resources.NoInternet -> {
                    loadingDialog.hide()
                }
                is Resources.Success -> {
                    // Toast.makeText(this, "sssss", Toast.LENGTH_SHORT).show()
                    txtRetailReady.text = it.data?.get(0)?.Category
                    txtFoodService.text = it.data?.get(1)?.Category

                    Constant.categoryId =
                        it.data?.get(1)?.SubCategory?.get(0)?.CategoryID.toString()
                    Constant.categoryName =
                        it.data?.get(1)?.SubCategory?.get(0)?.CategoryName.toString()

                    navLV.visibility = View.VISIBLE
                    navLV_2.visibility = View.GONE

                    val dividerDrawable =
                        ContextCompat.getDrawable(applicationContext, R.drawable.line_divider)
                    navLV.addItemDecoration(DividerItemDecoration(dividerDrawable))

                    navigationAdapter = NavigationAdapter(it.data?.get(0)?.SubCategory!!)
                    navLV.adapter = navigationAdapter
                    navigationAdapter.notifyDataSetChanged()


                    navLV_2.addItemDecoration(DividerItemDecoration(dividerDrawable))
                    navigationAdapter2 = NavigationAdapter2(it.data?.get(1)?.SubCategory!!)
                    navLV_2.adapter = navigationAdapter2
                    navigationAdapter2.notifyDataSetChanged()



                    //TOP CATEGORY MENU
                    recyTopCategory.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    val adapterTop = TopCategoryAdapter(it.data?.get(0)?.SubCategory!!)
                    recyTopCategory.adapter = adapterTop
                    adapterTop.notifyDataSetChanged()


                    loadingDialog.hide()

//  CALL MAIN PRODUCT LIST AFTER RESPONSE CATEGORY MENU
                    callProdctListAPI()
                }

                is Resources.Error -> {
                    loadingDialog.hide()
                    Toast.makeText(this, "errrr", Toast.LENGTH_SHORT).show()
                }
            }
        })

        imvSearch.setOnClickListener() {
            //txtSearchProduct.visibility = View.GONE
            searchView.visibility = View.VISIBLE
        }

        imvCart.setOnClickListener() {

            if (txtTotalCartItem.text.equals("0")) {
                AlertDialog().dialog(this, "Please add at lease one product to cart.")
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
            /* items.clear()
             items.add(Item("Beef"))
             items.add(Item("Chicken"))
             items.add(Item("Pork"))
             items.add(Item("Turkey"))
             items.add(Item("Fish"))*/

            navLV.visibility = View.VISIBLE
            navLV_2.visibility = View.GONE
            /* val dividerDrawable =
                 ContextCompat.getDrawable(applicationContext, R.drawable.line_divider)
             navLV.addItemDecoration(DividerItemDecoration(dividerDrawable))

             navigationAdapter = NavigationAdapter(items)
             navLV.adapter = navigationAdapter
             navigationAdapter.notifyDataSetChanged()*/
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

            /*  items.clear()
              items.add(Item("Beef"))
              items.add(Item("Chicken"))
              items.add(Item("Pork"))
              items.add(Item("Turkey"))
              items.add(Item("Fish"))*/

            navLV.visibility = View.GONE
            navLV_2.visibility = View.VISIBLE
            /*  val dividerDrawable =
                  ContextCompat.getDrawable(applicationContext, R.drawable.line_divider)
              navLV_2.addItemDecoration(DividerItemDecoration(dividerDrawable))

              navigationAdapter2 = NavigationAdapter2(items)
              navLV_2.adapter = navigationAdapter2
              navigationAdapter2.notifyDataSetChanged()*/
        }
    }

    fun callProdctListAPI() {

        productListViewModel = ViewModelProvider(
            this,
            FactoryProvider(Repository(), this)
        ).get(ProductListViewModel::class.java)

        val pramProductDetails = RequestBodies.PopulateProductDetailsBody(
            preferenceConnector.getValueString("USER_ID")!!,
            Constant.categoryId,
            Constant.categoryName,
            "12233"
        )
        productListViewModel.populateProduct(pramProductDetails)
        productListViewModel.productListResult.observe(this, Observer {

            when (it) {
                is Resources.Loading -> {

                }
                is Resources.NoInternet -> {

                }
                is Resources.Success -> {
                    recyProductList.layoutManager = LinearLayoutManager(this)
                    val dividerDrawable =
                        ContextCompat.getDrawable(applicationContext, R.drawable.line_divider)
                    recyProductList.addItemDecoration(DividerItemDecoration(dividerDrawable))
                    val adapter = ProductListAdapter(it.data!!)
                    recyProductList.adapter = adapter
                    adapter.notifyDataSetChanged()
                }

                is Resources.Error -> {

                }
            }

        })
    }


    fun closeDrawer() {

        drawer_layout.closeDrawer(Gravity.LEFT)
    }


    fun updateCartItem(cartItem: Int) {
        txtTotalCartItem.text = cartItem.toString()
    }

    override fun onResume() {
        super.onResume()
        txtTotalCartItem.text = totalCartItem.toString()
    }

}