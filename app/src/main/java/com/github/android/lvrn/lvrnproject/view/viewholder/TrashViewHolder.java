package com.github.android.lvrn.lvrnproject.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.android.lvrn.lvrnproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class TrashViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_title_note) public TextView tvTitle;

    @BindView(R.id.tv_date_created_note) public TextView tvDate;

    @BindView(R.id.tv_prompt_text_note) public TextView tvPromptText;

    @BindView(R.id.im_btn_favorite) public ImageButton imBtnFavorite;

    public TrashViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}