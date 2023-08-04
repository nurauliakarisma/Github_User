package com.project.githubusers.data.local.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.project.githubusers.utils.Result
import com.project.githubusers.data.models.GithubUsers
import com.project.githubusers.data.remote.ApiService

class UserRepository(
    private val apiService: ApiService,
) {
    fun getSearchedUsers(keyword: String): LiveData<Result<GithubUsers>> = liveData {
        emit(Result.Loading)
        try {
            val searchResponse = apiService.getSearchedUsers(keyword)
            emit(Result.Success(searchResponse))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Error(e.message.toString()))
        }
    }
}