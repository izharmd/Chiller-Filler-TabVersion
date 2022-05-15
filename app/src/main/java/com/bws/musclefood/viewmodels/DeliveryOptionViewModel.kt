package com.bws.musclefood.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bws.musclefood.R
import com.bws.musclefood.delivery.deliveryoption.DeliveryOptionListResponse
import com.bws.musclefood.network.NetworkUtils
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.Resources
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import org.json.JSONObject

class DeliveryOptionViewModel(val repository: Repository,val context: Context):ViewModel() {

    val addressList = MutableLiveData<Resources<DeliveryOptionListResponse>>()

    fun getDeliveryList(body: JSONObject) = viewModelScope.launch {

        calGetDeliveryDetails(body)
    }

    private suspend fun calGetDeliveryDetails(jsonBody: JSONObject) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            addressList.postValue(Resources.Loading(loadingMessage = context.resources.getString(R.string.LOADING_PLEASE_WAIT)))
            try {
                val response = repository.getDeliveryDetails(jsonBody)
                addressList.postValue(Resources.Success(response.body()))
            } catch (e: Exception) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
                addressList.postValue(Resources.Error(errorMessage = e.message.toString()))
            }
        } else {
            addressList.postValue(Resources.NoInternet(context.resources.getString(R.string.NO_INTERNET_CONNECTION)))
        }
    }
}