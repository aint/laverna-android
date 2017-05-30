package com.github.android.lvrn.lvrnproject.view.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.view.adapters.viewholders.NotesViewHolder;
import com.github.android.lvrn.lvrnproject.view.fragments.allnotes.impl.AllNotesFragmentImpl;

import java.util.List;


/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NotesViewHolder> {

    private AllNotesFragmentImpl mAllNotesFragment;

    private List<Note> mNotes;

    public NoteRecyclerViewAdapter(AllNotesFragmentImpl allNotesFragment, List<Note> notes) {
        mAllNotesFragment = allNotesFragment;
        mNotes = notes;
    }

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {
        holder.getTvTitle().setText(mNotes.get(position).getTitle());
        holder.getTvPromptText().setText(mNotes.get(position).getContent());

        holder.itemView.setOnClickListener(v -> mAllNotesFragment.showSelectedNote(mNotes.get(position)));
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }
}


