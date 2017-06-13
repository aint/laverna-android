package com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreation.NotebookCreationPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notebook_create, parent, false);
        return new NotebookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotebookViewHolder holder, int position) {

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

    class NotebookViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_view_notebook_name) TextView tvTitle;

        NotebookViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
