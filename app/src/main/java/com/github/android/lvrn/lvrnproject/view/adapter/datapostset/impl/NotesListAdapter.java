package com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.core.noteslist.NotesListFragment;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.core.noteslist.NotesListPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NoteViewHolder> implements DataPostSetAdapter<Note> {

    private NotesListFragment mAllNotesFragment;

    private List<Note> mNotes;

    private NotesListPresenter mNoteListPresenter;

    public NotesListAdapter(NotesListFragment allNotesFragment,NotesListPresenter notesListPresenter) {
        mAllNotesFragment = allNotesFragment;
        mNoteListPresenter = notesListPresenter;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Note note = mNotes.get(position);

        if(note.isFavorite()){
          holder.imBtnFavorite.setImageResource(R.drawable.ic_star_black_24dp);
        } else holder.imBtnFavorite.setImageResource(R.drawable.ic_star_white_24dp);

        holder.tvTitle.setText(mNotes.get(position).getTitle());
        holder.tvPromptText.setText(mNotes.get(position).getContent());
        holder.itemView.setOnClickListener(v -> mAllNotesFragment.showSelectedNote(note));
        holder.imBtnFavorite.setOnClickListener(view  -> mNoteListPresenter.changeNoteFavouriteStatus(note,position,view));

    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    @Override
    public void setData(List<Note> data) {
        mNotes = data;
    }

    //TODO: maybe create one class for it.
    class NoteViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title_note) TextView tvTitle;

        @BindView(R.id.tv_date_created_note) TextView tvDate;

        @BindView(R.id.tv_prompt_text_note) TextView tvPromptText;

        @BindView(R.id.im_btn_favorite) ImageButton imBtnFavorite;

        NoteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}


