package com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.databinding.ItemNoteBinding;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.NotesListFragment;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.NotesListPresenter;
import com.github.valhallalabs.laverna.persistent.entity.Note;

import java.util.List;


/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NoteViewHolder> implements DataPostSetAdapter<Note> {

    private NotesListFragment mAllNotesFragment;

    private List<Note> mNotes;

    private NotesListPresenter mNoteListPresenter;

    public NotesListAdapter(NotesListFragment allNotesFragment, NotesListPresenter notesListPresenter) {
        mAllNotesFragment = allNotesFragment;
        mNoteListPresenter = notesListPresenter;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoteViewHolder(ItemNoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Note note = mNotes.get(position);

        if (note.isFavorite()) {
            holder.itemNoteBinding.imBtnFavorite.setImageResource(R.drawable.ic_star_black_24dp);
        } else holder.itemNoteBinding.imBtnFavorite.setImageResource(R.drawable.ic_star_white_24dp);

        holder.itemNoteBinding.tvTitleNote.setText(mNotes.get(position).getTitle());
        holder.itemNoteBinding.tvPromptTextNote.setText(mNotes.get(position).getContent());
        holder.itemView.setOnClickListener(v -> mAllNotesFragment.showSelectedNote(note));
        holder.itemNoteBinding.imBtnFavorite.setOnClickListener(view -> mNoteListPresenter.changeNoteFavouriteStatus(note, position, view));

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
    class NoteViewHolder extends RecyclerView.ViewHolder {

        ItemNoteBinding itemNoteBinding;

        NoteViewHolder(ItemNoteBinding itemNoteBinding) {
            super(itemNoteBinding.getRoot());
            this.itemNoteBinding = itemNoteBinding;
        }
    }
}


