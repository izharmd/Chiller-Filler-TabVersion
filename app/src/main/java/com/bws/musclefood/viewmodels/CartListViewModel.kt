package com.bws.musclefood.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.bws.musclefood.R
import com.bws.musclefood.itemcategory.cartlist.CartListResponse
import com.bws.musclefood.network.NetworkUtils
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.Resources
import kotlinx.coroutines.launch

class CartListViewModel(val repository: Repository,val context: Context):ViewModel() {

    var cartList = MutableLiveData<Resources<CartListResponse>>()

    fun getCartList(body: RequestBodies.GetAllCartDetailsBody) = viewModelScope.launch {

        callCartList(body)
    }

    private suspend fun callCartList(cartListBody: RequestBodies.GetAllCartDetailsBody) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            cartList.postValue(Resources.Loading(loadingMessage = context.resources.getString(R.string.LOADING_PLEASE_WAIT)))
            try {
                val response = repository.getAllCartDetails(cartListBody)
                cartList.postValue(Resources.Success(response.body()))
            } catch (e: Exception) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
                cartList.postValue(Resources.Error(errorMessage = e.message.toString()))
            }
        } else {
            cartList.postValue(Resources.NoInternet(context.resources.getString(R.string.NO_INTERNET_CONNECTION)))
        }
    }
}