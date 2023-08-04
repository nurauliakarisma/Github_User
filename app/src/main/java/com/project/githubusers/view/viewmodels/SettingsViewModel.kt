package com.project.githubusers.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.project.githubusers.utils.SettingsPreferences
import kotlinx.coroutines.launch

class SettingsViewModel(private val settingsPreferences: SettingsPreferences) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return settingsPreferences.getThemeSettings().asLiveData()
    }

    fun saveThemeSetting(isAppDarkMode: Boolean) {
        viewModelScope.launch {
            settingsPreferences.saveThemeSettings(isAppDarkMode)
        }
    }
}