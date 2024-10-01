package com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.android.lvrn.lvrnproject.databinding.ItemTagBinding
import com.github.valhallalabs.laverna.persistent.entity.Tag

//TODO: FIX!
class TagsListAdapter(private val mDataSet : List<Tag>) : RecyclerView.Adapter<TagsListAdapter.TagEditingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagEditingViewHolder {
        return TagEditingViewHolder(
            ItemTagBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    //TODO change after implements DAO
    override fun onBindViewHolder(holder: TagEditingViewHolder, position: Int) {
        holder.itemTagBinding.checkedTextViewTags.text = mDataSet[position].name
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    class TagEditingViewHolder(var itemTagBinding: ItemTagBinding) :
        RecyclerView.ViewHolder(itemTagBinding.getRoot()) {
    }
}