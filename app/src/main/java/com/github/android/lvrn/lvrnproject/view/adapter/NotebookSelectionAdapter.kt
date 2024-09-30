package com.github.android.lvrn.lvrnproject.view.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.android.lvrn.lvrnproject.databinding.ItemNotebookCreateBinding
import com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.impl.NotebookSelectionDialogFragmentImpl
import com.github.valhallalabs.laverna.persistent.entity.Notebook

class NotebookSelectionAdapter constructor(
    val mNotebookSelectionDialogFragment: NotebookSelectionDialogFragmentImpl,
    val mNotebooks: List<Notebook>,
) : RecyclerView.Adapter<NotebookSelectionAdapter.NotebookViewHolder>() {

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
        val notebookName = mNotebooks[position].name
        holder.itemNotebookCreateBinding.textViewNotebookName.setText(notebookName)

        if (mSelectedItem == position) holder.itemView.setBackgroundColor(Color.parseColor("#000000"))
        else holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))

        holder.itemView.setOnClickListener { v: View? ->
            if (mSelectedItem == position) {
                mSelectedItem = -1
                mNotebookSelectionDialogFragment
                    .setSelectedNotebook(null)
            } else {
                mSelectedItem = position
                mNotebookSelectionDialogFragment
                    .setSelectedNotebook(mNotebooks[position])
            }
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return mNotebooks.size
    }

    class NotebookViewHolder(itemNotebookCreateBinding: ItemNotebookCreateBinding) :
        RecyclerView.ViewHolder(itemNotebookCreateBinding.getRoot()) {
        var itemNotebookCreateBinding: ItemNotebookCreateBinding = itemNotebookCreateBinding
    }
}