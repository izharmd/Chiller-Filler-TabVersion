package com.bws.musclefood.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bws.musclefood.R
import com.bws.musclefood.network.NetworkUtils
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.payment.PaymentDetailsResponse
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.Resources
import kotlinx.coroutines.launch

class PaymentDetailsViewModel(val repository: Repository,val context: Context):ViewModel() {

    var response = MutableLiveData<Resources<PaymentDetailsResponse>>()


    fun getPaymentDetails(body:RequestBodies.AddEditPaymentDetails) = viewModelScope.launch {
        callAddEditPaymentDetails(body)
    }

    suspend fun callAddEditPaymentDetails(body: RequestBodies.AddEditPaymentDetails) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            response.postValue(
                Resources.Loading(
                    loadingMessage = context.resources.getString(
                        R.string.LOADING_PLEASE_WAIT
                    )
                )
            )
            try {
                val result = repository.AddEditPaymentDetails(body)
                response.postValue(Resources.Success(result.body()))
            } catch (e: Exception) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
                response.postValue(Resources.Error(errorMessage = e.message.toString()))
            }
        } else {
            response.postValue(Resources.NoInternet(context.resources.getString(R.string.NO_INTERNET_CONNECTION)))
        }
    }
}