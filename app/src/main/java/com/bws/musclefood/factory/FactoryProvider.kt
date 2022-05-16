package com.bws.musclefood.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bws.musclefood.repo.Repository
import com.bws.musclefood.viewmodels.*


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
        if (modelClass.isAssignableFrom(CartListViewModel::class.java)) {
            return CartListViewModel(repository, context) as T
        }
        if (modelClass.isAssignableFrom(InsertUpdateCartViewModel::class.java)) {
            return InsertUpdateCartViewModel(repository, context) as T
        }
        if (modelClass.isAssignableFrom(ProductDetailsViewModel::class.java)) {
            return ProductDetailsViewModel(repository, context) as T
        }
        if (modelClass.isAssignableFrom(RemoveProductViewModel::class.java)) {
            return RemoveProductViewModel(repository, context) as T
        }
        if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
            return RegistrationViewModel(repository, context) as T
        }
        if (modelClass.isAssignableFrom(AddFavouriteViewModel::class.java)) {
            return AddFavouriteViewModel(repository, context) as T
        }
        if (modelClass.isAssignableFrom(RemoveFavoriteViewModel::class.java)) {
            return RemoveFavoriteViewModel(repository, context) as T
        }
        if (modelClass.isAssignableFrom(FavouriteListViewModel::class.java)) {
            return FavouriteListViewModel(repository, context) as T
        }
        if (modelClass.isAssignableFrom(SearchOrderViewModel::class.java)) {
            return SearchOrderViewModel(repository, context) as T
        }
        if (modelClass.isAssignableFrom(UpdateProfileViewModel::class.java)) {
            return UpdateProfileViewModel(repository, context) as T
        }
        if (modelClass.isAssignableFrom(UserProfileDetailsModel::class.java)) {
            return UserProfileDetailsModel(repository, context) as T
        }
        if (modelClass.isAssignableFrom(OrderPlaceViewModel::class.java)) {
            return OrderPlaceViewModel(repository, context) as T
        }
        if (modelClass.isAssignableFrom(DeliveryOptionViewModel::class.java)) {
            return DeliveryOptionViewModel(repository, context) as T
        }

        if (modelClass.isAssignableFrom(AddNewAddressViewModel::class.java)) {
            return AddNewAddressViewModel(repository, context) as T
        }

        if (modelClass.isAssignableFrom(PaymentDetailsViewModel::class.java)) {
            return PaymentDetailsViewModel(repository, context) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}