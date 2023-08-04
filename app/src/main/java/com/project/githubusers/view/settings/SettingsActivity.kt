package com.project.githubusers.view.settings

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.project.githubusers.databinding.ActivitySettingsBinding
import com.project.githubusers.utils.SettingsPreferences
import com.project.githubusers.view.viewmodelfactory.SettingsViewModelFactory
import com.project.githubusers.view.viewmodels.SettingsViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    private val settingsViewModel by viewModels<SettingsViewModel> {
        SettingsViewModelFactory(
            SettingsPreferences.getSettingsInstance(dataStore)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeSettings()
        setListeners()
    }

    private fun observeSettings() {
        settingsViewModel.getThemeSettings().observe(this) { isDarkMode ->
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchDarkTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchDarkTheme.isChecked = false
            }
        }
    }

    private fun setListeners() {
        binding.switchDarkTheme.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.saveThemeSetting(isChecked)
        }
    }
}