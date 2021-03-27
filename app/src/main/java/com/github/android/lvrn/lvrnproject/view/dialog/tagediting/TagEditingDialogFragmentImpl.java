package com.github.android.lvrn.lvrnproject.view.dialog.tagediting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.databinding.DialogFragmentTagEditingBinding;
import com.github.android.lvrn.lvrnproject.service.core.TagService;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl.TagsListAdapter;
import com.github.android.lvrn.lvrnproject.view.util.consts.BundleKeysConst;
import com.github.valhallalabs.laverna.persistent.entity.Tag;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class TagEditingDialogFragmentImpl extends DialogFragment {

    @Inject
    TagService mTagService;
    private List<Tag> mTagListDate = new ArrayList<>();
    private DialogFragmentTagEditingBinding dialogFragmentTagEditingBinding;

    public TagEditingDialogFragmentImpl() {
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dialogFragmentTagEditingBinding = DialogFragmentTagEditingBinding.inflate(inflater, container, false);
        LavernaApplication.getsAppComponent().inject(this);
        initRecyclerView();
        initTagData();
        return dialogFragmentTagEditingBinding.getRoot();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView recyclerViewAllTags = dialogFragmentTagEditingBinding.recyclerViewAllTags;
        recyclerViewAllTags.setLayoutManager(layoutManager);
        TagsListAdapter tagEditingAdapter = new TagsListAdapter(mTagListDate);
        recyclerViewAllTags.setAdapter(tagEditingAdapter);
    }

    private void initTagData() {
        mTagService.openConnection();
        mTagListDate.addAll(mTagService.getByNote(getArguments().getString(BundleKeysConst.BUNDLE_NOTE_ID_KEY)));
        mTagService.closeConnection();
    }

}
