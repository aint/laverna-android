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
import com.github.android.lvrn.lvrnproject.view.fragments.SingleNoteFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Andrii Bei <psihey1@gmail.com>
 */


public class AllNotesFragmentRecyclerViewAdapter extends RecyclerView.Adapter<AllNotesFragmentRecyclerViewAdapter.AllNotesViewHolder> {
    //TODO remove after implements DAO
    private List<String> mDataSet = new ArrayList<>();
    private Context mContext;

    //TODO change after implements DAO
    public AllNotesFragmentRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
        setDataInTempCollections();
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
        holder.tvTitle.setText(mDataSet.get(position));
        holder.tvDate.setText(mDataSet.get(position));
        holder.tvPromptText.setText(mDataSet.get(position));
        holder.tvTag1.setText("#tags1");
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    static class AllNotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        TextView tvDate;
        TextView tvPromptText;
        TextView tvTag1;
        ImageButton imBtnFavorite;

        public AllNotesViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title_note);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date_created_note);
            tvPromptText = (TextView) itemView.findViewById(R.id.tv_prompt_text_note);
            tvTag1 = (TextView) itemView.findViewById(R.id.tv_tags1_note);
            imBtnFavorite = (ImageButton) itemView.findViewById(R.id.im_btn_favorite);
        }

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

    //TODO remove after implements DAO
    private void setDataInTempCollections() {
        mDataSet.add("1");
        mDataSet.add("2");
        mDataSet.add("3");
        mDataSet.add("4");
        mDataSet.add("5");
        mDataSet.add("6");
        mDataSet.add("7");
    }
}


