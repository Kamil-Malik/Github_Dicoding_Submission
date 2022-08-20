package com.lelestacia.githubuserlist_dicodingsubmission.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lelestacia.githubuserlist_dicodingsubmission.data.model.GetUserFollowNetworkResponseItem
import com.lelestacia.githubuserlist_dicodingsubmission.databinding.UserlistItemAdapterBinding
import com.lelestacia.githubuserlist_dicodingsubmission.ui.view.DetailActivity

class FollowListAdapter :
    ListAdapter<GetUserFollowNetworkResponseItem, FollowListAdapter.ViewHolder>(
        FollowersComparator()
    ) {

    class ViewHolder(private val binding: UserlistItemAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: GetUserFollowNetworkResponseItem) {
            binding.apply {
                Glide.with(itemView).load(user.avatarUrl).into(ivProfilePicture)
                tvGithubName.text = user.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            UserlistItemAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
        holder.itemView.setOnClickListener {
            with(holder.itemView.context) {
                startActivity(
                    Intent(this, DetailActivity::class.java)
                        .putExtra(UserListAdapter.USER_KEY, item.login)
                )
            }
        }
    }

    class FollowersComparator : DiffUtil.ItemCallback<GetUserFollowNetworkResponseItem>() {
        override fun areItemsTheSame(
            oldItem: GetUserFollowNetworkResponseItem,
            newItem: GetUserFollowNetworkResponseItem
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: GetUserFollowNetworkResponseItem,
            newItem: GetUserFollowNetworkResponseItem
        ): Boolean =
            oldItem.id == newItem.id
    }
}