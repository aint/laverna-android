package com.github.android.lvrn.lvrnproject.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.android.lvrn.lvrnproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NotebookViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_view_notebook_name) public TextView notebookNameTextView;

    public NotebookViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
