package com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.github.android.lvrn.lvrnproject.databinding.ItemNotebookCreateBinding;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreation.NotebookCreationPresenter;
import com.github.valhallalabs.laverna.persistent.entity.Notebook;

import java.util.List;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class NotebookCreationAdapter extends RecyclerView.Adapter<NotebookCreationAdapter.NotebookViewHolder> implements DataPostSetAdapter<Notebook> {
    private List<Notebook> mNotebook;

    private NotebookCreationPresenter mNotebookCreationPresenter;

    private int mSelectedItem = -1;

    public NotebookCreationAdapter(NotebookCreationPresenter notebookCreationPresenter) {
        this.mNotebookCreationPresenter = notebookCreationPresenter;
    }

    @Override
    public NotebookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NotebookViewHolder(ItemNotebookCreateBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(NotebookViewHolder holder, int position) {

        if (mSelectedItem == position)
            holder.itemView.setBackgroundColor(Color.parseColor("#000000"));
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));

        holder.itemNotebookCreateBinding.textViewNotebookName.setText(mNotebook.get(position).getName());

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

    class NotebookViewHolder extends RecyclerView.ViewHolder {


        ItemNotebookCreateBinding itemNotebookCreateBinding;

        NotebookViewHolder(ItemNotebookCreateBinding itemNotebookCreateBinding) {
            super(itemNotebookCreateBinding.getRoot());
            this.itemNotebookCreateBinding = itemNotebookCreateBinding;
        }
    }
}
