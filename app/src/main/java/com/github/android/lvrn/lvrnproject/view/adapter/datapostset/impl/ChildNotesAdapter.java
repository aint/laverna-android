package com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.NotebookChildrenFragment;
import com.github.valhallalabs.laverna.persistent.entity.Note;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class ChildNotesAdapter extends RecyclerView.Adapter<ChildNotesAdapter.ChildNoteViewHolder> implements DataPostSetAdapter<Note> {

    private NotebookChildrenFragment mNotebookChildrenFragment;

    private List<Note> mNotes;

    public ChildNotesAdapter(NotebookChildrenFragment mNotebookChildrenFragment) {
        this.mNotebookChildrenFragment = mNotebookChildrenFragment;
    }

    @Override
    public ChildNotesAdapter.ChildNoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new ChildNoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChildNotesAdapter.ChildNoteViewHolder holder, int position) {
        holder.tvTitle.setText(mNotes.get(position).getTitle());
        holder.tvPromptText.setText(mNotes.get(position).getContent());

        holder.itemView.setOnClickListener(v -> mNotebookChildrenFragment.showSelectedNote(mNotes.get(position)));
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    @Override
    public void setData(List<Note> data) {
        mNotes = data;
    }

    class ChildNoteViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title_note) TextView tvTitle;

        @BindView(R.id.tv_date_created_note) TextView tvDate;

        @BindView(R.id.tv_prompt_text_note) TextView tvPromptText;

        @BindView(R.id.im_btn_favorite) ImageButton imBtnFavorite;

        ChildNoteViewHolder (View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
