package com.project.githubusers.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.project.githubusers.R

object Constants {
    val THEME_KEY = booleanPreferencesKey("theme_key")

    const val KEY_USERS = "key_users"

    const val TYPE_FOLLOWERS = "followers"
    const val TYPE_FOLLOWING = "following"
    const val IS_FAVORITE = "is_favorite"

    val TAB_LAYOUT_TITLES = intArrayOf(
        R.string.follower,
        R.string.following
    )

    fun checkConnectionToInternet(context: Context): Boolean {
        val mConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            val mNetworkInfo =
                mConnectivityManager.activeNetworkInfo ?: return false
            return mNetworkInfo.isConnected
        } else {
            val mNetwork = mConnectivityManager.activeNetwork ?: return false
            val mActiveNetwork =
                mConnectivityManager.getNetworkCapabilities(mNetwork) ?: return false

            return when {
                mActiveNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                mActiveNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        }
    }
}