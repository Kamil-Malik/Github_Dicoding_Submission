package com.lelestacia.githubuserlist_dicodingsubmission.data.model

import com.google.gson.annotations.SerializedName

data class SearchUserNetworkResponse(

    @field:SerializedName("total_count")
    val totalCount: Int? = null,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean? = null,

    @field:SerializedName("items")
    val items: List<SearchUserItem?>? = null
)