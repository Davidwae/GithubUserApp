package com.waekaizo.githubuserapp.ui.detailuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.waekaizo.githubuserapp.R
import com.waekaizo.githubuserapp.database.FavoriteUser
import com.waekaizo.githubuserapp.databinding.ActivityDetailUserBinding
import com.waekaizo.githubuserapp.helper.ViewModelFactory
import com.waekaizo.githubuserapp.ui.favorite.FavoriteUserAddViewModel
import com.waekaizo.githubuserapp.ui.SectionsPagerAdapter

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var favoriteUserAddViewModel: FavoriteUserAddViewModel

    private var favoriteUser: FavoriteUser? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)

        val detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            DetailViewModel::class.java)
        if (username != null) {
            detailViewModel.findDetailUser(username)
        }
        detailViewModel.detailUser.observe(this) {
            binding.tvDetailUsername.text = it.login
            binding.tvName.text = it.name
            binding.tvFollowers.text = "${it.followers} Followers"
            binding.tvFollowing.text = "${it.following} Following"
            Glide.with(this@DetailUserActivity)
                .load(it.avatarUrl)
                .into(binding.imgDetailUser)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        //FOR MAKE TAB LAYOUT
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
            sectionsPagerAdapter.username = username.toString()
        }.attach()

        supportActionBar?.elevation = 0f

        //UNTUK FAVORITE USER
        favoriteUserAddViewModel = obtainViewModel(this@DetailUserActivity)
        favoriteUser = FavoriteUser()

        var isEmpty = true
        favoriteUserAddViewModel.getFavoriteUserByUsername(username.toString()).observe(this) {favorite->
            if (favorite != null){
                binding.fabAddFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.baseline_favorite_24))
                isEmpty = false
            } else{
                binding.fabAddFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.baseline_favorite_border_24))
                isEmpty = true
            }
        }

        binding.fabAddFavorite.setOnClickListener {
            detailViewModel.detailUser.observe(this) {
                favoriteUser.let { favoriteUser ->
                    favoriteUser?.username = it.login
                    favoriteUser?.avatarUrl = it.avatarUrl
                }
                if (isEmpty) {
                    favoriteUserAddViewModel.insert(favoriteUser as FavoriteUser)
                    showToast(getString(R.string.added))
                } else{
                    favoriteUserAddViewModel.delete(favoriteUser as FavoriteUser)
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteUserAddViewModel {
        val factory = ViewModelFactory.getInstace(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteUserAddViewModel::class.java)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object{
        const val EXTRA_USERNAME = "extra_username"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

    }
}