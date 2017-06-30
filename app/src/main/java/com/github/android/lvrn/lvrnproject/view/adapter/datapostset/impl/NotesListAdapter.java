package com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.core.allnotes.AllNotesFragment;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.core.allnotes.AllNotesPresenter;
import com.github.android.lvrn.lvrnproject.view.viewholder.NoteViewHolder;

import java.util.List;


/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class NotesListAdapter extends DataPostSetAdapter<Note, NoteViewHolder> {

    private AllNotesFragment mAllNotesFragment;

    private List<Note> mNotes;

    private AllNotesPresenter mNoteListPresenter;

    public NotesListAdapter(AllNotesFragment allNotesFragment, AllNotesPresenter allNotesPresenter) {
        mAllNotesFragment = allNotesFragment;
        mNoteListPresenter = allNotesPresenter;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Note note = mNotes.get(position);

        if(note.isFavorite()){
          holder.imBtnFavorite.setImageResource(R.drawable.ic_star_black_24dp);
        } else holder.imBtnFavorite.setImageResource(R.drawable.ic_star_white_24dp);

        holder.tvTitle.setText(mNotes.get(position).getTitle());
        holder.tvPromptText.setText(mNotes.get(position).getContent());
        holder.itemView.setOnClickListener(v -> mAllNotesFragment.showSelectedNote(note));
        holder.imBtnFavorite.setOnClickListener(view  -> mNoteListPresenter.changeNoteFavouriteStatus(note,position,view));

    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    @Override
    public void setData(List<Note> data) {
        mNotes = data;
    }


}


