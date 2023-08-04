package com.project.githubusers.view.detail.follows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.githubusers.data.models.ItemsItem
import com.project.githubusers.databinding.FragmentFollowersBinding
import com.project.githubusers.view.adapter.UsersAdapter
import com.project.githubusers.view.detail.DetailActivity
import com.project.githubusers.view.viewmodels.DetailViewModel

class FollowersFragment : Fragment() {
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

    private val usersAdapter = UsersAdapter()
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel = (activity as DetailActivity).detailViewModel

        observeViewModel()
    }

    private fun observeViewModel() {
        detailViewModel.isLoadingFollower.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        detailViewModel.listFollower.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.tvNoList.visibility = View.GONE
                setData(it)
            } else {
                binding.tvNoList.visibility = View.VISIBLE
            }
        }
    }

    private fun setData(userList: List<ItemsItem>) {
        with(binding) {
            usersAdapter.setData(userList as ArrayList<ItemsItem>)

            rvFollowers.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = usersAdapter
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressbar.isVisible = isLoading
    }
}