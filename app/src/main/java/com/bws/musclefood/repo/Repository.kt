package com.bws.musclefood.repo

import com.bws.musclefood.common.ApiInterface
import com.bws.musclefood.common.RetrofitHelper
import com.bws.musclefood.network.RequestBodies
import org.json.JSONArray

class Repository {
    // var retrofitInntance = RetrofitHelper.getInstance().create(ApiInterface::class.java)
    var retrofitInntance = RetrofitHelper.retrofitService
    suspend fun loginUser(body: RequestBodies.LoginBody) = retrofitInntance.callLoginApi(body)
    suspend fun categoryMenu(body: RequestBodies.GetMenu) = retrofitInntance.callMenuCategory(body)

    suspend fun insertUpdate(body: JSONArray) =
        retrofitInntance.callInsertUpdateCartDetails(body)

    suspend fun populateProductDetails(body: RequestBodies.PopulateProductDetailsBody) =
        retrofitInntance.callPopulateProductDetails(body)

    suspend fun getAllCartDetails(body: RequestBodies.GetAllCartDetailsBody) =
        retrofitInntance.callGetAllCartDetails(body)


    suspend fun getProductDetails(body: RequestBodies.PopulateProductDetailsByProductIDBody) =
        retrofitInntance.callPopulateProductDetailsByProductID(body)

}