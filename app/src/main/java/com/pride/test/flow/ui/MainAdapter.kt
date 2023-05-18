package com.pride.test.flow.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pride.test.flow.R
import com.pride.test.flow.databinding.ItemBinding
import com.pride.test.flow.room.MyEntity

class MainAdapter : RecyclerView.Adapter<MainAdapter.ItemHolder>() {
    var listItems = listOf<MyEntity>()
    @SuppressLint("NotifyDataSetChanged")
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    class ItemHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ItemBinding.bind(item)
        fun inflateItem(item: MyEntity) = with(binding.root) {
            text = item.stringgMane
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))
    }

    override fun getItemCount() = listItems.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.inflateItem(listItems[position])
    }
}