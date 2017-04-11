package com.github.android.lvrn.lvrnproject.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class AllNotesAdapter extends RecyclerView.Adapter<> {

    public static  class AllNotesViewHolder extends RecyclerView.ViewHolder{


        public AllNotesViewHolder(View itemView) {
            super(itemView);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
