package com.project.githubusers.view.favorite

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.githubusers.data.models.FavoriteEntity
import com.project.githubusers.databinding.ActivityFavoriteBinding
import com.project.githubusers.utils.Constants.KEY_USERS
import com.project.githubusers.utils.Constants.checkConnectionToInternet
import com.project.githubusers.view.adapter.FavoriteAdapter
import com.project.githubusers.view.detail.DetailActivity
import com.project.githubusers.view.viewmodelfactory.FavoriteViewModelFactory
import com.project.githubusers.view.viewmodels.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModelFactory(
            this.application
        )
    }

    private val favLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            observeFav()
        }
    }

    private val favoriteAdapter = FavoriteAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeFav()
        setView()
    }

    private fun observeFav() {
        favoriteViewModel.getAllFavItem().observe(this) {
            if (it.isEmpty()) {
                binding.rvFavorite.visibility = View.GONE
            } else {
                binding.rvFavorite.visibility = View.VISIBLE
                favoriteAdapter.setFavorite(it as ArrayList<FavoriteEntity>)
            }
        }
    }

    private fun setView() {
        binding.rvFavorite.apply {
            favoriteAdapter.onFavClick = { username ->
                if (checkConnectionToInternet(this@FavoriteActivity)) {
                    val iDetail = Intent(this@FavoriteActivity, DetailActivity::class.java)
                    iDetail.putExtra(KEY_USERS, username)
                    favLauncher.launch(iDetail)
                } else {
                    Toast.makeText(
                        this@FavoriteActivity,
                        "No internet!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
        }
    }
}