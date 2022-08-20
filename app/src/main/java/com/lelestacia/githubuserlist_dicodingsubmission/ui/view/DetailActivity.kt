package com.lelestacia.githubuserlist_dicodingsubmission.ui.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lelestacia.githubuserlist_dicodingsubmission.R
import com.lelestacia.githubuserlist_dicodingsubmission.databinding.ActivityDetailBinding
import com.lelestacia.githubuserlist_dicodingsubmission.ui.adapter.ProfilePagerAdapter
import com.lelestacia.githubuserlist_dicodingsubmission.ui.adapter.UserListAdapter
import com.lelestacia.githubuserlist_dicodingsubmission.ui.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = resources.getString(R.string.user_profile)
        val username = intent.getStringExtra(UserListAdapter.USER_KEY)!!
        val profilePagerAdapter = ProfilePagerAdapter(this)
        val viewPager = binding.viewpager
        viewPager.adapter = profilePagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
        with(viewModel) {
            getUserByUsername(username)
            getFollowersByUsername(username)
            getFollowingByUsername(username)
        }
        subscribe()
        binding.btnOpen.setOnClickListener(this)
        binding.btnShare.setOnClickListener(this)
    }

    private fun subscribe() {
        viewModel.user.observe(this) { user ->
            binding.apply {
                Glide.with(this@DetailActivity).load(user.avatarUrl).into(ivProfilePicture)
                tvUsername.text = user.login

                if (user.name.isNullOrEmpty())
                    tvFullName.text = resources.getString(R.string.unknown)
                else tvFullName.text = user.name

                if (user.location.isNullOrEmpty())
                    tvLocation.text = resources.getString(R.string.unknown)
                else tvLocation.text = user.location

                val followersAndFollowing =
                    "${user.followers} ${resources.getString(R.string.follower)} - " +
                            "${user.following} ${resources.getString(R.string.following)}"
                tvFollowerAndFollowing.text = followersAndFollowing
            }
        }

        viewModel.showLoading.observe(this) {
            binding.progressCircular.progressCircle.isVisible = it
            binding.progressCircular.tvLoading.isVisible = it
        }
    }

    override fun onClick(v: View?) {
        val user = intent.getStringExtra(UserListAdapter.USER_KEY)!!
        when (v!!.id) {
            R.id.btnOpen -> startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("$BASE_URL${user}")
                )
            )
            R.id.btnShare -> {
                startActivity(
                    Intent(Intent.ACTION_SEND).also {
                        it.putExtra(Intent.EXTRA_TEXT, "Link Profile : $BASE_URL${user}")
                        it.type = "text/plain"
                    })
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val BASE_URL = "https://github.com/"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.Follower,
            R.string.Following
        )
    }
}
