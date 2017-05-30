package com.github.android.lvrn.lvrnproject.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Entity;
import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.view.adapter.viewholders.NotebookViewHolder;
import com.github.android.lvrn.lvrnproject.view.adapter.viewholders.NotesViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class TrashRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Entity> mEntityData = new ArrayList<>();

    private final int NOTE = 0, NOTEBOOK = 1;

    public TrashRecyclerViewAdapter(List<Entity> mEntityData) {
        this.mEntityData = mEntityData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == NOTE) {
            View viewNote = inflater.inflate(R.layout.item_note, parent, false);
            return new NotesViewHolder(viewNote);
        } else if (viewType == NOTEBOOK) {
            View viewNotebook = inflater.inflate(R.layout.item_notebook, parent, false);
            return new NotebookViewHolder(viewNotebook);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == NOTE) {
            NotesViewHolder notesViewHolder = (NotesViewHolder) holder;
            configureNotesViewHolder(notesViewHolder, position);
        } else if (holder.getItemViewType() == NOTEBOOK){
            NotebookViewHolder notebookViewHolder = (NotebookViewHolder) holder;
            configureNotebookViewHolder(notebookViewHolder,position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mEntityData.get(position) instanceof Note) {
            return NOTE;
        } else if (mEntityData.get(position) instanceof Notebook) {
            return NOTEBOOK;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return mEntityData.size();
    }

    private void configureNotesViewHolder(NotesViewHolder notesViewHolder, int position) {
        notesViewHolder.getTvTitle().setText(((Note) mEntityData.get(position)).getTitle());
        notesViewHolder.getTvPromptText().setText(((Note) mEntityData.get(position)).getContent());
    }

    private void configureNotebookViewHolder(NotebookViewHolder notebookViewHolder, int position) {
        notebookViewHolder.getTvTitle().setText(((Notebook)mEntityData.get(position)).getName());
    }

}
