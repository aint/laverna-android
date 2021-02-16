package com.github.android.lvrn.lvrnproject.view.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.github.android.lvrn.lvrnproject.databinding.ItemNotebookCreateBinding;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.impl.NotebookSelectionDialogFragmentImpl;
import com.github.valhallalabs.laverna.persistent.entity.Notebook;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NotebookSelectionAdapter extends RecyclerView.Adapter<NotebookSelectionAdapter.NotebookViewHolder> {

    private NotebookSelectionDialogFragmentImpl mNotebookSelectionDialogFragment;

    private List<Notebook> mNotebooks;
    private int mSelectedItem = -1;

    public NotebookSelectionAdapter(NotebookSelectionDialogFragmentImpl notebookSelectionDialogFragment, List<Notebook> notebooks) {
        mNotebookSelectionDialogFragment = notebookSelectionDialogFragment;
        mNotebooks = notebooks;
    }

    @Override
    public NotebookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NotebookViewHolder(ItemNotebookCreateBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(NotebookViewHolder holder, int position) {
        String notebookName = mNotebooks.get(position).getName();
        holder.itemNotebookCreateBinding.textViewNotebookName.setText(notebookName);

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

        ItemNotebookCreateBinding itemNotebookCreateBinding;

        NotebookViewHolder(ItemNotebookCreateBinding itemNotebookCreateBinding) {
            super(itemNotebookCreateBinding.getRoot());
            this.itemNotebookCreateBinding = itemNotebookCreateBinding;
        }
    }
}
