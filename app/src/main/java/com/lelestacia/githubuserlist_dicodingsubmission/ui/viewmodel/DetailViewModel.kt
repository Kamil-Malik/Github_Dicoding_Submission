package com.lelestacia.githubuserlist_dicodingsubmission.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lelestacia.githubuserlist_dicodingsubmission.data.api.ApiConfig
import com.lelestacia.githubuserlist_dicodingsubmission.data.model.GetUserFollowNetworkResponse
import com.lelestacia.githubuserlist_dicodingsubmission.data.model.GetUserFollowNetworkResponseItem
import com.lelestacia.githubuserlist_dicodingsubmission.data.model.GetUserNetworkResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val showLoading: LiveData<Boolean> = _showLoading

    private val _user: MutableLiveData<GetUserNetworkResponse> = MutableLiveData()
    val user: LiveData<GetUserNetworkResponse> = _user

    private val _following: MutableLiveData<List<GetUserFollowNetworkResponseItem>> =
        MutableLiveData()
    val following: LiveData<List<GetUserFollowNetworkResponseItem>> = _following

    private val _followers: MutableLiveData<List<GetUserFollowNetworkResponseItem>> =
        MutableLiveData()
    val followers: LiveData<List<GetUserFollowNetworkResponseItem>> = _followers

    private val _emptyFollowers: MutableLiveData<Boolean> = MutableLiveData(false)
    val emptyFollowers: LiveData<Boolean> = _emptyFollowers

    private val _emptyFollowing: MutableLiveData<Boolean> = MutableLiveData(false)
    val emptyFollowing: LiveData<Boolean> = _emptyFollowing

    private val _failureFollowers: MutableLiveData<Boolean> = MutableLiveData(false)
    val failureFollowers: LiveData<Boolean> = _failureFollowers

    private val _failureFollowing: MutableLiveData<Boolean> = MutableLiveData(false)
    val failureFollowing: LiveData<Boolean> = _failureFollowing

    fun getUserByUsername(username: String) {
        _showLoading.postValue(true)
        ApiConfig.getApiService().getUserByUsername(username)
            .enqueue(object : Callback<GetUserNetworkResponse> {
                override fun onResponse(
                    call: Call<GetUserNetworkResponse>,
                    response: Response<GetUserNetworkResponse>
                ) {
                    _showLoading.postValue(false)
                    if (response.isSuccessful && response.body() != null) {
                        _user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<GetUserNetworkResponse>, t: Throwable) {
                    _showLoading.postValue(false)
                }
            })
    }

    fun getFollowersByUsername(username: String) {
        if (_emptyFollowers.value!!) _emptyFollowers.postValue(false)
        if (_failureFollowers.value!!) _failureFollowers.postValue(false)
        ApiConfig.getApiService().getFollowersByUsername(username)
            .enqueue(object : Callback<GetUserFollowNetworkResponse> {
                override fun onResponse(
                    call: Call<GetUserFollowNetworkResponse>,
                    response: Response<GetUserFollowNetworkResponse>
                ) {
                    if (response.body()!!.isNotEmpty())
                        _followers.postValue(response.body())
                    else _emptyFollowers.postValue(true)
                }

                override fun onFailure(call: Call<GetUserFollowNetworkResponse>, t: Throwable) {
                    _failureFollowers.postValue(true)
                    return
                }
            })
    }

    fun getFollowingByUsername(username: String) {
        if (_emptyFollowing.value!!) _emptyFollowing.postValue(false)
        if (_failureFollowing.value!!) _failureFollowing.postValue(false)
        ApiConfig.getApiService().getFollowingByUsername(username)
            .enqueue(object : Callback<GetUserFollowNetworkResponse> {
                override fun onResponse(
                    call: Call<GetUserFollowNetworkResponse>,
                    response: Response<GetUserFollowNetworkResponse>
                ) {
                    if (response.body()!!.isNotEmpty())
                        _following.postValue(response.body())
                    else _emptyFollowing.postValue(true)
                }

                override fun onFailure(call: Call<GetUserFollowNetworkResponse>, t: Throwable) {
                    _failureFollowing.postValue(true)
                    return
                }
            })
    }
}