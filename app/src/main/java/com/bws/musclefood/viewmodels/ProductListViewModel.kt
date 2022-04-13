package com.bws.musclefood.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bws.musclefood.R
import com.bws.musclefood.itemcategory.productlist.ProductListResponse
import com.bws.musclefood.network.NetworkUtils
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.Resources
import kotlinx.coroutines.launch

class ProductListViewModel(val repository: Repository,val context: Context):ViewModel() {

   var productListResult = MutableLiveData<Resources<ProductListResponse>>()


    fun populateProduct(body:RequestBodies.PopulateProductDetailsBody) = viewModelScope.launch {
        productList(body)
    }


    private suspend fun productList(productDetailBody: RequestBodies.PopulateProductDetailsBody) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            productListResult.postValue(Resources.Loading(loadingMessage = context.resources.getString(R.string.LOADING_PLEASE_WAIT)))
            try {
                val response = repository.populateProductDetails(productDetailBody)
                productListResult.postValue(Resources.Success(response.body()))
            } catch (e: Exception) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
                productListResult.postValue(Resources.Error(errorMessage = e.message.toString()))
            }
        } else {
            productListResult.postValue(Resources.NoInternet(context.resources.getString(R.string.NO_INTERNET_CONNECTION)))
        }
    }
}