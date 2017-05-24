package com.github.android.lvrn.lvrnproject.view.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.view.adapters.TagEditingRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class TagEditingDialogFragment extends DialogFragment {
    @BindView(R.id.recycler_view_all_tags) RecyclerView mRecyclerViewTags;

    public TagEditingDialogFragment() {
    }

    public static TagEditingDialogFragment newInstance() {
        return new TagEditingDialogFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_fragment_tag_editing, container, false);
        ButterKnife.bind(this, root);
        initRecyclerView();
        return root;
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewTags.setLayoutManager(layoutManager);
        TagEditingRecyclerViewAdapter tagEditingAdapter = new TagEditingRecyclerViewAdapter();
        mRecyclerViewTags.setAdapter(tagEditingAdapter);
    }
}
