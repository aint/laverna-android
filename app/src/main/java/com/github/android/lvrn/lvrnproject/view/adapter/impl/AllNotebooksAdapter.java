package com.github.android.lvrn.lvrnproject.view.adapter.impl;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.view.adapter.DataPostSetAdapter;
import com.github.android.lvrn.lvrnproject.view.adapter.viewholders.NotebookViewHolder;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist.NotebooksListFragment;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class AllNotebooksAdapter extends RecyclerView.Adapter<NotebookViewHolder> implements DataPostSetAdapter<Notebook> {

    private NotebooksListFragment mNotebooksListFragment;

    private List<Notebook> mNotebooks;


    public AllNotebooksAdapter(NotebooksListFragment notebooksListFragment) {
        mNotebooksListFragment = notebooksListFragment;
    }

    @Override
    public NotebookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notebook, parent, false);
        return new NotebookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotebookViewHolder holder, int position) {
        //TODO:
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
