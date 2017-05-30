package com.github.android.lvrn.lvrnproject.view.dialog.tagediting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Tag;
import com.github.android.lvrn.lvrnproject.service.core.TagService;
import com.github.android.lvrn.lvrnproject.view.adapter.TagRecyclerViewAdapter;
import com.github.android.lvrn.lvrnproject.view.util.consts.BundleKeysConst;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class TagEditingDialogFragmentImpl extends DialogFragment {
    @BindView(R.id.recycler_view_all_tags) RecyclerView mRecyclerViewTags;
    @Inject TagService mTagService;
    private List<Tag> mTagListDate = new ArrayList<>();

    public TagEditingDialogFragmentImpl() {
    }

    public static TagEditingDialogFragmentImpl newInstance() {

        return new TagEditingDialogFragmentImpl();
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
        LavernaApplication.getsAppComponent().inject(this);
        initRecyclerView();
        initTagData();
        return root;
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewTags.setLayoutManager(layoutManager);
        TagRecyclerViewAdapter tagEditingAdapter = new TagRecyclerViewAdapter(mTagListDate);
        mRecyclerViewTags.setAdapter(tagEditingAdapter);
    }

    private void initTagData() {
        mTagService.openConnection();
        mTagListDate.addAll(mTagService.getByNote(getArguments().getString(BundleKeysConst.BUNDLE_NOTE_ID_KEY)));
        mTagService.closeConnection();
    }

}
