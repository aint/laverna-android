package com.github.android.lvrn.lvrnproject.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class NotebookRecyclerViewAdapter  extends RecyclerView.Adapter<NotebookRecyclerViewAdapter.NotebookViewHolder> {
    private List<Notebook> mNotebookData = new ArrayList<>();
    private NotesRecyclerViewAdapter.ItemClickListener mItemClickListener;

    public NotebookRecyclerViewAdapter(List<Notebook> mNotebookData) {
        this.mNotebookData = mNotebookData;
    }

    @Override
    public NotebookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notebook,parent,false);
        return new NotebookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotebookViewHolder holder, int position) {
        holder.tvTitle.setText(mNotebookData.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mNotebookData.size();
    }

    public void setClickListener(NotesRecyclerViewAdapter.ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    class NotebookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.text_view_notebook_name)
        TextView tvTitle;
        @BindView(R.id.image_btn_more_menu)
        ImageButton menu;

        public NotebookViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) mItemClickListener.onClick(v, getAdapterPosition());
        }

    }
}
