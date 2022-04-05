package com.bws.musclefood.repo

import com.bws.musclefood.common.ApiInterface
import com.bws.musclefood.common.RetrofitHelper
import com.bws.musclefood.network.RequestBodies

class Repository {
   // var retrofitInntance = RetrofitHelper.getInstance().create(ApiInterface::class.java)
    var retrofitInntance = RetrofitHelper.retrofitService
    suspend fun loginUser(body: RequestBodies.LoginBody) = retrofitInntance.callLoginApi(body)
    suspend fun categoryMenu(body: RequestBodies.GetMenu) = retrofitInntance.callMenuCategory(body)

    //suspend fun loginUser() = loginApi1.callLoginApi()

    // suspend fun getProductList(body: ProductListBody) = loginApi1.callProductListApi(body)

    suspend fun populateProductDetails(body: RequestBodies.PopulateProductDetailsBody) = retrofitInntance.callPopulateProductDetails(body)


    // suspend fun GetAllCartDetails(body: RequestBodies.GetAllCartDetailsBody) = loginApi1.callGetAllCartDetails(body)


}