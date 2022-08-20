package com.lelestacia.githubuserlist_dicodingsubmission.ui.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.lelestacia.githubuserlist_dicodingsubmission.R
import com.lelestacia.githubuserlist_dicodingsubmission.databinding.ActivityMainBinding
import com.lelestacia.githubuserlist_dicodingsubmission.ui.adapter.UserListAdapter
import com.lelestacia.githubuserlist_dicodingsubmission.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.subtitle = resources.getString(R.string.dicoding_submission)
        subscribe()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.searchbar_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchUserByName(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    private fun subscribe() {
        viewModel.userList.observe(this) { userList ->
            val adapter = UserListAdapter()
            binding.rvGithubUserList.adapter = adapter
            binding.rvGithubUserList.layoutManager = LinearLayoutManager(this)
            adapter.submitList(userList)
        }

        viewModel.showLoading.observe(this) {
            binding.progressCircular.apply {
                progressCircle.isVisible = it
                tvLoading.isVisible = it
            }
        }

        viewModel.showError.observe(this) {
            binding.tvErrorMessage.isVisible = it
        }

        viewModel.showDefault.observe(this) {
            binding.tvDefault.isVisible = it
        }

        viewModel.totalCount.observe(this) {
            val found = resources.getString(R.string.ditemukan)
            val user = resources.getString(R.string.user)
            val text = "$found $it $user"
            Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG).show()
        }

        viewModel.failure.observe(this) {
            binding.tvFailure.isVisible = it
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}