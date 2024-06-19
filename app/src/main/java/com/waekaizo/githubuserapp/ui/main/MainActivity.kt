package com.waekaizo.githubuserapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.waekaizo.githubuserapp.R
import com.waekaizo.githubuserapp.data.response.ItemsItem
import com.waekaizo.githubuserapp.databinding.ActivityMainBinding
import com.waekaizo.githubuserapp.ui.ListUserAdapter
import com.waekaizo.githubuserapp.ui.favorite.FavoriteUserActivity
import com.waekaizo.githubuserapp.ui.theme.SettingPreferences
import com.waekaizo.githubuserapp.ui.theme.SwitchThemeActivity
import com.waekaizo.githubuserapp.ui.theme.ThemeViewModel
import com.waekaizo.githubuserapp.ui.theme.ThemeViewModelFactory
import com.waekaizo.githubuserapp.ui.theme.dataStore

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rvSearchuser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvSearchuser.addItemDecoration(itemDecoration)

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)
        mainViewModel.listUser.observe(this) { listUser ->
            setUsersData(listUser)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener{ _, _, _ ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    mainViewModel.findUsers(searchView.text.toString())
                    false
                }
        }

        binding.favoriteActivity.setOnClickListener{
            val intentFavorite = Intent(this@MainActivity, FavoriteUserActivity::class.java)
            startActivity(intentFavorite)
        }

        val pref = SettingPreferences.getInstance(application.dataStore)
        val themeViewModel = ViewModelProvider(this, ThemeViewModelFactory(pref)).get(ThemeViewModel::class.java)
        themeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.nightMode.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.baseline_mode_night_24_yellow))
                binding.favoriteActivity.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.baseline_favorite_24_red))
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.nightMode.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.baseline_mode_night_24))
                binding.favoriteActivity.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.baseline_favorite_24))
            }
        }

        binding.nightMode.setOnClickListener{
            val intentSwitchTheme = Intent(this@MainActivity, SwitchThemeActivity::class.java)
            startActivity(intentSwitchTheme)
        }
    }


    private fun setUsersData(users: List<ItemsItem>) {
        val adapter = ListUserAdapter()
        adapter.submitList(users)
        binding.rvSearchuser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}