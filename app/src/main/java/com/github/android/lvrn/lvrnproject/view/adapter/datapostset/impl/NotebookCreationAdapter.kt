package com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.android.lvrn.lvrnproject.databinding.ItemNotebookCreateBinding
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter
import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreation.NotebookCreationPresenter
import com.github.valhallalabs.laverna.persistent.entity.Notebook

class NotebookCreationAdapter(val notebookCreationAdapterListener: NotebookCreationAdapterListener) :
    RecyclerView.Adapter<NotebookCreationAdapter.NotebookViewHolder>(),
    DataPostSetAdapter<Notebook> {

    interface NotebookCreationAdapterListener{
        fun getNotebookId(notebookId: String?)
    }

    private var mNotebook: List<Notebook> = emptyList()

    private var mSelectedItem = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotebookViewHolder {
        return NotebookViewHolder(
            ItemNotebookCreateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NotebookViewHolder, position: Int) {
        if (mSelectedItem == position) holder.itemView.setBackgroundColor(Color.parseColor("#000000"))
        else holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))

        holder.itemNotebookCreateBinding.textViewNotebookName.text = mNotebook[position].name

        holder.itemView.setOnClickListener { v: View? ->
            if (mSelectedItem == position) {
                mSelectedItem = -1
                notebookCreationAdapterListener.getNotebookId(null)
            } else {
                mSelectedItem = position
                notebookCreationAdapterListener.getNotebookId(mNotebook[position].id)
            }
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return mNotebook.size
    }

    override fun setData(data: List<Notebook>) {
        mNotebook = data
    }

    class NotebookViewHolder(var itemNotebookCreateBinding: ItemNotebookCreateBinding) :
        RecyclerView.ViewHolder(itemNotebookCreateBinding.getRoot()) {
    }
}