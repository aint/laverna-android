package com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.github.android.lvrn.lvrnproject.databinding.ItemNoteBinding;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.trashlist.TrashListFragment;
import com.github.valhallalabs.laverna.persistent.entity.Note;

import java.util.List;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class TrashListAdapter extends RecyclerView.Adapter<TrashListAdapter.TrashViewHolder> implements DataPostSetAdapter<Note> {

    private TrashListFragment mAllNotesFragment;

    private List<Note> mNotes;

    public TrashListAdapter(TrashListFragment allNotesFragment) {
        mAllNotesFragment = allNotesFragment;
    }

    @Override
    public TrashViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TrashViewHolder(ItemNoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(TrashViewHolder holder, int position) {
        holder.itemNoteBinding.tvTitleNote.setText(mNotes.get(position).getTitle());
        holder.itemNoteBinding.tvPromptTextNote.setText(mNotes.get(position).getContent());

        holder.itemView.setOnClickListener(v -> mAllNotesFragment.showSelectedNote(mNotes.get(position)));
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    @Override
    public void setData(List<Note> data) {
        mNotes = data;
    }

    //TODO: maybe create one class for it.
    class TrashViewHolder extends RecyclerView.ViewHolder {

        ItemNoteBinding itemNoteBinding;

        TrashViewHolder(ItemNoteBinding itemNoteBinding) {
            super(itemNoteBinding.getRoot());
            this.itemNoteBinding = itemNoteBinding;
        }
    }
}


