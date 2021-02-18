package com.artsman.elderly.auth.user_auth_api

import com.artsman.elderly.GenericData
import com.artsman.elderly.auth.AuthUser
import com.artsman.elderly.core.getRetrofitInstance
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

class UserAuthApi(private val retrofit: Retrofit? = getRetrofitInstance()) {
    val api=retrofit?.create(IUserAuthApi::class.java)

    fun userAuth(username: String, password: String): Call<AuthUser>? {
        return api?.userAuth(admin = username ?: "", password = password?: "")
    }




    interface IUserAuthApi{
        @GET("/sample/authuser")
        fun userAuth(@Query("username")admin:String,
                     @Query("pass")password:String
        ): Call<AuthUser>
    }
}

