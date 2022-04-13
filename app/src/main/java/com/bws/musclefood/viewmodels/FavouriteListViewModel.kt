package com.bws.musclefood.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bws.musclefood.R
import com.bws.musclefood.favourites.FavouritesListResponse
import com.bws.musclefood.network.NetworkUtils
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.Resources
import kotlinx.coroutines.launch

class FavouriteListViewModel(val repository: Repository,val context: Context):ViewModel() {


    var resultFavouriteList = MutableLiveData<Resources<FavouritesListResponse>>()

    fun getFavouriteList(body:RequestBodies.FavouriteListBody) = viewModelScope.launch {

        favouriteList(body)
    }

    suspend fun favouriteList(body: RequestBodies.FavouriteListBody) {

        if (NetworkUtils.isNetworkAvailable(context)) {
            resultFavouriteList.postValue(
                Resources.Loading(
                    loadingMessage = context.resources.getString(
                        R.string.LOADING_PLEASE_WAIT
                    )
                )
            )
            try {
                val response = repository.favouriteList(body)
                resultFavouriteList.postValue(Resources.Success(response.body()))
            } catch (e: Exception) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
                resultFavouriteList.postValue(Resources.Error(errorMessage = e.message.toString()))
            }
        } else {
            resultFavouriteList.postValue(Resources.NoInternet(context.resources.getString(R.string.NO_INTERNET_CONNECTION)))
        }
    }
}