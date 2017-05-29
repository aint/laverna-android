package com.github.android.lvrn.lvrnproject.view.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.view.adapters.viewholders.NotesViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NotesViewHolder> {

    public static ItemClickListener mItemClickListener;
    public List<Note> allNotesData = new ArrayList<>();


    public NoteRecyclerViewAdapter(List<Note> data) {
        allNotesData = data;
    }

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {
        holder.getTvTitle().setText(allNotesData.get(position).getTitle());
        holder.getTvPromptText().setText(allNotesData.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return allNotesData.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void setAllNotesData(List<Note> allNotesData) {
        this.allNotesData = allNotesData;
        notifyDataSetChanged();
    }


    public interface ItemClickListener {
        void onClick(View view, int position);
    }
}


