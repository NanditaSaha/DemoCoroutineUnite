package com.example.democoroutineunite.ui.user

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.democoroutineunite.R
import com.example.democoroutineunite.data.model.Data

class UserListAdapter(
    private val context: Context,
    private val arrayList: ArrayList<Data>
) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_name = itemView.findViewById(R.id.tv_name) as TextView
        val tv_year = itemView.findViewById(R.id.tv_year) as TextView
        val tv_panton_value = itemView.findViewById(R.id.tv_panton_value) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_user, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_name.text = arrayList[position].first_name
        holder.tv_year.text = arrayList[position].last_name
        holder.tv_panton_value.text = arrayList[position].email
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}