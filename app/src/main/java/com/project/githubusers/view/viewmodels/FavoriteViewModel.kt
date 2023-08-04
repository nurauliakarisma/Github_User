package com.project.githubusers.view.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.project.githubusers.data.local.repo.FavoriteRepository
import com.project.githubusers.data.models.FavoriteEntity

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFavItem(): LiveData<List<FavoriteEntity>> = mFavoriteRepository.getAllFavItem()

    fun checkFavItem(username: String): LiveData<List<FavoriteEntity>> {
        return mFavoriteRepository.checkFavItem(username)
    }

    fun insertFavItem(favorite: FavoriteEntity) {
        mFavoriteRepository.insertFavItem(favorite)
    }

    fun deleteFavItem(login: String) {
        mFavoriteRepository.deleteFavItem(login)
    }
}