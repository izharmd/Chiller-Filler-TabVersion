package com.bws.musclefood.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bws.musclefood.R
import com.bws.musclefood.network.NetworkUtils
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.profile.UserProfileDetailsResponse
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.Resources
import kotlinx.coroutines.launch

class UserProfileDetailsModel(val repository: Repository, val context: Context) : ViewModel() {

    var resultUserProfileDetails = MutableLiveData<Resources<UserProfileDetailsResponse>>()

    fun getUserProfileDetails(body: RequestBodies.GetUserProfileDetailsBody) =
        viewModelScope.launch {

            userProfileDetails(body)
        }


    suspend fun userProfileDetails(body: RequestBodies.GetUserProfileDetailsBody) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            resultUserProfileDetails.postValue(
                Resources.Loading(
                    loadingMessage = context.resources.getString(
                        R.string.LOADING_PLEASE_WAIT
                    )
                )
            )
            try {
                val response = repository.userProfileDetails(body)
                resultUserProfileDetails.postValue(Resources.Success(response.body()))
            } catch (e: Exception) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
                resultUserProfileDetails.postValue(Resources.Error(errorMessage = e.message.toString()))
            }
        } else {
            resultUserProfileDetails.postValue(Resources.NoInternet(context.resources.getString(R.string.NO_INTERNET_CONNECTION)))
        }
    }

}