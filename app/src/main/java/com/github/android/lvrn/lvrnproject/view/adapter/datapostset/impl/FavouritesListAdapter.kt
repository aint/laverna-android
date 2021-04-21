package com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.android.lvrn.lvrnproject.R
import com.github.android.lvrn.lvrnproject.databinding.ItemNoteBinding
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.favouriteslist.FavouritesListFragment
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.favouriteslist.FavouritesListPresenter
import com.github.android.lvrn.lvrnproject.view.util.convertMillisecondsToString
import com.github.valhallalabs.laverna.persistent.entity.Note

class FavouritesListAdapter(var favouritesListFragment: FavouritesListFragment,
                            var favouritesListPresenter: FavouritesListPresenter
) : RecyclerView.Adapter<FavouritesListAdapter.FavouriteViewHolder>(), DataPostSetAdapter<Note> {

    lateinit var notes: MutableList<Note>

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
            favouritesListPresenter.changeNoteFavouriteStatus(note, position, it)
            notes.remove(note)
            notifyItemRemoved(position)
            if (notes.isEmpty()) {
                favouritesListFragment.showEmptyListView()
            }
        }
        holder.itemView.setOnClickListener { favouritesListFragment.showSelectedNote(note) }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun setData(data: MutableList<Note>?) {
        notes = data!!
    }

    class FavouriteViewHolder(var itemNoteBinding: ItemNoteBinding) : RecyclerView.ViewHolder(itemNoteBinding.root)
}