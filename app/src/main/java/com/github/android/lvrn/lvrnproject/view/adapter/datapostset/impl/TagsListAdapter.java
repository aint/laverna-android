package com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.github.android.lvrn.lvrnproject.databinding.ItemTagBinding;
import com.github.valhallalabs.laverna.persistent.entity.Tag;

import java.util.List;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */
//TODO: FIX!
public class TagsListAdapter extends RecyclerView.Adapter<TagsListAdapter.TagEditingViewHolder> {
    private List<Tag> mDataSet;

    public TagsListAdapter(List<Tag> data) {
        mDataSet = data;
    }

    @Override
    public TagEditingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TagEditingViewHolder(ItemTagBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    //TODO change after implements DAO
    @Override
    public void onBindViewHolder(TagEditingViewHolder holder, int position) {
        holder.itemTagBinding.checkedTextViewTags.setText(mDataSet.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    class TagEditingViewHolder extends RecyclerView.ViewHolder {

        ItemTagBinding itemTagBinding;

        TagEditingViewHolder(ItemTagBinding itemTagBinding) {
            super(itemTagBinding.getRoot());
            this.itemTagBinding = itemTagBinding;
        }
    }

}
