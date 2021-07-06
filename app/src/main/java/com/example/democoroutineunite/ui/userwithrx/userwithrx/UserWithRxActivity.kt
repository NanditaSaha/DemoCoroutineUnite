package com.example.democoroutineunite.ui.userwithrx.userwithrx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.democoroutineunite.R
import com.example.democoroutineunite.data.model.Data
import com.example.democoroutineunite.databinding.ActivityUserwithRxBinding
import com.example.democoroutineunite.ui.user.UserListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class UserWithRxActivity : AppCompatActivity() {
    private val userWithRxViewModel by viewModel<UserWithRxViewModel>()
    lateinit var binding: ActivityUserwithRxBinding
    private lateinit var dataArrayList: ArrayList<Data>
    private lateinit var liniarLayoutManager: LinearLayoutManager
    private lateinit var userListAdapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userwith_rx)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_userwith_rx)
        binding.lifecycleOwner = this
        binding.viewModel = userWithRxViewModel
        setUpRv()
        doGetUser()
    }
    private fun doGetUser() {
        userWithRxViewModel.doGetUserWithRx("1")
        observeData()
    }
    private fun setUpRv() {
        dataArrayList = ArrayList<Data>()
        liniarLayoutManager = LinearLayoutManager(this@UserWithRxActivity)
        binding.rvUsers.layoutManager = liniarLayoutManager
        userListAdapter = UserListAdapter(this, dataArrayList)
        binding.rvUsers.adapter = userListAdapter
    }
    private fun observeData() {
        userWithRxViewModel.data.observe(this, Observer {
            dataArrayList.clear()
            dataArrayList.addAll(it.data)
            Log.e("DATA", it.data.size.toString() + " " + it.toString())
            userListAdapter.notifyDataSetChanged()
        })
    }
}