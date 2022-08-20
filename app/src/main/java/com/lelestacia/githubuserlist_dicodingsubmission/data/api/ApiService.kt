package com.lelestacia.githubuserlist_dicodingsubmission.data.api

import com.lelestacia.githubuserlist_dicodingsubmission.data.model.GetUserFollowNetworkResponse
import com.lelestacia.githubuserlist_dicodingsubmission.data.model.GetUserNetworkResponse
import com.lelestacia.githubuserlist_dicodingsubmission.data.model.SearchUserNetworkResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun searchUserByUsername(
        @Query("q") username: String,
        @Query("per_page") page: Int = 100
    ): Call<SearchUserNetworkResponse>

    @GET("users/{username}")
    fun getUserByUsername(
        @Path("username") username: String,
        @Header("Authorization") token: String = APIKEY
    ): Call<GetUserNetworkResponse>

    @GET("users/{username}/followers")
    fun getFollowersByUsername(
        @Path("username") username: String,
        @Header("Authorization") token: String = APIKEY
    ): Call<GetUserFollowNetworkResponse>

    @GET("users/{username}/following")
    fun getFollowingByUsername(
        @Path("username") username: String,
        @Header("Authorization") token: String = APIKEY
    ): Call<GetUserFollowNetworkResponse>

    companion object {
        private const val APIKEY = "token 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"
    }
}