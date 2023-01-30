package com.utad.crudexample.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiRest {
    lateinit var service: ApiService
    val URL = "https://hello-world.innocv.com/api/"

    fun initService() {
        val retrofit =
            Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create())
                .build()
        service = retrofit.create(ApiService::class.java)
    }
}
