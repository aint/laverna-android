package com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.android.lvrn.lvrnproject.R;
import com.github.valhallalabs.laverna.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist.NotebooksListFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    class NotebookViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_view_notebook_name) TextView notebookNameTextView;

        NotebookViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
