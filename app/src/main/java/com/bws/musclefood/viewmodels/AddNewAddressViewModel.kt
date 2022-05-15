package com.bws.musclefood.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bws.musclefood.R
import com.bws.musclefood.delivery.AddNewAddresResponse
import com.bws.musclefood.network.NetworkUtils
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.Resources
import kotlinx.coroutines.launch

class AddNewAddressViewModel(val repository: Repository,val context: Context):ViewModel() {

    var response  = MutableLiveData<Resources<AddNewAddresResponse>>()

    fun AddEditDeliveryDetails(body: RequestBodies.AddEditDeliveryDetails) = viewModelScope.launch {
        addNewAddress(body)
    }


    suspend fun addNewAddress(body: RequestBodies.AddEditDeliveryDetails) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            response.postValue(
                Resources.Loading(
                    loadingMessage = context.resources.getString(
                        R.string.LOADING_PLEASE_WAIT
                    )
                )
            )
            try {
                val response = repository.addNewAddress(body)
                response.postValue(Resources.Success(response.body()))
            } catch (e: Exception) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
                response.postValue(Resources.Error(errorMessage = e.message.toString()))
            }
        } else {
            response.postValue(Resources.NoInternet(context.resources.getString(R.string.NO_INTERNET_CONNECTION)))
        }
    }

}