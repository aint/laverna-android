package com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.github.android.lvrn.lvrnproject.R
import com.github.android.lvrn.lvrnproject.databinding.ItemTrashBinding
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.trashlist.TrashListFragment
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.trashlist.TrashListPresenter
import com.github.android.lvrn.lvrnproject.view.util.convertMillisecondsToString
import com.github.valhallalabs.laverna.persistent.entity.Note

class TrashListAdapter(private var trashListFragment: TrashListFragment, private var trashListPresenter: TrashListPresenter) : RecyclerView.Adapter<TrashListAdapter.TrashViewHolder>(), DataPostSetAdapter<Note> {

    private var notes: List<Note> = emptyList()
    private val viewBinderHelper: ViewBinderHelper = ViewBinderHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrashViewHolder {
        return TrashViewHolder(ItemTrashBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: TrashViewHolder, position: Int) {
        val note: Note = notes.get(position)
        val itemTrashBinding: ItemTrashBinding = holder.itemTrashBinding
        itemTrashBinding.tvTitleNote.text = note.title
        itemTrashBinding.tvPromptTextNote.text = note.content
        itemTrashBinding.cardViewNotes.setOnClickListener { trashListFragment.showSelectedNote(note) }
        itemTrashBinding.deleteLayout.setOnClickListener{
            trashListFragment.removeNoteForever(position)
            showEmptyViewState()
        }
        itemTrashBinding.restoreLayout.setOnClickListener{
            trashListPresenter.restoreNote(position)
            showEmptyViewState()
        }
        if (note.isFavorite) {
            itemTrashBinding.imBtnFavorite.setImageResource(R.drawable.ic_star_black_24dp)
        } else {
            itemTrashBinding.imBtnFavorite.setImageResource(R.drawable.ic_star_white_24dp)
        }
        itemTrashBinding.tvDateCreatedNote.text = convertMillisecondsToString(note.creationTime)
        viewBinderHelper.setOpenOnlyOne(true)
        viewBinderHelper.bind(itemTrashBinding.swipeRevealLayout, note.id)
    }

    override fun setData(data: List<Note>) {
        notes = data
    }

    private fun showEmptyViewState() {
        if (notes.isEmpty()) {
            trashListFragment.showEmptyListView()
        }
    }

    class TrashViewHolder(var itemTrashBinding: ItemTrashBinding) : RecyclerView.ViewHolder(itemTrashBinding.root)
}