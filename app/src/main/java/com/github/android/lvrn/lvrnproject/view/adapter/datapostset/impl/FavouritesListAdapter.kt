package com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.android.lvrn.lvrnproject.R
import com.github.android.lvrn.lvrnproject.databinding.ItemNoteBinding
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.favouriteslist.FavouritesListFragment
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.favouriteslist.FavouritesListPresenter
import com.github.android.lvrn.lvrnproject.view.util.convertMillisecondsToString
import com.github.valhallalabs.laverna.persistent.entity.Note

class FavouritesListAdapter(
    private val favouriteAdapterListener: FavouriteAdapterListener
) : RecyclerView.Adapter<FavouritesListAdapter.FavouriteViewHolder>(), DataPostSetAdapter<Note> {

    interface FavouriteAdapterListener{
        fun showSelectedNote(note: Note)

        fun showEmptyListView()

        fun changeNoteFavouriteStatus(note: Note)
    }

    private var notes: MutableList<Note> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        return FavouriteViewHolder(ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val note = notes[position]
        holder.itemNoteBinding.tvTitleNote.text = note.title
        holder.itemNoteBinding.tvPromptTextNote.text = note.content
        holder.itemNoteBinding.tvDateCreatedNote.text = convertMillisecondsToString(note.creationTime)
        holder.itemNoteBinding.imBtnFavorite.setImageResource(R.drawable.ic_star_black_24dp)
        holder.itemNoteBinding.imBtnFavorite.setOnClickListener {
            favouriteAdapterListener.changeNoteFavouriteStatus(note)
            notes.remove(note)
            notifyItemRemoved(position)
            if (notes.isEmpty()) {
                favouriteAdapterListener.showEmptyListView()
            }
        }
        holder.itemView.setOnClickListener { favouriteAdapterListener.showSelectedNote(note) }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun setData(data: List<Note>) {
        notes = data as MutableList<Note>
    }

    class FavouriteViewHolder(var itemNoteBinding: ItemNoteBinding) : RecyclerView.ViewHolder(itemNoteBinding.root)
}