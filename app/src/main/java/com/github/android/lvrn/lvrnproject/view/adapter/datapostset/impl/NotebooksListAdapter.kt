package com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.android.lvrn.lvrnproject.databinding.ItemNotebookBinding
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist.NotebooksListFragment
import com.github.valhallalabs.laverna.persistent.entity.Notebook

class NotebooksListAdapter(var notebooksListFragment: NotebooksListFragment) : RecyclerView.Adapter<NotebooksListAdapter.NotebookViewHolder>(), DataPostSetAdapter<Notebook> {

    lateinit var notebooks: List<Notebook>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotebookViewHolder {
        return NotebookViewHolder(ItemNotebookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: NotebookViewHolder, position: Int) {
        holder.itemNotebookBinding.textViewNotebookName.text = notebooks[position].name
        holder.itemView.setOnClickListener { notebooksListFragment.openNotebook(notebooks[position]) }
    }

    override fun getItemCount(): Int {
        return notebooks.size
    }

    override fun setData(data: List<Notebook>) {
        notebooks = data.toMutableList()
    }

    class NotebookViewHolder(var itemNotebookBinding: ItemNotebookBinding) : RecyclerView.ViewHolder(itemNotebookBinding.root)
}