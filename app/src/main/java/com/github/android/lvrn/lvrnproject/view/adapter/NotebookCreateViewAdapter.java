package com.github.android.lvrn.lvrnproject.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreate.impl.NotebookCreateDialogFragmentImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class NotebookCreateViewAdapter extends RecyclerView.Adapter<NotebookCreateViewAdapter.NotebookCreateViewHolder> {

    private List<Notebook> mNotebook = new ArrayList<>();
    private NotebookCreateDialogFragmentImpl mNotebookCreateDialogFragment;

    public NotebookCreateViewAdapter(List<Notebook> mNotebook, NotebookCreateDialogFragmentImpl mNotebookCreateDialogFragment) {
        this.mNotebook = mNotebook;
        this.mNotebookCreateDialogFragment = mNotebookCreateDialogFragment;
    }


    @Override
    public NotebookCreateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notebook_create, parent, false);
        return new NotebookCreateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotebookCreateViewHolder holder, int position) {
        holder.tvTitle.setText(mNotebook.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return mNotebook.size();
    }

    class NotebookCreateViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_view_notebook_name)
        TextView tvTitle;

        public NotebookCreateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }
}
