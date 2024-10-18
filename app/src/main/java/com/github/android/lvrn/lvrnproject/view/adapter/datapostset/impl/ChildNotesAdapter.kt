package com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.android.lvrn.lvrnproject.databinding.ItemNoteBinding
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.NotebookChildrenFragment
import com.github.valhallalabs.laverna.persistent.entity.Note

class ChildNotesAdapter(private var notebookChildrenListener: NotebookChildrenListener) : RecyclerView.Adapter<ChildNotesAdapter.ChildNoteViewHolder>(), DataPostSetAdapter<Note> {

    interface NotebookChildrenListener{
        fun showSelectedNote(note: Note)
    }

    private var notes: List<Note> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildNoteViewHolder {
        return ChildNoteViewHolder(ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ChildNoteViewHolder, position: Int) {
        val note = notes[position]
        holder.itemNoteBinding.tvTitleNote.text = note.title
        holder.itemNoteBinding.tvPromptTextNote.text = note.content
        holder.itemView.setOnClickListener { notebookChildrenListener.showSelectedNote(note) }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun setData(data: List<Note>) {
        notes = data
    }

    class ChildNoteViewHolder(var itemNoteBinding: ItemNoteBinding) : RecyclerView.ViewHolder(itemNoteBinding.root)
}