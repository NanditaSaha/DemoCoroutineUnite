package com.example.democoroutineunite.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.democoroutineunite.R
import com.example.democoroutineunite.data.model.Data
import com.example.democoroutineunite.databinding.ActivityUserListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserListActivity : AppCompatActivity() {
    private val userListViewModel by viewModel<UserListViewModel>()
    private lateinit var binding: ActivityUserListBinding
    private lateinit var dataArrayList: ArrayList<Data>
    private lateinit var liniarLayoutManager: LinearLayoutManager
    private lateinit var userListAdapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_list)
        binding.lifecycleOwner = this
        binding.viewModel = userListViewModel
        setUpRv()
        doGetUser()
    }

    private fun doGetUser() {
        userListViewModel.doGetUserList("1")
        observeData()
    }

    private fun setUpRv() {
        dataArrayList = ArrayList<Data>()
        liniarLayoutManager = LinearLayoutManager(this@UserListActivity)
        binding.rvUsers.layoutManager = liniarLayoutManager
        userListAdapter = UserListAdapter(this, dataArrayList)
        binding.rvUsers.adapter = userListAdapter
    }

    private fun observeData() {
        userListViewModel.data.observe(this, Observer {
            dataArrayList.clear()
            dataArrayList.addAll(it.data)
            Log.e("DATA",it.data.size.toString()+" "+it.toString())
            userListAdapter.notifyDataSetChanged()
        })
    }
}