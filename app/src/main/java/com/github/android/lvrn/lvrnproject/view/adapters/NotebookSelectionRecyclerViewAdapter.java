package com.github.android.lvrn.lvrnproject.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NotebookSelectionRecyclerViewAdapter extends RecyclerView.Adapter<NotebookSelectionRecyclerViewAdapter.ViewHolder> {
    private List<Notebook> mNotebooks;

    public NotebookSelectionRecyclerViewAdapter(List<Notebook> notebooks) {
        mNotebooks = notebooks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notebook_small, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String notebookName = mNotebooks.get(position).getName();
        holder.mNotebookNameTextView.setText(notebookName);
    }

    @Override
    public int getItemCount() {
        return mNotebooks.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_view_notebook_name) TextView mNotebookNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }



}
