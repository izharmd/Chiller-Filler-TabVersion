package com.bws.musclefood.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bws.musclefood.R
import com.bws.musclefood.login.LoginResponse
import com.bws.musclefood.network.NetworkUtils
import com.bws.musclefood.network.RequestBodies

import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.Resources

import kotlinx.coroutines.launch


class LoginViewModel(val repository: Repository, val context: Context) : ViewModel() {

    val loginResult = MutableLiveData<Resources<LoginResponse>>()

    fun loginUser(loginBody: RequestBodies.LoginBody) = viewModelScope.launch {
        login(loginBody)
    }

    private suspend fun login(loginBody: RequestBodies.LoginBody) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            loginResult.postValue(Resources.Loading(loadingMessage = context.resources.getString(R.string.LOADING_PLEASE_WAIT)))
            try {
                val response = repository.loginUser(loginBody)
                loginResult.postValue(Resources.Success(response.body()))
            } catch (e: Exception) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
                loginResult.postValue(Resources.Error(errorMessage = e.message.toString()))
            }
        } else {
            loginResult.postValue(Resources.NoInternet(context.resources.getString(R.string.NO_INTERNET_CONNECTION)))
        }
    }
}