package com.bws.musclefood.common

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
  // private const val BASE_URL = "http://food-hunt.co.uk/ChillerFiller/API/ChillerFillerMobileWS.svc/"


   /* fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }*/

    private fun retrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
    val retrofitService: ApiInterface by lazy {
        retrofit().create(ApiInterface::class.java)
    }

}