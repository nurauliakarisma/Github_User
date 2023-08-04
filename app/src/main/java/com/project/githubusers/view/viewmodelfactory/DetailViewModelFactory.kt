package com.project.githubusers.view.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.githubusers.data.local.repo.UserRepository
import com.project.githubusers.data.remote.ApiService
import com.project.githubusers.view.viewmodels.DetailViewModel

class DetailViewModelFactory constructor(
    private val username: String,
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(username) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}