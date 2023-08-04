package com.project.githubusers.data.local.repo

import android.app.Application
import androidx.lifecycle.LiveData
import com.project.githubusers.data.local.db.FavoriteDao
import com.project.githubusers.data.local.db.FavoriteRoomDatabase
import com.project.githubusers.data.models.FavoriteEntity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val favoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabaseInstance(application)
        favoriteDao = db.favDao()
    }

    fun getAllFavItem(): LiveData<List<FavoriteEntity>> = favoriteDao.getAllFavItem()

    fun checkFavItem(username: String): LiveData<List<FavoriteEntity>> =
        favoriteDao.checkFavItem(username)

    fun insertFavItem(favorite: FavoriteEntity) {
        executorService.execute { favoriteDao.insertFavItem(favorite) }
    }

    fun deleteFavItem(username: String) {
        executorService.execute { favoriteDao.deleteFavItem(username) }
    }
}