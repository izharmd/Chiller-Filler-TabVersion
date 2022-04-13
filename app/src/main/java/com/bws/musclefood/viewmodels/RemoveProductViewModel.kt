package com.bws.musclefood.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bws.musclefood.R
import com.bws.musclefood.itemcategory.cartlist.RemoveProductResponse
import com.bws.musclefood.network.NetworkUtils
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.Resources
import kotlinx.coroutines.launch

class RemoveProductViewModel(val repository: Repository,val context: Context):ViewModel() {

    var resultRemoveProduct = MutableLiveData<Resources<RemoveProductResponse>>()

    fun getRemoveProduct(body:RequestBodies.GetRemoveCartProductBody) = viewModelScope.launch {
        removeProduct(body)
    }


    private suspend fun removeProduct(loginBody: RequestBodies.GetRemoveCartProductBody) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            resultRemoveProduct.postValue(Resources.Loading(loadingMessage = context.resources.getString(R.string.LOADING_PLEASE_WAIT)))
            try {
                val response = repository.getRemoveProduct(loginBody)
                resultRemoveProduct.postValue(Resources.Success(response.body()))
            } catch (e: Exception) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
                resultRemoveProduct.postValue(Resources.Error(errorMessage = e.message.toString()))
            }
        } else {
            resultRemoveProduct.postValue(Resources.NoInternet(context.resources.getString(R.string.NO_INTERNET_CONNECTION)))
        }
    }
}