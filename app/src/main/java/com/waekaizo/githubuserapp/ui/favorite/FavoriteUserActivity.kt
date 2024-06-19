package com.waekaizo.githubuserapp.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.waekaizo.githubuserapp.databinding.ActivityFavoriteUserBinding
import com.waekaizo.githubuserapp.helper.ViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {

    private var _activityFavoriteUserBinding: ActivityFavoriteUserBinding? = null
    private val binding get() = _activityFavoriteUserBinding

    private lateinit var adapter: FavoriteUseradapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityFavoriteUserBinding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val favoriteViewModel = obtainViewModel(this@FavoriteUserActivity)
        favoriteViewModel.getAllFavoriteUser().observe(this) {favoriteList ->
            if (favoriteList != null) {
                adapter.setListFavoriteUsers(favoriteList)
            }
        }

        adapter = FavoriteUseradapter()

        binding?.rvFavoriteUser?.layoutManager = LinearLayoutManager(this)
        binding?.rvFavoriteUser?.setHasFixedSize(true)
        binding?.rvFavoriteUser?.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstace(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteUserBinding = null
    }
}