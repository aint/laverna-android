package com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.github.android.lvrn.lvrnproject.databinding.ItemNotebookBinding;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist.NotebooksListFragment;
import com.github.valhallalabs.laverna.persistent.entity.Notebook;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NotebooksListAdapter extends RecyclerView.Adapter<NotebooksListAdapter.NotebookViewHolder> implements DataPostSetAdapter<Notebook> {

    private NotebooksListFragment mNotebooksListFragment;

    private List<Notebook> mNotebooks;


    public NotebooksListAdapter(NotebooksListFragment notebooksListFragment) {
        mNotebooksListFragment = notebooksListFragment;
    }

    @Override
    public NotebookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NotebookViewHolder(ItemNotebookBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(NotebookViewHolder holder, int position) {
        holder.itemNotebookBinding.textViewNotebookName.setText(mNotebooks.get(position).getName());
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

    class NotebookViewHolder extends RecyclerView.ViewHolder {

        ItemNotebookBinding itemNotebookBinding;

        NotebookViewHolder(ItemNotebookBinding itemNotebookBinding) {
            super(itemNotebookBinding.getRoot());
            this.itemNotebookBinding = itemNotebookBinding;
        }
    }
}
