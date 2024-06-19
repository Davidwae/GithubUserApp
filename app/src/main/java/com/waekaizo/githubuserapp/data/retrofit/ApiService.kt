package com.waekaizo.githubuserapp.data.retrofit

import com.waekaizo.githubuserapp.BuildConfig
import com.waekaizo.githubuserapp.data.response.DetailUserResponse
import com.waekaizo.githubuserapp.data.response.ItemsItem
import com.waekaizo.githubuserapp.data.response.ResponseUsers
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    fun getSearchUsers(
        @Query("q") query: String,
        @Header("Authorization") token: String = BuildConfig.KEY
    ): Call<ResponseUsers>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String,
        @Header("Authorization") token: String = BuildConfig.KEY
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String,
        @Header("Authorization") token: String = BuildConfig.KEY
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String,
        @Header("Authorization") token: String = BuildConfig.KEY
    ): Call<List<ItemsItem>>
}