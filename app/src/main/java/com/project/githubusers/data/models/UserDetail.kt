package com.project.githubusers.data.models

import com.google.gson.annotations.SerializedName

data class UserDetail(

    @field:SerializedName("login")
    val login: String?,

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String?,

    @field:SerializedName("followers")
    val followers: String?,

    @field:SerializedName("following")
    val following: String?,

    @field:SerializedName("company")
    val company: String?,

    @field:SerializedName("public_repos")
    val publicRepo: String?,

    @field:SerializedName("location")
    val location: String?,

    @field:SerializedName("followers_url")
    val followersUrl: String?,

    @field:SerializedName("following_url")
    val followingUrl: String?

)