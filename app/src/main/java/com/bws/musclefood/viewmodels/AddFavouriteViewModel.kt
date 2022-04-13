package com.bws.musclefood.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bws.musclefood.R
import com.bws.musclefood.itemcategory.productlist.ResponseAddFavourite
import com.bws.musclefood.network.NetworkUtils
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.Resources
import kotlinx.coroutines.launch

class AddFavouriteViewModel(val repository: Repository, val context: Context) : ViewModel() {


    var resultAddFavourite = MutableLiveData<Resources<ResponseAddFavourite>>()
    fun getAddFavourite(body: RequestBodies.AddFavouriteListBody) = viewModelScope.launch {
        addFavourite(body)

    }

    suspend fun addFavourite(body: RequestBodies.AddFavouriteListBody) {

        if (NetworkUtils.isNetworkAvailable(context)) {
            resultAddFavourite.postValue(
                Resources.Loading(
                    loadingMessage = context.resources.getString(
                        R.string.LOADING_PLEASE_WAIT
                    )
                )
            )
            try {
                val response = repository.addAddFavourite(body)
                resultAddFavourite.postValue(Resources.Success(response.body()))
            } catch (e: Exception) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
                resultAddFavourite.postValue(Resources.Error(errorMessage = e.message.toString()))
            }
        } else {
            resultAddFavourite.postValue(Resources.NoInternet(context.resources.getString(R.string.NO_INTERNET_CONNECTION)))
        }
    }

}