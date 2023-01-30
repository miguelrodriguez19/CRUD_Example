package com.utad.crudexample.users

class UsersResponse : ArrayList<UsersResponse.User>(){
    data class User(
        val id: Int?,
        val name: String?,
        val birthdate: String?
    ):java.io.Serializable
}