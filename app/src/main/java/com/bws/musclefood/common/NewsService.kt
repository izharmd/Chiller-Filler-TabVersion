package com.bws.musclefood.common

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsService {

  //  private const val BASE_URL = " https://www.MYAPI.com/"
    private const val BASE_URL = " https://www.MYAPI.com/"
    private fun retrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(NewsService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
    val retrofitService: ApiInterface by lazy {
        retrofit().create(ApiInterface::class.java)
    }

}