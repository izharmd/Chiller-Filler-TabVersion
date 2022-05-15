package com.bws.musclefood.itemcategory.productlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.bws.musclefood.NavigationAdapter
import com.bws.musclefood.NavigationAdapter2
import com.bws.musclefood.R
import com.bws.musclefood.User
import com.bws.musclefood.common.Constant
import com.bws.musclefood.common.Constant.Companion.foodService
import com.bws.musclefood.common.Constant.Companion.hashMap
import com.bws.musclefood.common.Constant.Companion.mainCategory
import com.bws.musclefood.common.Constant.Companion.retailReady
import com.bws.musclefood.common.Constant.Companion.totalCartItem
import com.bws.musclefood.database.AppDatabase
import com.bws.musclefood.factory.FactoryProvider
import com.bws.musclefood.favourites.FavouritesActivity
import com.bws.musclefood.interfaceCallback.CallbackInterface
import com.bws.musclefood.itemcategory.basket.BasketsActivity
import com.bws.musclefood.itemcategory.cartlist.CartListActivity
import com.bws.musclefood.itemcategory.productlist.categorytop.TopCategoryAdapter
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.orders.searchorder.SearchOrderActivity
import com.bws.musclefood.profile.MyProfileActivity
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.AlertDialog
import com.bws.musclefood.utils.LoadingDialog
import com.bws.musclefood.utils.PreferenceConnector
import com.bws.musclefood.utils.Resources
import com.bws.musclefood.viewmodels.*
import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration
import com.google.gson.Gson
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.volcaniccoder.bottomify.BottomifyNavigationView
import com.volcaniccoder.bottomify.OnNavigationItemChangeListener
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.HttpEntity
import cz.msebera.android.httpclient.entity.StringEntity
import kotlinx.android.synthetic.main.activity_productlist.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.drawer_layout.*
import kotlinx.android.synthetic.main.side_menu_drawer.*
import kotlinx.android.synthetic.main.tool_bar_search_view.*
import org.json.JSONArray
import org.json.JSONObject


class ProductListActivity : AppCompatActivity(), CallbackInterface {

    lateinit var navigationAdapter: NavigationAdapter
    lateinit var navigationAdapter2: NavigationAdapter2
    lateinit var topCategoryAdapter: TopCategoryAdapter

    lateinit var categoryViewModel: CategoryViewModel

    lateinit var productListViewModel: ProductListViewModel

    lateinit var insertUpdateCartViewModel: InsertUpdateCartViewModel
    lateinit var addFavouriteViewModel: AddFavouriteViewModel
    lateinit var removeFavoriteViewModel: RemoveFavoriteViewModel

    lateinit var preferenceConnector: PreferenceConnector

    //lateinit var loginResponse: LoginResponse

    var respository: Repository? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_drawer)
        supportActionBar?.hide()
        txtLogInSignUp.text = Constant.serviceType


        val user = User("123")

        println("USER==="+user.id)
        println("USER==="+user.uniqueIdentifier)


        preferenceConnector = PreferenceConnector(this)

        var db = AppDatabase(this)

        respository = Repository()

        val factory = FactoryProvider(respository!!, this)
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
                    loadingDialog.dismiss()
                }
                is Resources.Success -> {
                    // Toast.makeText(this, "sssss", Toast.LENGTH_SHORT).show()
                    txtRetailReady.text = it.data?.get(0)?.Category
                    txtFoodService.text = it.data?.get(1)?.Category

                    retailReady = it.data?.get(0)?.Category.toString()
                    foodService = it.data?.get(1)?.Category.toString()

                    mainCategory = retailReady

                    Constant.categoryId =
                        it.data?.get(1)?.SubCategory?.get(0)?.CategoryID.toString()
                    Constant.categoryName =
                        it.data?.get(1)?.SubCategory?.get(0)?.CategoryName.toString()

                    navLV.visibility = View.VISIBLE
                    navLV_2.visibility = View.GONE

                    val dividerDrawable =
                        ContextCompat.getDrawable(applicationContext, R.drawable.line_divider)
                    navLV.addItemDecoration(DividerItemDecoration(dividerDrawable))

                    navigationAdapter = NavigationAdapter(it.data?.get(0)!!.SubCategory)
                    navLV.adapter = navigationAdapter
                    navigationAdapter.notifyDataSetChanged()


                    navLV_2.addItemDecoration(DividerItemDecoration(dividerDrawable))
                    navigationAdapter2 = NavigationAdapter2(it.data?.get(1)?.SubCategory!!)
                    navLV_2.adapter = navigationAdapter2
                    navigationAdapter2.notifyDataSetChanged()


                    //TOP CATEGORY MENU
                    recyTopCategory.layoutManager =
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    topCategoryAdapter = TopCategoryAdapter(it.data?.get(0)?.SubCategory!!)
                    recyTopCategory.adapter = topCategoryAdapter
                    topCategoryAdapter.notifyDataSetChanged()


                    loadingDialog.dismiss()

//  CALL MAIN PRODUCT LIST AFTER RESPONSE CATEGORY MENU
                    callProdctListAPI()
                }

                is Resources.Error -> {
                    loadingDialog.dismiss()
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
                 updateInsertCart()
             }
         // updateInsertCart()
        }

        bottomify.setOnNavigationItemChangedListener(object : OnNavigationItemChangeListener {
            override fun onNavigationItemChanged(navigationItem: BottomifyNavigationView.NavigationItem) {
                val pos = navigationItem.position
                if (pos == 0) {

                } else if (pos == 1) {
                    startActivity(Intent(this@ProductListActivity, SearchOrderActivity::class.java))
                } else if (pos == 2) {
                    startActivity(Intent(this@ProductListActivity, CartListActivity::class.java))
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
            }

            override fun onDrawerClosed(drawerView: View) {}
            override fun onDrawerStateChanged(newState: Int) {

            }
        })


        burgerMenu.setOnClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }

        txtRetailReady.setOnClickListener {
            txtLogInSignUp.text = "Retail Ready"
            txtRetailReady.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up_15, 0)
            txtFoodService.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.arrow_down_24,
                0
            )
            navLV.visibility = View.VISIBLE
            navLV_2.visibility = View.GONE
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
            navLV.visibility = View.GONE
            navLV_2.visibility = View.VISIBLE
        }
    }

    fun callProdctListAPI() {

        productListViewModel = ViewModelProvider(
            this,
            FactoryProvider(respository!!, this)
        ).get(ProductListViewModel::class.java)

        val pramProductDetails = RequestBodies.PopulateProductDetailsBody(
            preferenceConnector.getValueString("USER_ID")!!,
            Constant.categoryId,
            Constant.categoryName,
            "12233",
            mainCategory
        )

        println("JSON===" + Gson().toJson(pramProductDetails))
        productListViewModel.populateProduct(pramProductDetails)

        productListViewModel.productListResult.observe(this) {

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
                    val adapter = ProductListAdapter(this,it.data!!,respository!!)
                    recyProductList.adapter = adapter
                    adapter.notifyDataSetChanged()

                    this.viewModelStore.clear()
                }

                is Resources.Error -> {

                }
            }

        }
    }


    fun calAddFavouritePI(productId: String) {

        addFavouriteViewModel = ViewModelProvider(
            this,
            FactoryProvider(respository!!, this)
        ).get(AddFavouriteViewModel::class.java)

        val addFavourite = RequestBodies.AddFavouriteListBody(
            productId,
            preferenceConnector.getValueString("USER_ID")!!,

            )

        println("JSON===" + Gson().toJson(addFavourite))
        addFavouriteViewModel.getAddFavourite(addFavourite)

        val loadingDialog = LoadingDialog.progressDialog(this)

        addFavouriteViewModel.resultAddFavourite.observe(this) {

            when (it) {
                is Resources.Loading -> {
                    loadingDialog.show()
                }
                is Resources.NoInternet -> {
                    loadingDialog.hide()
                }
                is Resources.Success -> {

                    Toast.makeText(this, it.data?.StatusMSG, Toast.LENGTH_SHORT).show()
                    loadingDialog.hide()

                    this.viewModelStore.clear()
                }

                is Resources.Error -> {
                    Toast.makeText(this, "reeeee", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }


    fun calRemoveFavouritePI(productId: String) {

        removeFavoriteViewModel = ViewModelProvider(
            this,
            FactoryProvider(respository!!, this)
        ).get(RemoveFavoriteViewModel::class.java)

        val removeFavourite = RequestBodies.RemoveFavoriteProductBody(
            productId,
            preferenceConnector.getValueString("USER_ID")!!,

            )

        println("JSON===" + Gson().toJson(removeFavourite))
        removeFavoriteViewModel.getRemoveFavorite(removeFavourite)

        val loadingDialog = LoadingDialog.progressDialog(this)

        removeFavoriteViewModel.resultRemoveRemoveFavorite.observe(this) {

            when (it) {
                is Resources.Loading -> {
                    loadingDialog.show()
                }
                is Resources.NoInternet -> {
                    loadingDialog.hide()
                }
                is Resources.Success -> {

                    Toast.makeText(this, it.data?.StatusMSG, Toast.LENGTH_SHORT).show()
                    loadingDialog.hide()

                    this.viewModelStore.clear()
                }

                is Resources.Error -> {
                    Toast.makeText(this, "reeeee", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    fun updateInsertCart() {
        val client = AsyncHttpClient()
        val jsonArray = JSONArray()
        var keys = hashMap.keys
        for (key in keys) {
            var jsonObject = JSONObject()
            var cartModel = hashMap.get(key)
            try {
                jsonObject.put("CartItemID", "")
                jsonObject.put("Price", cartModel?.price)
                jsonObject.put("ProductID", cartModel?.productId)
                jsonObject.put("Quantity", cartModel?.quantity)
                jsonObject.put("SessionID", Constant.sessionID);
                jsonObject.put("TotalPrice", cartModel?.price)
                jsonObject.put("UserID", preferenceConnector.getValueString("USER_ID").toString())
                jsonArray.put(jsonObject)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        println("CART INSERT =="+jsonArray.toString())

        val entity: HttpEntity
        entity = try {
            StringEntity(jsonArray.toString(), "UTF-8")
        } catch (e: IllegalArgumentException) {
            Log.d("HTTP", "StringEntity: IllegalArgumentException")
            return
        }
        val contentType = "application/json; charset=utf-8"
        client.post(
            this,
           Constant.BASE_URL + "InsertUpdateCartDetails",
            entity,
            contentType,
            object : AsyncHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<Header>,
                    responseBody: ByteArray
                ) {
                    val asyncResult = String(responseBody)
                   // Toast.makeText(this@ProductListActivity, asyncResult, Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@ProductListActivity, CartListActivity::class.java))
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<Header>,
                    responseBody: ByteArray,
                    error: Throwable
                ) {
                    Toast.makeText(this@ProductListActivity, statusCode.toString(), Toast.LENGTH_SHORT).show()
                }
            })
    }


    fun closeDrawer() {
        drawer_layout.closeDrawer(Gravity.LEFT)
        topCategoryAdapter.notifyDataSetChanged()
    }


    fun updateCartItem(cartItem: Int) {
        txtTotalCartItem.text = cartItem.toString()
    }

    override fun onResume() {
        super.onResume()
        txtTotalCartItem.text = totalCartItem.toString()
    }

    override fun passResultCallback(message: ProductListResponseItem) {

       // Toast.makeText(this,message.ProductName,Toast.LENGTH_SHORT).show()

    }

}
