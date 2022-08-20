package com.lelestacia.githubuserlist_dicodingsubmission.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lelestacia.githubuserlist_dicodingsubmission.databinding.FragmentFollowingBinding
import com.lelestacia.githubuserlist_dicodingsubmission.ui.adapter.FollowListAdapter
import com.lelestacia.githubuserlist_dicodingsubmission.ui.viewmodel.DetailViewModel

class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        subscribe()
        return binding.root
    }

    private fun subscribe() {
        val adapter = FollowListAdapter()
        binding.rvFollowing.adapter = adapter
        binding.rvFollowing.layoutManager = LinearLayoutManager(activity)
        viewModel.following.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        viewModel.emptyFollowing.observe(viewLifecycleOwner) {
            binding.tvNoFollowing.isVisible = it
        }
        viewModel.failureFollowing.observe(viewLifecycleOwner) {
            binding.tvFailure.isVisible = it
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}