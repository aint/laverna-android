package com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.android.lvrn.lvrnproject.databinding.ItemNotebookBinding
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.NotebookChildrenFragment
import com.github.valhallalabs.laverna.persistent.entity.Notebook

class ChildNotebooksAdapter(private var childNotebooksAdapterListener: ChildNotebooksAdapterListener) :
    RecyclerView.Adapter<ChildNotebooksAdapter.ChildNotebooksViewHolder>(),
    DataPostSetAdapter<Notebook> {

    interface ChildNotebooksAdapterListener{
        fun openNotebook(notebook: Notebook)
    }

    private var notebookList: List<Notebook> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildNotebooksViewHolder {
        return ChildNotebooksViewHolder(
            ItemNotebookBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ChildNotebooksViewHolder, position: Int) {
        val notebook = notebookList[position]
        holder.itemNotebookBinding.textViewNotebookName.text = notebook.name
        holder.itemView.setOnClickListener { childNotebooksAdapterListener.openNotebook(notebook) }
    }

    override fun getItemCount(): Int {
        return notebookList.size
    }

    override fun setData(data: List<Notebook>) {
        notebookList = data
    }

    class ChildNotebooksViewHolder(var itemNotebookBinding: ItemNotebookBinding) :
        RecyclerView.ViewHolder(itemNotebookBinding.root)
}