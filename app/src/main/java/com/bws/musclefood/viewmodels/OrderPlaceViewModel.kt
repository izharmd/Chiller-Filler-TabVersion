package com.bws.musclefood.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bws.musclefood.R
import com.bws.musclefood.network.NetworkUtils
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.payment.OrderSuccessResponse
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.Resources
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import org.json.JSONObject

class OrderPlaceViewModel(val repository: Repository, val context: Context ):ViewModel() {

    var resultOrderPlace = MutableLiveData<Resources<OrderSuccessResponse>>()


    fun getPlaceOrder(body:JSONObject) = viewModelScope.launch {
        placeOrder(body)
    }


    private suspend fun placeOrder(body: JSONObject) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            resultOrderPlace.postValue(Resources.Loading(loadingMessage = context.resources.getString(R.string.LOADING_PLEASE_WAIT)))
            try {
                val response = repository.insertPlaceOrderDetails(body)
                resultOrderPlace.postValue(Resources.Success(response.body()))
            } catch (e: Exception) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
                resultOrderPlace.postValue(Resources.Error(errorMessage = e.message.toString()))
            }
        } else {
            resultOrderPlace.postValue(Resources.NoInternet(context.resources.getString(R.string.NO_INTERNET_CONNECTION)))
        }
    }

}