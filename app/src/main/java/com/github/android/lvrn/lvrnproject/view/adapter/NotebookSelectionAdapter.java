package com.github.android.lvrn.lvrnproject.view.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.android.lvrn.lvrnproject.R;
import com.github.valhallalabs.laverna.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.impl.NotebookSelectionDialogFragmentImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NotebookSelectionAdapter extends RecyclerView.Adapter<NotebookSelectionAdapter.NotebookViewHolder>  {

    private NotebookSelectionDialogFragmentImpl mNotebookSelectionDialogFragment;

    private List<Notebook> mNotebooks;
    private int mSelectedItem = -1;

    public NotebookSelectionAdapter(NotebookSelectionDialogFragmentImpl notebookSelectionDialogFragment, List<Notebook> notebooks) {
        mNotebookSelectionDialogFragment = notebookSelectionDialogFragment;
        mNotebooks = notebooks;
    }

    @Override
    public NotebookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notebook_create, parent, false);

        return new NotebookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotebookViewHolder holder, int position) {
        String notebookName = mNotebooks.get(position).getName();
        holder.mNotebookNameTextView.setText(notebookName);

        if (mSelectedItem == position)
            holder.itemView.setBackgroundColor(Color.parseColor("#000000"));
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));

        holder.itemView.setOnClickListener(v -> {
            if (mSelectedItem == position) {
                mSelectedItem = -1;
                mNotebookSelectionDialogFragment
                        .setSelectedNotebook(null);
            } else {
                mSelectedItem = position;
                mNotebookSelectionDialogFragment
                        .setSelectedNotebook(mNotebooks.get(position));
            }
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return mNotebooks.size();
    }

    static class NotebookViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_view_notebook_name) TextView mNotebookNameTextView;

        NotebookViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
