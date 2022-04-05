package com.bws.musclefood.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bws.musclefood.viewmodels.LoginViewModel
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.viewmodels.CategoryViewModel
import com.bws.musclefood.viewmodels.ProductListViewModel


class FactoryProvider(private val repository: Repository, val context: Context) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository, context) as T
        }

        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            return CategoryViewModel(repository, context) as T
        }

        if (modelClass.isAssignableFrom(ProductListViewModel::class.java)) {
            return ProductListViewModel(repository, context) as T
        }

        /*  if (modelClass.isAssignableFrom(CartListViewModel::class.java)) {
             return CartListViewModel(repository, context) as T
         }*/
        throw IllegalArgumentException("Unknown class name")
    }
}