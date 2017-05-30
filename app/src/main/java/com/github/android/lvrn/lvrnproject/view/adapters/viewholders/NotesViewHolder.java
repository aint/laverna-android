package com.github.android.lvrn.lvrnproject.view.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.android.lvrn.lvrnproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class NotesViewHolder extends RecyclerView.ViewHolder {

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
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(TextView tvTitle) {
        this.tvTitle = tvTitle;
    }

    public TextView getTvDate() {
        return tvDate;
    }

    public void setTvDate(TextView tvDate) {
        this.tvDate = tvDate;
    }

    public TextView getTvPromptText() {
        return tvPromptText;
    }

    public void setTvPromptText(TextView tvPromptText) {
        this.tvPromptText = tvPromptText;
    }

    public ImageButton getImBtnFavorite() {
        return imBtnFavorite;
    }

    public void setImBtnFavorite(ImageButton imBtnFavorite) {
        this.imBtnFavorite = imBtnFavorite;
    }

}
