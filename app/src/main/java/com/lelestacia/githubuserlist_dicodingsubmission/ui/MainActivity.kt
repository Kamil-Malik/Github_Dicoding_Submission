package com.lelestacia.githubuserlist_dicodingsubmission.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lelestacia.githubuserlist_dicodingsubmission.R
import com.lelestacia.githubuserlist_dicodingsubmission.data.User
import com.lelestacia.githubuserlist_dicodingsubmission.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val userList = arrayListOf<User>()

    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fullname = resources.getStringArray(R.array.fullname)
        val username = resources.getStringArray(R.array.username)
        val location = resources.getStringArray(R.array.location)
        val follower = resources.getStringArray(R.array.follower)
        val following = resources.getStringArray(R.array.following)
        val picture = resources.obtainTypedArray(R.array.profile_picture)

        for (i in fullname.indices) {
            userList.add(
                User(
                    username = username[i],
                    fullname = fullname[i],
                    location = location[i],
                    follower = follower[i],
                    following = following[i],
                    picture = picture.getResourceId(i, -1)
                )
            )
        }

        binding.rvGithubUserList.setHasFixedSize(true)
        binding.rvGithubUserList.adapter = UserlistAdapter(userList)
    }
}