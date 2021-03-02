package com.artsman.elderly.auth.user_auth_api

import com.artsman.elderly.GenericData
import com.artsman.elderly.auth.AuthUser
import com.artsman.elderly.core.getRetrofitInstance
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

class UserAuthApi(private val retrofit: Retrofit? = getRetrofitInstance()) {
    val api = retrofit?.create(IUserAuthApi::class.java)

    fun userAuth(username: String, password: String): Call<AuthUser>? {
        val creds=Creds(username,password)
        return api?.userAuthPost(creds)
//        return api?.userAuth(admin = username ?: "", password = password ?: "")
    }

    data class Creds(val username: String,val pass:String)
    interface IUserAuthApi {
        @Deprecated("use post method")
        @GET("/sample/authuser")
        fun userAuth(
            @Query("username") admin: String,
            @Query("pass") password: String
        ): Call<AuthUser>

        @POST("/sample/authuser")
        fun userAuthPost(@Body credentials: Creds): Call<AuthUser>
    }
}

