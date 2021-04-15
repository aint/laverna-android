package com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.android.lvrn.lvrnproject.R
import com.github.android.lvrn.lvrnproject.databinding.ItemNoteBinding
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.NotesListFragment
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.NotesListPresenter
import com.github.android.lvrn.lvrnproject.view.util.convertMillisecondsToString
import com.github.valhallalabs.laverna.persistent.entity.Note

class NotesListAdapter(val allNotesFragment: NotesListFragment, val noteListPresenter: NotesListPresenter) : RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>(), DataPostSetAdapter<Note> {

    private var notes: List<Note>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return notes?.size ?: 0
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note: Note = notes!![position]
        val itemNoteBinding: ItemNoteBinding = holder.itemNoteBinding
        if (note.isFavorite) {
            itemNoteBinding.imBtnFavorite.setImageResource(R.drawable.ic_star_black_24dp)
        } else {
            itemNoteBinding.imBtnFavorite.setImageResource(R.drawable.ic_star_white_24dp)
        }
        itemNoteBinding.tvTitleNote.text = note.title
        itemNoteBinding.tvPromptTextNote.text = note.content
        holder.itemView.setOnClickListener { allNotesFragment.showSelectedNote(note) }
        itemNoteBinding.imBtnFavorite.setOnClickListener { view -> noteListPresenter.changeNoteFavouriteStatus(note, position, view) }
        itemNoteBinding.tvDateCreatedNote.text = convertMillisecondsToString(note.creationTime)
    }

    override fun setData(data: MutableList<Note>?) {
        notes = data
    }

    class NoteViewHolder(val itemNoteBinding: ItemNoteBinding) : RecyclerView.ViewHolder(itemNoteBinding.root)

}

