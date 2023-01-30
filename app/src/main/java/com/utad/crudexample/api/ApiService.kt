package com.utad.crudexample.api

import com.utad.crudexample.users.UsersResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("User")
    fun getAllUsers(
    ): Call<UsersResponse>

    @GET("User/{id}")
    fun getUserById(
        @Path("id") id:Int
    ): Call<UsersResponse>

    @POST("User/{id}")
    fun createUser(@Body newUser: UsersResponse.User): Call<*>
}
