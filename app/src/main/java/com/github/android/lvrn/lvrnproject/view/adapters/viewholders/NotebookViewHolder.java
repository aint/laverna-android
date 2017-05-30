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

public class NotebookViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_view_notebook_name)
    TextView tvTitle;
    @BindView(R.id.image_btn_more_menu)
    ImageButton menu;

    public NotebookViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(TextView tvTitle) {
        this.tvTitle = tvTitle;
    }

    public ImageButton getMenu() {
        return menu;
    }

    public void setMenu(ImageButton menu) {
        this.menu = menu;
    }
}
