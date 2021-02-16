package com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.github.android.lvrn.lvrnproject.databinding.ItemNoteBinding;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.NotebookChildrenFragment;
import com.github.valhallalabs.laverna.persistent.entity.Note;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class ChildNotesAdapter extends RecyclerView.Adapter<ChildNotesAdapter.ChildNoteViewHolder> implements DataPostSetAdapter<Note> {

    private NotebookChildrenFragment mNotebookChildrenFragment;

    private List<Note> mNotes;

    public ChildNotesAdapter(NotebookChildrenFragment mNotebookChildrenFragment) {
        this.mNotebookChildrenFragment = mNotebookChildrenFragment;
    }

    @Override
    public ChildNotesAdapter.ChildNoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChildNoteViewHolder(ItemNoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(ChildNotesAdapter.ChildNoteViewHolder holder, int position) {
        holder.itemNoteBinding.tvTitleNote.setText(mNotes.get(position).getTitle());
        holder.itemNoteBinding.tvPromptTextNote.setText(mNotes.get(position).getContent());
        holder.itemView.setOnClickListener(v -> mNotebookChildrenFragment.showSelectedNote(mNotes.get(position)));
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    @Override
    public void setData(List<Note> data) {
        mNotes = data;
    }

    class ChildNoteViewHolder extends RecyclerView.ViewHolder {

        ItemNoteBinding itemNoteBinding;

        ChildNoteViewHolder(ItemNoteBinding itemNoteBinding) {
            super(itemNoteBinding.getRoot());
            this.itemNoteBinding = itemNoteBinding;
        }
    }
}
