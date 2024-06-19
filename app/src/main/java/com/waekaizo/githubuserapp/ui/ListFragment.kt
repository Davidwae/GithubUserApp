package com.waekaizo.githubuserapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.waekaizo.githubuserapp.data.response.ItemsItem
import com.waekaizo.githubuserapp.databinding.FragmentListBinding
import com.waekaizo.githubuserapp.ui.main.MainViewModel

class ListFragment : Fragment() {

    private var _binding : FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var username: String
    private var position = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME).toString()
        }

        binding.rvListfollow.layoutManager = LinearLayoutManager(requireActivity())
        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)
        mainViewModel.findFollowUsers(username)

        if (position == 1){
            mainViewModel.listFollower.observe(viewLifecycleOwner) { listFollower ->
                setFollowUsersData(listFollower)
            }
            mainViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        } else {
            mainViewModel.listFollowing.observe(viewLifecycleOwner) { listFollowing ->
                setFollowUsersData(listFollowing)
            }
            mainViewModel.isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        }
    }

    companion object {
        const val ARG_POSITION = "section_number"
        const val ARG_USERNAME = "extra_username"
    }

    private fun setFollowUsersData(users: List<ItemsItem>) {
        val adapter = ListUserAdapter()
        adapter.submitList(users)
        binding.rvListfollow.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}