package com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.NotebookChildrenFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notebook, parent, false);
        return new ChildNotebooksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChildNotebooksAdapter.ChildNotebooksViewHolder holder, int position) {
        holder.notebookNameTextView.setText(mNotebooks.get(position).getName());
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

        @BindView(R.id.text_view_notebook_name) TextView notebookNameTextView;

        ChildNotebooksViewHolder (View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
