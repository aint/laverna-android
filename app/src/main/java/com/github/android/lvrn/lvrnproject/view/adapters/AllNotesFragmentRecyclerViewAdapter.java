package com.github.android.lvrn.lvrnproject.view.adapters;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.view.fragments.SingleNoteFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author Andrii Bei <psihey1@gmail.com>
 */


public class AllNotesFragmentRecyclerViewAdapter extends RecyclerView.Adapter<AllNotesFragmentRecyclerViewAdapter.AllNotesViewHolder> {
    //TODO remove after implements DAO
    private List<Note> mDataSet = new ArrayList<>();
    private Context mContext;

    //TODO change after implements DAO
    public AllNotesFragmentRecyclerViewAdapter(Context mContext,List<Note> data) {
        this.mContext = mContext;
        mDataSet = data;
    }

    @Override
    public AllNotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notes, parent, false);
        AllNotesViewHolder viewHolder = new AllNotesViewHolder(view);
        return viewHolder;
    }

    //TODO change after implements DAO
    @Override
    public void onBindViewHolder(AllNotesViewHolder holder, int position) {
        holder.tvTitle.setText(mDataSet.get(position).getTitle());
        holder.tvPromptText.setText(mDataSet.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    static class AllNotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_title_note) TextView tvTitle;
        @BindView(R.id.tv_date_created_note) TextView tvDate;
        @BindView(R.id.tv_prompt_text_note) TextView tvPromptText;
        @BindView(R.id.im_btn_favorite) ImageButton imBtnFavorite;

        public AllNotesViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        //TODO reBuilding with ButterKnife after implements DAO
        @Override
        public void onClick(View v) {
            AppCompatActivity activity = (AppCompatActivity)itemView.getContext();
            SingleNoteFragment singleNoteFragment = new SingleNoteFragment();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.constraint_container,singleNoteFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

}


