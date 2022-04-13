package com.bws.musclefood.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bws.musclefood.R
import com.bws.musclefood.network.NetworkUtils
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.signup.RegistrationResponse
import com.bws.musclefood.utils.Resources
import kotlinx.coroutines.launch

class RegistrationViewModel(val repository: Repository,val context: Context):ViewModel() {

    var resultRegistraion = MutableLiveData<Resources<RegistrationResponse>>()
    fun getRegistrationDetails(body:RequestBodies.RegistrationDetailsBody) = viewModelScope.launch {
        registration(body)
    }
    private suspend fun registration(loginBody: RequestBodies.RegistrationDetailsBody) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            resultRegistraion.postValue(Resources.Loading(loadingMessage = context.resources.getString(R.string.LOADING_PLEASE_WAIT)))
            try {
                val response = repository.registrationDetails(loginBody)
                resultRegistraion.postValue(Resources.Success(response.body()))
            } catch (e: Exception) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
                resultRegistraion.postValue(Resources.Error(errorMessage = e.message.toString()))
            }
        } else {
            resultRegistraion.postValue(Resources.NoInternet(context.resources.getString(R.string.NO_INTERNET_CONNECTION)))
        }
    }
}