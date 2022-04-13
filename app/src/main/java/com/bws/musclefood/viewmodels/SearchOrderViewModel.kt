package com.bws.musclefood.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bws.musclefood.R
import com.bws.musclefood.network.NetworkUtils
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.orders.searchorder.SearchOrderResponse
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.Resources
import kotlinx.coroutines.launch

class SearchOrderViewModel(val repository: Repository,val context: Context):ViewModel(){

    var resultSearchOrder = MutableLiveData<Resources<SearchOrderResponse>>()

    fun getSearchOrder(body:RequestBodies.SearchOrdersBody) = viewModelScope.launch {

        searchOrder(body)
    }

    private suspend fun searchOrder(body: RequestBodies.SearchOrdersBody) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            resultSearchOrder.postValue(Resources.Loading(loadingMessage = context.resources.getString(R.string.LOADING_PLEASE_WAIT)))
            try {
                val response = repository.searchOrder(body)
                resultSearchOrder.postValue(Resources.Success(response.body()))
            } catch (e: Exception) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
                resultSearchOrder.postValue(Resources.Error(errorMessage = e.message.toString()))
            }
        } else {
            resultSearchOrder.postValue(Resources.NoInternet(context.resources.getString(R.string.NO_INTERNET_CONNECTION)))
        }
    }
}