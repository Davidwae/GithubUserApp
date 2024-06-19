package com.waekaizo.githubuserapp.ui.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.waekaizo.githubuserapp.database.FavoriteUser
import com.waekaizo.githubuserapp.databinding.ItemRowUsersBinding
import com.waekaizo.githubuserapp.helper.FavoriteUserDiffCallback
import com.waekaizo.githubuserapp.ui.detailuser.DetailUserActivity

class FavoriteUseradapter: RecyclerView.Adapter<FavoriteUseradapter.FavoriteViewHolder>() {
    private val listFavoriteUsers = ArrayList<FavoriteUser>()
    fun setListFavoriteUsers(listFavoriteUser: List<FavoriteUser>) {
        val diffCallback = FavoriteUserDiffCallback(this.listFavoriteUsers, listFavoriteUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavoriteUsers.clear()
        this.listFavoriteUsers.addAll(listFavoriteUser)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class FavoriteViewHolder(private val binding: ItemRowUsersBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteUser: FavoriteUser) {
            with(binding) {
                tvUserName.text = favoriteUser.username
                Glide.with(itemView)
                    .load(favoriteUser.avatarUrl)
                    .into(binding.imgUserName)
                cardView.setOnClickListener{
                    val intent = Intent(it.context, DetailUserActivity::class.java)
                    intent.putExtra(DetailUserActivity.EXTRA_USERNAME, favoriteUser.username)
                    it.context.startActivity(intent)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemRowUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int = listFavoriteUsers.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavoriteUsers[position])
    }
}