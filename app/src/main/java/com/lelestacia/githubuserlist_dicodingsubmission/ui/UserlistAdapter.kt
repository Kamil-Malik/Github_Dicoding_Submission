package com.lelestacia.githubuserlist_dicodingsubmission.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lelestacia.githubuserlist_dicodingsubmission.data.User
import com.lelestacia.githubuserlist_dicodingsubmission.databinding.UserlistItemAdapterBinding

class UserlistAdapter(private val userList : ArrayList<User>) :
    RecyclerView.Adapter<UserlistAdapter.ViewHolder>() {

    companion object {
        const val USER_KEY = "user"
    }

    class ViewHolder(val binding : UserlistItemAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ViewHolder {
        val binding =
            UserlistItemAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder : ViewHolder, position : Int) {
        holder.binding.apply {
            val user = userList[position]
            tvGithubName.text = user.fullname
            tvUserLocation.text = user.location
            Glide.with(holder.itemView.context).load(user.picture).into(ivProfilePicture)
        }

        holder.itemView.setOnClickListener {
            val user = userList[position]
            holder.itemView.context.startActivity(Intent(holder.itemView.context, DetailActivity::class.java)
                .also {
                    it.putExtra(USER_KEY, user)
            })
        }
    }

    override fun getItemCount() : Int = userList.size
}