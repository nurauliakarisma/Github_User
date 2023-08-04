package com.project.githubusers.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.project.githubusers.data.models.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavItem(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE login =:login ")
    fun deleteFavItem(login: String)

    @Query("SELECT * FROM favorites")
    fun getAllFavItem(): LiveData<List<FavoriteEntity>>

    @Query("SELECT * FROM favorites WHERE login =:login")
    fun checkFavItem(login: String): LiveData<List<FavoriteEntity>>
}