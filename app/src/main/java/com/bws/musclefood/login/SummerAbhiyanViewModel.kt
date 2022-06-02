package com.bws.musclefood.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bws.musclefood.R
import com.bws.musclefood.login.tbl.ResponseData
import com.bws.musclefood.network.NetworkUtils
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.Resources
import kotlinx.coroutines.launch

class SummerAbhiyanViewModel(val repository: Repository, val context: Context):ViewModel() {
    val resultSummerAbiyan = MutableLiveData<Resources<ResponseData>>()

    fun loginUser(email:String,pass:String) = viewModelScope.launch {
        login(email,pass)
    }

    private suspend fun login(email: String,pass: String) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            resultSummerAbiyan.postValue(Resources.Loading(loadingMessage = context.resources.getString(R.string.LOADING_PLEASE_WAIT)))
            try {
                val response = repository.userLogin(email,pass)
                resultSummerAbiyan.postValue(Resources.Success(response.body()))
            } catch (e: Exception) {
              //  Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
                resultSummerAbiyan.postValue(Resources.Error(errorMessage = e.message.toString()))
            }
        } else {
            resultSummerAbiyan.postValue(Resources.NoInternet(context.resources.getString(R.string.NO_INTERNET_CONNECTION)))
        }
    }

    fun loginDta(string: String):String{


        return string
    }
}