package com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.github.android.lvrn.lvrnproject.databinding.ItemNotebookBinding;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.NotebookChildrenFragment;
import com.github.valhallalabs.laverna.persistent.entity.Notebook;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class ChildNotebooksAdapter extends RecyclerView.Adapter<ChildNotebooksAdapter.ChildNotebooksViewHolder> implements DataPostSetAdapter<Notebook> {

    private NotebookChildrenFragment mNotebooksChildrenFragment;

    private List<Notebook> mNotebooks;

    public ChildNotebooksAdapter(NotebookChildrenFragment mNotebooksChildrenFragment) {
        this.mNotebooksChildrenFragment = mNotebooksChildrenFragment;
    }

    @Override
    public ChildNotebooksAdapter.ChildNotebooksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChildNotebooksViewHolder(ItemNotebookBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(ChildNotebooksAdapter.ChildNotebooksViewHolder holder, int position) {
        holder.itemNotebookBinding.textViewNotebookName.setText(mNotebooks.get(position).getName());
        holder.itemView.setOnClickListener(v -> mNotebooksChildrenFragment.openNotebook(mNotebooks.get(position)));
    }

    @Override
    public int getItemCount() {
        return mNotebooks.size();
    }

    @Override
    public void setData(List<Notebook> data) {
        mNotebooks = data;
    }

    class ChildNotebooksViewHolder extends RecyclerView.ViewHolder {

        private ItemNotebookBinding itemNotebookBinding;

        ChildNotebooksViewHolder(ItemNotebookBinding itemNotebookBinding) {
            super(itemNotebookBinding.getRoot());
            this.itemNotebookBinding = itemNotebookBinding;
        }
    }
}
