package com.bws.musclefood.signup

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bws.musclefood.R
import com.bws.musclefood.network.NetworkUtils
import com.bws.musclefood.network.RequestBodies
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.utils.Resources
import kotlinx.coroutines.launch

class ValidateOTPViewModel(val repository: Repository,val context: Context):ViewModel(){


    var resultOTP = MutableLiveData<Resources<ResposeOTPValidate>>()


    fun optValidate(body:RequestBodies.ValidateRegistrationOTP) = viewModelScope.launch {

        callValidateOTP(body)

    }

    suspend fun callValidateOTP(body: RequestBodies.ValidateRegistrationOTP){

        if (NetworkUtils.isNetworkAvailable(context)) {
            resultOTP.postValue(
                Resources.Loading(
                    loadingMessage = context.resources.getString(
                        R.string.LOADING_PLEASE_WAIT
                    )
                )
            )
            try {
                val response = repository.validateOtp(body)
                resultOTP.postValue(Resources.Success(response.body()))
            } catch (e: Exception) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
                resultOTP.postValue(Resources.Error(errorMessage = e.message.toString()))
            }
        } else {
            resultOTP.postValue(Resources.NoInternet(context.resources.getString(R.string.NO_INTERNET_CONNECTION)))
        }


    }
}