package com.mobile.finance.entries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mobile.finance.data.entity.CompositeEntry
import com.mobile.finance.databinding.EntryItemBinding

class EntryListAdapter(private val viewModel: EntryListViewModel) : ListAdapter<CompositeEntry, EntryListAdapter.ViewHolder>(EntryDiffCallback()) {

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(viewModel, getItem(position))
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder.from(parent)
  }


  class ViewHolder private constructor(val binding: EntryItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(viewModel: EntryListViewModel, item: CompositeEntry) {
      binding.entry = item
      binding.viewModel = viewModel
      binding.executePendingBindings()
    }

    companion object {
      fun from(parent: ViewGroup): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = EntryItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
      }
    }
  }
}

class EntryDiffCallback : DiffUtil.ItemCallback<CompositeEntry>() {
  override fun areItemsTheSame(oldItem: CompositeEntry, newItem: CompositeEntry): Boolean {
    return oldItem.entry.entryId == newItem.entry.entryId
  }

  override fun areContentsTheSame(oldItem: CompositeEntry, newItem: CompositeEntry): Boolean {
    return oldItem == newItem
  }
}
