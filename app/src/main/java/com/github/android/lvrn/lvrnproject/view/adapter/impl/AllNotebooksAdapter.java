package com.github.android.lvrn.lvrnproject.view.adapter.impl;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.view.adapter.DataPostSetAdapter;
import com.github.android.lvrn.lvrnproject.view.adapter.viewholders.NotebookViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */
//TODO: FIX!
public class AllNotebooksAdapter extends RecyclerView.Adapter<NotebookViewHolder> implements DataPostSetAdapter<Note> {
    private List<Notebook> mNotebookData = new ArrayList<>();
//    public static AllNotesAdapter.ItemClickListener mItemClickListener;

    public AllNotebooksAdapter(List<Notebook> mNotebookData) {
        this.mNotebookData = mNotebookData;
    }

    @Override
    public NotebookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notebook,parent,false);
        return new NotebookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotebookViewHolder holder, int position) {
        holder.getTvTitle().setText(mNotebookData.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mNotebookData.size();
    }

    @Override
    public void setData(List<Note> data) {

    }

//    public void setClickListener(AllNotesAdapter.ItemClickListener itemClickListener) {
//        mItemClickListener = itemClickListener;
//    }

}
