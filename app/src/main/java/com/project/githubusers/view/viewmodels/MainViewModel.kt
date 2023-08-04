package com.project.githubusers.view.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.githubusers.data.local.repo.UserRepository
import com.project.githubusers.data.models.GithubUsers
import com.project.githubusers.utils.Event

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {
    val isLoading = MutableLiveData(false)
    val listSearchedUser = MutableLiveData<GithubUsers>()
    val toastText = MutableLiveData<Event<String>>()

    fun getSearchedUser(keyword: String) = userRepository.getSearchedUsers(keyword)
}