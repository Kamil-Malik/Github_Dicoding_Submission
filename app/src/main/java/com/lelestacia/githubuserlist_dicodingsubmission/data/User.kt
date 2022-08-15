package com.lelestacia.githubuserlist_dicodingsubmission.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val fullname: String,
    val username: String,
    val location: String,
    val follower: String,
    val following: String,
    val picture: Int
) : Parcelable
