package com.project.githubusers.view.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.project.githubusers.R
import com.project.githubusers.data.models.FavoriteEntity
import com.project.githubusers.data.models.UserDetail
import com.project.githubusers.databinding.ActivityDetailBinding
import com.project.githubusers.utils.Constants.IS_FAVORITE
import com.project.githubusers.utils.Constants.KEY_USERS
import com.project.githubusers.utils.Constants.TAB_LAYOUT_TITLES
import com.project.githubusers.view.adapter.SectionPagerAdapter
import com.project.githubusers.view.viewmodelfactory.DetailViewModelFactory
import com.project.githubusers.view.viewmodelfactory.FavoriteViewModelFactory
import com.project.githubusers.view.viewmodels.DetailViewModel
import com.project.githubusers.view.viewmodels.FavoriteViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val userId by lazy { intent.getStringExtra(KEY_USERS) }
    private val isFromFav by lazy { intent.getBooleanExtra(IS_FAVORITE, false) }

    val detailViewModel by viewModels<DetailViewModel> {
        DetailViewModelFactory(
            userId!!
        )
    }

    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModelFactory(
            this.application
        )
    }

    private var isFav = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeAll()
        setTabLayout()
    }

    private fun observeAll() {
        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.toastText.observe(this) {
            it.getContentIfNotHandled()?.let { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }

        detailViewModel.detailUser.observe(this) { userDetail ->
            if (userDetail != null) {
                setDetails(userDetail)
            }
        }
    }

    private fun setDetails(userDetail: UserDetail) {
        Glide.with(this)
            .load(userDetail.avatarUrl)
            .circleCrop()
            .into(binding.imgUserDetail)

        binding.apply {
            tvName.text = userDetail.name
            tvUsername.text = userDetail.login
            tvRepo.text = userDetail.publicRepo ?: "0"
            tvFollowers.text = userDetail.followers ?: "0"
            tvFollowing.text = userDetail.following ?: "0"
            tvLocation.text =
                StringBuilder("Location : ${userDetail.location ?: "-"}")
            tvCompany.text =
                StringBuilder("Company : ${userDetail.company ?: "-"}")
        }

        observeFavorite()
        setFabListener(userDetail)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun observeFavorite() {
        favoriteViewModel.checkFavItem(userId!!).observe(this) { isFavorite ->
            isFav = isFavorite.isNotEmpty()

            if (isFav) {
                binding.fabFavorite.setImageDrawable(getDrawable(R.drawable.ic_favorite))
            } else {
                binding.fabFavorite.setImageDrawable(getDrawable(R.drawable.ic_favorite_outline))
            }
        }
    }

    private fun setFabListener(userDetail: UserDetail) {
        binding.fabFavorite.setOnClickListener {
            if (isFav) {
                favoriteViewModel.deleteFavItem(userId!!)
                showToast("Deleted from Favorite!")
            } else {
                favoriteViewModel.insertFavItem(
                    FavoriteEntity(
                        login = userDetail.login,
                        avatarUrl = userDetail.avatarUrl,
                        type = userDetail.type
                    )
                )
                showToast("Added to Favorite!")
            }
        }
    }

    private fun setTabLayout() {
        val sectionPagerAdapter = SectionPagerAdapter(this)
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.layoutTab, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_LAYOUT_TITLES[position])
        }.attach()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressbar.isVisible = isLoading
        binding.fabFavorite.isVisible = !isLoading
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (isFromFav) {
            val intent = Intent()
            setResult(RESULT_OK, intent)
            finish()
        }
        finish()
    }
}