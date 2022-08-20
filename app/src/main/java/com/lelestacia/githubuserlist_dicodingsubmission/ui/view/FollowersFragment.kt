package com.lelestacia.githubuserlist_dicodingsubmission.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lelestacia.githubuserlist_dicodingsubmission.databinding.FragmentFollowersBinding
import com.lelestacia.githubuserlist_dicodingsubmission.ui.adapter.FollowListAdapter
import com.lelestacia.githubuserlist_dicodingsubmission.ui.adapter.UserListAdapter
import com.lelestacia.githubuserlist_dicodingsubmission.ui.viewmodel.DetailViewModel

class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        subscribe()
        return binding.root
    }

    private fun subscribe() {
        val adapter = FollowListAdapter()
        binding.rvFollowers.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowers.adapter = adapter
        viewModel.getFollowersByUsername(requireActivity().intent.getStringExtra(UserListAdapter.USER_KEY)!!)
        viewModel.followers.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        viewModel.emptyFollowers.observe(viewLifecycleOwner) {
            binding.tvNoFollowers.isVisible = it
        }
        viewModel.failureFollowers.observe(viewLifecycleOwner) {
            binding.tvFailure.isVisible = it
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}