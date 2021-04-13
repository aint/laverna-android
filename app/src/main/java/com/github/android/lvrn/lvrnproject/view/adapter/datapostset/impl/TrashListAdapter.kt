package com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.android.lvrn.lvrnproject.R
import com.github.android.lvrn.lvrnproject.databinding.ItemNoteBinding
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.trashlist.TrashListFragment
import com.github.android.lvrn.lvrnproject.view.util.convertMillisecondsToString
import com.github.valhallalabs.laverna.persistent.entity.Note

class TrashListAdapter(var trashListFragment: TrashListFragment) : RecyclerView.Adapter<TrashListAdapter.TrashViewHolder>(), DataPostSetAdapter<Note> {

    var notes: List<Note>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrashViewHolder {
        return TrashViewHolder(ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return notes?.size ?: 0
    }

    override fun onBindViewHolder(holder: TrashViewHolder, position: Int) {
        val note: Note = notes!!.get(position)
        val itemNoteBinding: ItemNoteBinding = holder.itemNoteBinding
        itemNoteBinding.tvTitleNote.text = note.title
        itemNoteBinding.tvPromptTextNote.text = note.content
        holder.itemView.setOnClickListener { v: View? -> trashListFragment.showSelectedNote(note) }
        if (note.isFavorite) {
            itemNoteBinding.imBtnFavorite.setImageResource(R.drawable.ic_star_black_24dp)
        } else {
            itemNoteBinding.imBtnFavorite.setImageResource(R.drawable.ic_star_white_24dp)
        }
        itemNoteBinding.tvDateCreatedNote.text = convertMillisecondsToString(note.creationTime)
    }

    override fun setData(data: MutableList<Note>?) {
        notes = data
    }

    class TrashViewHolder(var itemNoteBinding: ItemNoteBinding) : RecyclerView.ViewHolder(itemNoteBinding.root)
}