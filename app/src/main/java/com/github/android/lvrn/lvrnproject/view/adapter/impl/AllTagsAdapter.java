package com.github.android.lvrn.lvrnproject.view.adapter.impl;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Tag;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */
//TODO: FIX!
public class AllTagsAdapter extends RecyclerView.Adapter<AllTagsAdapter.TagEditingViewHolder>{
    private List<Tag> mDataSet;

    public AllTagsAdapter(List<Tag> data) {
        mDataSet = data;
    }

    @Override
    public TagEditingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag, parent, false);
        return new TagEditingViewHolder(view);
    }

    //TODO change after implements DAO
    @Override
    public void onBindViewHolder(TagEditingViewHolder holder, int position) {
        holder.textViewTag.setText(mDataSet.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    static class TagEditingViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.checked_text_view_tags)
        TextView textViewTag;

        TagEditingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
