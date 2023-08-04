package com.project.githubusers.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.githubusers.data.models.ItemsItem
import com.project.githubusers.databinding.ItemUserListRowBinding

class UsersAdapter :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private val listUsers = ArrayList<ItemsItem>()

    var onUserlick: ((String) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(usersList: ArrayList<ItemsItem>) {
        listUsers.clear()
        listUsers.addAll(usersList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemUserListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUsers[position])
    }

    override fun getItemCount() = listUsers.size

    inner class ViewHolder(private var binding: ItemUserListRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(accountInfo: ItemsItem) {
            binding.apply {
                Glide.with(root)
                    .load(accountInfo.avatarUrl)
                    .circleCrop()
                    .into(ivUser)

                tvUsername.text = accountInfo.login
                tvType.text = accountInfo.type

                root.setOnClickListener {
                    onUserlick?.invoke(accountInfo.login)
                }
            }
        }
    }
}