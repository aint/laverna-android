package com.github.android.lvrn.lvrnproject.view.adapter.impl;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.view.adapter.viewholders.NoteViewHolder;
import com.github.android.lvrn.lvrnproject.view.fragment.trash.TrashFragment;

import java.util.List;


/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class TrashAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private TrashFragment mAllNotesFragment;

    private List<Note> mNotes;

    public TrashAdapter(TrashFragment allNotesFragment, List<Note> notes) {
        mAllNotesFragment = allNotesFragment;
        mNotes = notes;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        holder.getTvTitle().setText(mNotes.get(position).getTitle());
        holder.getTvPromptText().setText(mNotes.get(position).getContent());

        holder.itemView.setOnClickListener(v -> mAllNotesFragment.showSelectedNote(mNotes.get(position)));
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }
}


