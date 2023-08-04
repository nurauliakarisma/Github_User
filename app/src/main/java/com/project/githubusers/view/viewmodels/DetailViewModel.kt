package com.project.githubusers.view.viewmodels

import android.content.Context
import androidx.lifecycle.*
import com.project.githubusers.data.local.repo.UserRepository
import com.project.githubusers.data.models.ItemsItem
import com.project.githubusers.data.models.UserDetail
import com.project.githubusers.data.remote.ApiConfig
import com.project.githubusers.utils.Constants.TYPE_FOLLOWERS
import com.project.githubusers.utils.Constants.TYPE_FOLLOWING
import com.project.githubusers.utils.Event
import com.project.githubusers.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailViewModel(
    username: String,
) : ViewModel() {

    private val _isloading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isloading

    private val _detailUser = MutableLiveData<UserDetail>()
    val detailUser: LiveData<UserDetail> = _detailUser

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    private val _listFollower = MutableLiveData<List<ItemsItem>>()
    val listFollower: LiveData<List<ItemsItem>> = _listFollower

    private val _listFollowing = MutableLiveData<List<ItemsItem>>()
    val listFollowing: LiveData<List<ItemsItem>> = _listFollowing

    private val _isLoadingFollower = MutableLiveData<Boolean>()
    val isLoadingFollower: LiveData<Boolean> = _isLoadingFollower

    private val _isLoadingFollowing = MutableLiveData<Boolean>()
    val isLoadingFollowing: LiveData<Boolean> = _isLoadingFollowing

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    init {
        viewModelScope.launch {
            getUserDetail(username)
            getFollowers(username)
            getFollowings(username)
        }
    }

    private fun getUserDetail(username: String) {
        coroutineScope.launch {
            _isloading.value = _isloading.value == null
            val result = ApiConfig.getApiService().getDetailUser(username)
            try {
                _isloading.value = false
                _detailUser.postValue(result)
            } catch (e: Exception) {
                _isloading.value = false
                _toastText.value = Event(e.message.toString())
            }
        }
    }

    private suspend fun getFollowers(username: String) {
        coroutineScope.launch {
            _isLoadingFollower.value = _isLoadingFollower.value == null
            val result = ApiConfig.getApiService().getUserFollows(username, TYPE_FOLLOWERS)
            try {
                _isLoadingFollower.value = false
                _listFollower.postValue(result)
            } catch (e: Exception) {
                _isLoadingFollower.value = false
            }
        }
    }

    private suspend fun getFollowings(username: String) {
        coroutineScope.launch {
            _isLoadingFollowing.value = _isLoadingFollowing.value == null
            val result = ApiConfig.getApiService().getUserFollows(username, TYPE_FOLLOWING)
            try {
                _isLoadingFollowing.value = false
                _listFollowing.postValue(result)
            } catch (e: Exception) {
                _isLoadingFollowing.value = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}