package com.lelestacia.githubuserlist_dicodingsubmission.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lelestacia.githubuserlist_dicodingsubmission.data.api.ApiConfig
import com.lelestacia.githubuserlist_dicodingsubmission.data.model.SearchUserItem
import com.lelestacia.githubuserlist_dicodingsubmission.data.model.SearchUserNetworkResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _showDefault: MutableLiveData<Boolean> = MutableLiveData(true)
    val showDefault: LiveData<Boolean> = _showDefault

    private val _showLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val showLoading: LiveData<Boolean> = _showLoading

    private val _showError: MutableLiveData<Boolean> = MutableLiveData(false)
    val showError: LiveData<Boolean> = _showError

    private val _userList: MutableLiveData<List<SearchUserItem>> = MutableLiveData()
    val userList: LiveData<List<SearchUserItem>> = _userList

    private val _totalCount: MutableLiveData<Int> = MutableLiveData()
    val totalCount: LiveData<Int> = _totalCount

    private val _failure: MutableLiveData<Boolean> = MutableLiveData(false)
    val failure: LiveData<Boolean> = _failure

    fun searchUserByName(user: String) {
        _showLoading.postValue(true)
        if (_showError.value!!) _showError.postValue(false)
        if (_showDefault.value!!) _showDefault.postValue(false)
        if (_failure.value!!) _failure.postValue(false)
        _userList.postValue(emptyList())
        ApiConfig.getApiService().searchUserByUsername(user)
            .enqueue(object : Callback<SearchUserNetworkResponse> {
                override fun onResponse(
                    call: Call<SearchUserNetworkResponse>,
                    response: Response<SearchUserNetworkResponse>
                ) {
                    _showLoading.postValue(false)
                    if (response.isSuccessful && response.body()!!.items!!.isNotEmpty()) {
                        _totalCount.postValue(response.body()!!.totalCount as Int)
                        _userList.postValue(response.body()!!.items as List<SearchUserItem>?)
                    } else {
                        _showError.postValue(true)
                    }
                }

                override fun onFailure(call: Call<SearchUserNetworkResponse>, t: Throwable) {
                    _showLoading.postValue(false)
                    _failure.postValue(true)
                }
            })
    }
}