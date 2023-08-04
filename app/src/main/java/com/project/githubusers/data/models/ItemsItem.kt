package com.project.githubusers.data.models

import com.google.gson.annotations.SerializedName

data class ItemsItem(

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("type")
    val type: String
)