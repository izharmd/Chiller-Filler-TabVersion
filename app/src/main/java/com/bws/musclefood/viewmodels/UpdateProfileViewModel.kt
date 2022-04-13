package com.bws.musclefood.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bws.musclefood.R
import com.bws.musclefood.network.NetworkUtils
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.profile.UpdateProfileResponse
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.Resources
import kotlinx.coroutines.launch

class UpdateProfileViewModel(val repository: Repository,val context: Context):ViewModel(){

   var resultUpdateProfile = MutableLiveData<Resources<UpdateProfileResponse>>()

    fun getUpdateProfile(body:RequestBodies.UpdateProfileBody) = viewModelScope.launch {
        updateProfile(body)
    }

   suspend fun updateProfile(body: RequestBodies.UpdateProfileBody){

        if (NetworkUtils.isNetworkAvailable(context)) {
            resultUpdateProfile.postValue(
                Resources.Loading(
                    loadingMessage = context.resources.getString(
                        R.string.LOADING_PLEASE_WAIT
                    )
                )
            )
            try {
                val response = repository.updateProfile(body)
                resultUpdateProfile.postValue(Resources.Success(response.body()))
            } catch (e: Exception) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
                resultUpdateProfile.postValue(Resources.Error(errorMessage = e.message.toString()))
            }
        } else {
            resultUpdateProfile.postValue(Resources.NoInternet(context.resources.getString(R.string.NO_INTERNET_CONNECTION)))
        }
    }
}