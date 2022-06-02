package com.bws.musclefood.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bws.musclefood.R
import com.bws.musclefood.network.NetworkUtils
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.orders.reorder.ReorderResponse
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.Resources
import kotlinx.coroutines.launch

class ReorderViewModel(val repository: Repository,val context:Context):ViewModel() {

    val resultReroder = MutableLiveData<Resources<ReorderResponse>>()

    fun getReorder(boby:RequestBodies.ReorderBody) = viewModelScope.launch {
        callReorder(boby)
    }
    suspend fun callReorder(body: RequestBodies.ReorderBody) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            resultReroder.postValue(
                Resources.Loading(
                    loadingMessage = context.resources.getString(
                        R.string.LOADING_PLEASE_WAIT
                    )
                )
            )
            try {
                val response = repository.reorder(body)
                resultReroder.postValue(Resources.Success(response.body()))
            } catch (e: Exception) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
                resultReroder.postValue(Resources.Error(errorMessage = e.message.toString()))
            }
        } else {
            resultReroder.postValue(Resources.NoInternet(context.resources.getString(R.string.NO_INTERNET_CONNECTION)))
        }
    }

}