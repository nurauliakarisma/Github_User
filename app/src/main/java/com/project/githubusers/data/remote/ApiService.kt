package com.project.githubusers.data.remote

import com.project.githubusers.data.models.GithubUsers
import com.project.githubusers.data.models.ItemsItem
import com.project.githubusers.data.models.UserDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun getSearchedUsers(
        @Query("q") username: String,
    ): GithubUsers

    @GET("users/{login}")
    suspend fun getDetailUser(
        @Path("login") login: String,
    ): UserDetail

    @GET("users/{login}/{type}")
    suspend fun getUserFollows(
        @Path("login") login: String,
        @Path("type") type: String,
    ): List<ItemsItem>
}