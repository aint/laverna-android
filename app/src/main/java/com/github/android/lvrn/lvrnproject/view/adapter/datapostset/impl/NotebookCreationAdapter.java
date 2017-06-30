package com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreation.NotebookCreationPresenter;
import com.github.android.lvrn.lvrnproject.view.viewholder.NotebookCreationViewHolder;

import java.util.List;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class NotebookCreationAdapter extends DataPostSetAdapter<Notebook, NotebookCreationViewHolder> {
    private List<Notebook> mNotebook;

    private NotebookCreationPresenter mNotebookCreationPresenter;

    private int mSelectedItem = -1;

    public NotebookCreationAdapter(NotebookCreationPresenter notebookCreationPresenter) {
        this.mNotebookCreationPresenter = notebookCreationPresenter;
    }

    @Override
    public NotebookCreationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notebook_create, parent, false);
        return new NotebookCreationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotebookCreationViewHolder holder, int position) {

        if (mSelectedItem == position)
            holder.itemView.setBackgroundColor(Color.parseColor("#000000"));
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));

        holder.tvTitle.setText(mNotebook.get(position).getName());

        holder.itemView.setOnClickListener(v -> {
            if (mSelectedItem == position) {
                mSelectedItem = -1;
                mNotebookCreationPresenter.getNotebookId(null);
            } else {
                mSelectedItem = position;
                mNotebookCreationPresenter.getNotebookId(mNotebook.get(position).getId());
            }
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return mNotebook.size();
    }

    @Override
    public void setData(List<Notebook> data) {
        mNotebook = data;
    }


}
