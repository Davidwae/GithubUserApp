package com.waekaizo.githubuserapp.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.waekaizo.githubuserapp.data.response.ItemsItem
import com.waekaizo.githubuserapp.databinding.ItemRowUsersBinding
import com.waekaizo.githubuserapp.ui.detailuser.DetailUserActivity

class ListUserAdapter: ListAdapter<ItemsItem, ListUserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(private val binding: ItemRowUsersBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(listUser: ItemsItem) {
            binding.tvUserName.text = listUser.login
            Glide.with(itemView)
                .load(listUser.avatarUrl)
                .into(binding.imgUserName)
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>(){
            override fun areItemsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener{
            val intentDetail = Intent(holder.itemView.context, DetailUserActivity::class.java)
            intentDetail.putExtra(DetailUserActivity.EXTRA_USERNAME, user.login)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

}