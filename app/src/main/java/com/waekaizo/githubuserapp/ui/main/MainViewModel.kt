package com.waekaizo.githubuserapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.waekaizo.githubuserapp.data.response.ItemsItem
import com.waekaizo.githubuserapp.data.response.ResponseUsers
import com.waekaizo.githubuserapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _listUser = MutableLiveData<List<ItemsItem>>()
    val listUser: LiveData<List<ItemsItem>> = _listUser

    private val _listFollower = MutableLiveData<List<ItemsItem>>()
    val listFollower : LiveData<List<ItemsItem>> = _listFollower

    private val _listFollowing = MutableLiveData<List<ItemsItem>>()
    val listFollowing : LiveData<List<ItemsItem>> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        findUsers("david")
        findFollowUsers("")
    }

    fun findUsers(userId: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getSearchUsers(userId)
        client.enqueue(object : Callback<ResponseUsers> {
            override fun onResponse(
                call: Call<ResponseUsers>,
                response: Response<ResponseUsers>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.postValue(response.body()?.items)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ResponseUsers>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
    //dipisah untuk menghindari bug API mengikuti nilai default
    fun findFollowUsers(userId: String) {
        _isLoading.value = true
        val followers = ApiConfig.getApiService().getFollowers(userId)
        followers.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollower.postValue(response.body())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })

        val following = ApiConfig.getApiService().getFollowing(userId)
        following.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollowing.postValue(response.body())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "MainViewModel"
    }


}