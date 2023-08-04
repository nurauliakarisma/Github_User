package com.project.githubusers.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.githubusers.data.models.FavoriteEntity
import com.project.githubusers.databinding.ItemUserListRowBinding

class FavoriteAdapter :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private val listFavorite = ArrayList<FavoriteEntity>()
    var onFavClick: ((String) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setFavorite(listFav: ArrayList<FavoriteEntity>) {
        listFavorite.clear()
        listFavorite.addAll(listFav)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemUserListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    override fun getItemCount() = listFavorite.size

    inner class ViewHolder(private var binding: ItemUserListRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteEntity: FavoriteEntity) {

            binding.apply {
                Glide.with(root)
                    .load(favoriteEntity.avatarUrl)
                    .circleCrop()
                    .into(ivUser)

                tvUsername.text = favoriteEntity.login
                tvType.text = favoriteEntity.type

                root.setOnClickListener {
                    onFavClick?.invoke(favoriteEntity.login!!)
                }
            }
        }
    }
}