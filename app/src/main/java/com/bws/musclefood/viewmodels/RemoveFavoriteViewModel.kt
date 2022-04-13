package com.bws.musclefood.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bws.musclefood.R
import com.bws.musclefood.itemcategory.productlist.RemoveFavoriteResponse
import com.bws.musclefood.network.NetworkUtils
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.Resources
import kotlinx.coroutines.launch

class RemoveFavoriteViewModel(val repository: Repository,val context: Context):ViewModel() {

    var resultRemoveRemoveFavorite = MutableLiveData<Resources<RemoveFavoriteResponse>>()

    fun getRemoveFavorite(body:RequestBodies.RemoveFavoriteProductBody)=viewModelScope.launch {

        getRemoveFav(body)
    }

    suspend fun getRemoveFav(body: RequestBodies.RemoveFavoriteProductBody){

        if (NetworkUtils.isNetworkAvailable(context)) {
            resultRemoveRemoveFavorite.postValue(
                Resources.Loading(
                    loadingMessage = context.resources.getString(
                        R.string.LOADING_PLEASE_WAIT
                    )
                )
            )
            try {
                val response = repository.removeFavourite(body)
                resultRemoveRemoveFavorite.postValue(Resources.Success(response.body()))
            } catch (e: Exception) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
                resultRemoveRemoveFavorite.postValue(Resources.Error(errorMessage = e.message.toString()))
            }
        } else {
            resultRemoveRemoveFavorite.postValue(Resources.NoInternet(context.resources.getString(R.string.NO_INTERNET_CONNECTION)))
        }

    }

}