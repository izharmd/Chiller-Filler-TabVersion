package com.bws.musclefood.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bws.musclefood.R
import com.bws.musclefood.itemcategory.productlist.InsertUpdateCartResponse
import com.bws.musclefood.network.NetworkUtils
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.urils.Resources
import kotlinx.coroutines.launch
import org.json.JSONArray

class InsertUpdateCartViewModel(val repository: Repository, val context: Context) : ViewModel() {


    var resultInsertUpdate = MutableLiveData<Resources<String>>()

    fun insertUpdateCart(body: JSONArray) = viewModelScope.launch {
        insetUpdate(body)
    }


    private suspend fun insetUpdate(body: JSONArray) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            resultInsertUpdate.postValue(
                Resources.Loading(
                    loadingMessage = context.resources.getString(
                        R.string.LOADING_PLEASE_WAIT
                    )
                )
            )

            try {
                val response = repository.insertUpdate(body)

                var code = response.code()

                if(code == 200){
                    resultInsertUpdate.postValue(Resources.Success("Item update successfully"))
                }else{
                    resultInsertUpdate.postValue(Resources.Success("Error"))
                }


            } catch (e: Exception) {
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_LONG).show()
                resultInsertUpdate.postValue(Resources.Error(errorMessage = e.message.toString()))
            }

        } else {
            resultInsertUpdate.postValue(Resources.NoInternet(context.resources.getString(R.string.NO_INTERNET_CONNECTION)))
        }
    }
}