package com.github.android.lvrn.lvrnproject.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.view.adapter.viewholders.NotebookViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class NotebookRecyclerViewAdapter  extends RecyclerView.Adapter<NotebookViewHolder> {
    private List<Notebook> mNotebookData = new ArrayList<>();

    public NotebookRecyclerViewAdapter(List<Notebook> mNotebookData) {
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

}
