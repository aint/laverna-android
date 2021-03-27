package com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.github.android.lvrn.lvrnproject.databinding.ItemNoteBinding;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.favouriteslist.FavouritesListFragment;
import com.github.valhallalabs.laverna.persistent.entity.Note;

import java.util.List;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */
public class FavouritesListAdapter extends RecyclerView.Adapter<FavouritesListAdapter.FavouriteViewHolder> implements DataPostSetAdapter<Note> {

    private FavouritesListFragment mAllNotesFragment;

    private List<Note> mNotes;

    public FavouritesListAdapter(FavouritesListFragment allNotesFragment) {
        mAllNotesFragment = allNotesFragment;
    }

    @Override
    public FavouriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FavouriteViewHolder(ItemNoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(FavouriteViewHolder holder, int position) {
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
    class FavouriteViewHolder extends RecyclerView.ViewHolder {

        ItemNoteBinding itemNoteBinding;

        FavouriteViewHolder(ItemNoteBinding itemNoteBinding) {
            super(itemNoteBinding.getRoot());
            this.itemNoteBinding = itemNoteBinding;
        }
    }
}


