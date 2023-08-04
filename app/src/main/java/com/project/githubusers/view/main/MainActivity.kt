package com.project.githubusers.view.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.project.githubusers.utils.Result
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.githubusers.R
import com.project.githubusers.data.remote.ApiConfig
import com.project.githubusers.databinding.ActivityMainBinding
import com.project.githubusers.utils.Constants.KEY_USERS
import com.project.githubusers.utils.Constants.checkConnectionToInternet
import com.project.githubusers.utils.Event
import com.project.githubusers.utils.SettingsPreferences
import com.project.githubusers.view.adapter.UsersAdapter
import com.project.githubusers.view.detail.DetailActivity
import com.project.githubusers.view.favorite.FavoriteActivity
import com.project.githubusers.view.settings.SettingsActivity
import com.project.githubusers.view.viewmodelfactory.MainViewModelFactory
import com.project.githubusers.view.viewmodelfactory.SettingsViewModelFactory
import com.project.githubusers.view.viewmodels.MainViewModel
import com.project.githubusers.view.viewmodels.SettingsViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel> {
        MainViewModelFactory(
            ApiConfig.getApiService()
        )
    }

    private val settingsViewModel by viewModels<SettingsViewModel> {
        SettingsViewModelFactory(
            SettingsPreferences.getSettingsInstance(dataStore)
        )
    }

    private val usersAdapter = UsersAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeSettings()
        observeViewModel()

        setupView()
    }

    private fun setupView() {
        usersAdapter.onUserlick = { username ->
            if (checkConnectionToInternet(this@MainActivity)) {
                val iDetail = Intent(this@MainActivity, DetailActivity::class.java)
                iDetail.putExtra(KEY_USERS, username)
                startActivity(iDetail)
            } else {
                showToast("No internet!")
            }
        }

        binding.apply {
            svUsers.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(keyword: String?): Boolean {
                    searchUser(keyword)
                    return true
                }

                override fun onQueryTextChange(keyword: String?): Boolean {
                    return false
                }
            })

            rvUsers.apply {
                adapter = usersAdapter
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
    }

    private fun searchUser(keyword: String?) {
        if (!keyword.isNullOrEmpty()) {
            mainViewModel.getSearchedUser(keyword).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            mainViewModel.isLoading.postValue(true)
                        }
                        is Result.Success -> {
                            mainViewModel.isLoading.postValue(false)
                            mainViewModel.listSearchedUser.postValue(result.data)
                        }
                        is Result.Error -> {
                            mainViewModel.isLoading.postValue(false)
                            mainViewModel.toastText.postValue(Event(result.error))
                        }
                    }
                }
            }
        }
    }

    private fun observeViewModel() {
        mainViewModel.listSearchedUser.observe(this) { accountList ->
            usersAdapter.setData(accountList.items)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.toastText.observe(this) {
            it.getContentIfNotHandled()?.let { message ->
                showToast(message)
            }
        }
    }

    private fun observeSettings() {
        settingsViewModel.getThemeSettings().observe(this) { isDarkMode ->
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_fav -> startActivity(Intent(this, FavoriteActivity::class.java))
            R.id.menu_settings -> startActivity(Intent(this, SettingsActivity::class.java))
        }

        return super.onOptionsItemSelected(item)
    }
}