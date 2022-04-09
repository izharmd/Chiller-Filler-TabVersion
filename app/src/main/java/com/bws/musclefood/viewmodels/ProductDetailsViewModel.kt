package com.bws.musclefood.viewmodels

import android.content.Context
import android.widget.Toast

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bws.musclefood.R
import com.bws.musclefood.network.NetworkUtils
import com.bws.musclefood.network.RequestBodies

import com.bws.musclefood.productdetails.ProductDetailsResponse
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.urils.Resources
import kotlinx.coroutines.launch

class ProductDetailsViewModel(val repository: Repository,val context: Context):ViewModel() {

    var resultProductDetails = MutableLiveData<Resources<ProductDetailsResponse>>()

    fun getProductDetails(body:RequestBodies.PopulateProductDetailsByProductIDBody) = viewModelScope.launch {

        callApiProductDetails(body)
    }
    private suspend fun callApiProductDetails(body: RequestBodies.PopulateProductDetailsByProductIDBody) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            resultProductDetails.postValue(Resources.Loading(loadingMessage = context.resources.getString(R.string.LOADING_PLEASE_WAIT)))
            try {
                val response = repository.getProductDetails(body)
                resultProductDetails.postValue(Resources.Success(response.body()))
            } catch (e: Exception) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
                resultProductDetails.postValue(Resources.Error(errorMessage = e.message.toString()))
            }
        } else {
            resultProductDetails.postValue(Resources.NoInternet(context.resources.getString(R.string.NO_INTERNET_CONNECTION)))
        }
    }
}