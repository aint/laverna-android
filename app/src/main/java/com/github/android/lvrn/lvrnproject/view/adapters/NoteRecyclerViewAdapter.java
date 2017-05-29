package com.github.android.lvrn.lvrnproject.view.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Note;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class NoteRecyclerViewAdapter extends RecyclerView.Adapter<NoteRecyclerViewAdapter.NotesViewHolder> {

    private ItemClickListener mItemClickListener;
    public List<Note> allNotesData = new ArrayList<>();


    public NoteRecyclerViewAdapter(List<Note> data) {
        allNotesData = data;
    }

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {
        holder.tvTitle.setText(allNotesData.get(position).getTitle());
        holder.tvPromptText.setText(allNotesData.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return allNotesData.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setAllNotesData(List<Note> allNotesData) {
        this.allNotesData = allNotesData;
        notifyDataSetChanged();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_title_note)
        TextView tvTitle;
        @BindView(R.id.tv_date_created_note)
        TextView tvDate;
        @BindView(R.id.tv_prompt_text_note)
        TextView tvPromptText;
        @BindView(R.id.im_btn_favorite)
        ImageButton imBtnFavorite;

        public NotesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) mItemClickListener.onClick(v, getAdapterPosition());
        }

    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

}


