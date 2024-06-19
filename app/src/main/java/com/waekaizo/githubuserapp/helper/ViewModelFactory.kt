package com.waekaizo.githubuserapp.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.waekaizo.githubuserapp.ui.favorite.FavoriteUserAddViewModel
import com.waekaizo.githubuserapp.ui.favorite.FavoriteViewModel

class ViewModelFactory private constructor(private val mApplication: Application): ViewModelProvider.NewInstanceFactory(){
    companion object {
        @Volatile
        private var INSTACE: ViewModelFactory? = null

        @JvmStatic
        fun getInstace(application: Application): ViewModelFactory {
            if (INSTACE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTACE = ViewModelFactory(application)
                }
            }
            return INSTACE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(FavoriteUserAddViewModel::class.java)) {
            return FavoriteUserAddViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}