package com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist.NotebooksListFragment;
import com.github.android.lvrn.lvrnproject.view.viewholder.NotebookViewHolder;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NotebooksListAdapter extends DataPostSetAdapter<Notebook, NotebookViewHolder> {

    private NotebooksListFragment mNotebooksListFragment;

    private List<Notebook> mNotebooks;


    public NotebooksListAdapter(NotebooksListFragment notebooksListFragment) {
        mNotebooksListFragment = notebooksListFragment;
    }

    @Override
    public NotebookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notebook, parent, false);
        return new NotebookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotebookViewHolder holder, int position) {
        holder.notebookNameTextView.setText(mNotebooks.get(position).getName());
        holder.itemView.setOnClickListener(v -> mNotebooksListFragment.openNotebook(mNotebooks.get(position)));
    }

    @Override
    public int getItemCount() {
        return mNotebooks.size();
    }

    @Override
    public void setData(List<Notebook> data) {
        mNotebooks = data;
    }

}
