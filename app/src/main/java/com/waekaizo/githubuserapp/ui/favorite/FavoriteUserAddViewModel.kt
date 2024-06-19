package com.waekaizo.githubuserapp.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.waekaizo.githubuserapp.database.FavoriteUser
import com.waekaizo.githubuserapp.repository.FavoriteUserRepository

class FavoriteUserAddViewModel(application: Application): ViewModel() {
    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)
    fun insert(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.insert(favoriteUser)
    }
    fun delete(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.delete(favoriteUser)
    }
    fun getFavoriteUserByUsername(favoriteUser: String): LiveData<FavoriteUser> = mFavoriteUserRepository.getFavoriteUserByUsername(favoriteUser)
}