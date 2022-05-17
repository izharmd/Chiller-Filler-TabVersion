package com.bws.musclefood.common


import com.bws.musclefood.delivery.AddNewAddresResponse
import com.bws.musclefood.delivery.deliveryoption.DeliveryOptionListResponse
import com.bws.musclefood.favourites.FavouritesListResponse
import com.bws.musclefood.itemcategory.cartlist.CartListResponse
import com.bws.musclefood.itemcategory.cartlist.RemoveProductResponse
import com.bws.musclefood.itemcategory.productlist.*

import com.bws.musclefood.login.LoginResponse
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.orders.searchorder.SearchOrderResponse
import com.bws.musclefood.payment.OrderSuccessResponse
import com.bws.musclefood.payment.PaymentDetailsResponse
import com.bws.musclefood.productdetails.ProductDetailsResponse
import com.bws.musclefood.profile.UpdateProfileResponse
import com.bws.musclefood.profile.UserProfileDetailsResponse
import com.bws.musclefood.signup.RegistrationResponse
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject

import retrofit2.Response

import retrofit2.http.Body

import retrofit2.http.POST

interface ApiInterface {
    @POST("UserMobileloginDetails")
    suspend fun callLoginApi(@Body loginPram: RequestBodies.LoginBody): Response<LoginResponse>

    @POST("PopulateProductCategoryMenu")
    suspend fun callMenuCategory(@Body menuCategory: RequestBodies.GetMenu): Response<MenuResponse>

    @POST("PopulateProductDetails")
    suspend fun callPopulateProductDetails(@Body populateProDetailPram: RequestBodies.PopulateProductDetailsBody): Response<ProductListResponse>

    @POST("GetAllCartDetails")
    suspend fun callGetAllCartDetails(@Body getCartDetails: RequestBodies.GetAllCartDetailsBody): Response<CartListResponse>


    @POST("InsertUpdateCartDetails")
    suspend fun callInsertUpdateCartDetails(@Body getCartDetails: JSONArray): Response<String>


    @POST("PopulateProductDetailsByProductID")
    suspend fun callPopulateProductDetailsByProductID(@Body getCartDetails: RequestBodies.PopulateProductDetailsByProductIDBody): Response<ProductDetailsResponse>


    @POST("RemoveCartProduct")
    suspend fun callRemoveCartProduct(@Body getCartDetails: RequestBodies.GetRemoveCartProductBody): Response<RemoveProductResponse>


    @POST("RegistrationDetails")
    suspend fun callRegistrationDetails(@Body getCartDetails: RequestBodies.RegistrationDetailsBody): Response<RegistrationResponse>


    @POST("AddFavouriteList")
    suspend fun callAddFavouriteList(@Body getCartDetails: RequestBodies.AddFavouriteListBody): Response<ResponseAddFavourite>

    @POST("RemoveFavoriteProduct")
    suspend fun callRemoveFavourite(@Body getCartDetails: RequestBodies.RemoveFavoriteProductBody): Response<RemoveFavoriteResponse>


    @POST("FavouriteList")
    suspend fun callFavouriteList(@Body getCartDetails: RequestBodies.FavouriteListBody): Response<FavouritesListResponse>

    @POST("SearchOrders")
    suspend fun callSearchOrders(@Body getCartDetails: RequestBodies.SearchOrdersBody): Response<SearchOrderResponse>


    @POST("UpdateProfile")
    suspend fun callUpdateProfile(@Body getCartDetails: RequestBodies.UpdateProfileBody): Response<UpdateProfileResponse>


    @POST("GetUserProfileDetails")
    suspend fun callGetUserProfileDetails(@Body getCartDetails: RequestBodies.GetUserProfileDetailsBody): Response<UserProfileDetailsResponse>


    @POST("InsertPlaceOrderDetails")
    suspend fun callInsertPlaceOrderDetails(@Body insertPlaceOrder: JSONObject): Response<OrderSuccessResponse>


    @POST("GetDeliveryDetails")
    suspend fun getDeliveryDetails(@Body json: RequestBodies.GetDeliveryDetails): Response<DeliveryOptionListResponse>

    @POST("AddEditDeliveryDetails")
    suspend fun addNewAddress(@Body getCartDetails: RequestBodies.AddEditDeliveryDetails): Response<AddNewAddresResponse>


    @POST("AddEditPaymentDetails")
    suspend fun AddEditPaymentDetails(@Body getCartDetails: RequestBodies.AddEditPaymentDetails): Response<PaymentDetailsResponse>


    /*   @GET("data.json")
       suspend fun callLoginApi(): Response<CovidDataList>

       @POST("Populateproductmenu")
       suspend fun callProductListApi(@Body loginPram: ProductListBody): Response<ProductMenuResponse>


       @POST("GetAllCartDetails")
       suspend fun callGetAllCartDetails(@Body getAllCartDetailsBody: RequestBodies.GetAllCartDetailsBody): Response<CartListResponse>
   */


}
