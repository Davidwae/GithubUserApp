package com.waekaizo.githubuserapp.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.waekaizo.githubuserapp.database.FavoriteUser
import com.waekaizo.githubuserapp.repository.FavoriteUserRepository

class FavoriteViewModel(application: Application): ViewModel() {

    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>> = mFavoriteUserRepository.getAllFavoriteUser()
}