package com.lelestacia.githubuserlist_dicodingsubmission.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.lelestacia.githubuserlist_dicodingsubmission.R
import com.lelestacia.githubuserlist_dicodingsubmission.data.User
import com.lelestacia.githubuserlist_dicodingsubmission.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private const val BASE_URL = "https://github.com/"
    }

    private lateinit var binding : ActivityDetailBinding
    private lateinit var user : User

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getParcelableExtra<User>(UserlistAdapter.USER_KEY)!!

        binding.apply {
            tvFullName.text = user.fullname
            tvUsername.text = user.username
            tvLocation.text = user.location
            tvFollowers.text = "${user.follower} followers"
            tvFollowing.text = "- ${user.following} following"
            btnOpen.setOnClickListener(this@DetailActivity)
            btnShare.setOnClickListener(this@DetailActivity)
        }

        Glide.with(this).load(user.picture).into(binding.ivProfilePicture)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onClick(v : View?) {
        when (v!!.id) {
            R.id.btnOpen -> startActivity(Intent(Intent.ACTION_VIEW,
                Uri.parse("$BASE_URL${user.username}")))
            R.id.btnShare -> {
                startActivity(
                    Intent(Intent.ACTION_SEND).also {
                        it.putExtra(Intent.EXTRA_TEXT, "Link Profile : $BASE_URL${user.username}")
                        it.type="text/plain"
                    })
            }
        }
    }
}