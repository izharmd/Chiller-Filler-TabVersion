package com.bws.musclefood.repo

import com.bws.musclefood.common.RetrofitHelper
import com.bws.musclefood.network.RequestBodies
import org.json.JSONArray
import org.json.JSONObject

class Repository/*(private val db:ContactDatabase)*/{
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

    suspend fun getRemoveProduct(body: RequestBodies.GetRemoveCartProductBody) =
        retrofitInntance.callRemoveCartProduct(body)

    suspend fun registrationDetails(body: RequestBodies.RegistrationDetailsBody) =
        retrofitInntance.callRegistrationDetails(body)

    suspend fun addAddFavourite(body: RequestBodies.AddFavouriteListBody) =
        retrofitInntance.callAddFavouriteList(body)


    suspend fun removeFavourite(body: RequestBodies.RemoveFavoriteProductBody) =
        retrofitInntance.callRemoveFavourite(body)

    suspend fun favouriteList(body: RequestBodies.FavouriteListBody) =
        retrofitInntance.callFavouriteList(body)


    suspend fun searchOrder(body: RequestBodies.SearchOrdersBody) =
        retrofitInntance.callSearchOrders(body)

    suspend fun updateProfile(body: RequestBodies.UpdateProfileBody) =
        retrofitInntance.callUpdateProfile(body)

    suspend fun userProfileDetails(body: RequestBodies.GetUserProfileDetailsBody) =
        retrofitInntance.callGetUserProfileDetails(body)


    suspend fun insertPlaceOrderDetails(body:JSONObject) =
        retrofitInntance.callInsertPlaceOrderDetails(body)

}