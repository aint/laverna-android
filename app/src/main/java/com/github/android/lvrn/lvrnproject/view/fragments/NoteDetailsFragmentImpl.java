package com.github.android.lvrn.lvrnproject.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.view.util.consts.BundleKeysConst;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class NoteDetailsFragmentImpl extends Fragment {
    @BindView(R.id.tv_update_date_detail_note) TextView mTextViewUpdateNote;
    @BindView(R.id.tv_create_date_detail_note) TextView mTextViewCreateNote;
    @BindView(R.id.tv_notebook_name_detail_note) TextView mTextViewNotebookName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_note_details, container, false);
        ButterKnife.bind(this, rootView);
        reInitBaseView();
        getParcelableDataAndSetInView();
        return rootView;
    }

    /**
     * A method which hears when user click on button and goes one fragment below from current
     */
    @OnClick(R.id.im_btn_arrow_back)
    public void backToPreviousFragment() {
        getActivity().onBackPressed();
    }

    /**
     * A method which sets defined view of main toolbar
     */
    private void reInitBaseView() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    /**
     * A method which gets data from fragment argument bundles and sets their in defined views
     */
    private void getParcelableDataAndSetInView() {
        mTextViewUpdateNote.setText(String.valueOf(getArguments().getLong(BundleKeysConst.BUNDLE_NOTE_UPDATED_KEY)));
        mTextViewCreateNote.setText(String.valueOf(getArguments().getLong(BundleKeysConst.BUNDLE_NOTE_CREATED_KEY)));
        mTextViewNotebookName.setText(getArguments().getString(BundleKeysConst.BUNDLE_NOTEBOOK_NAME_KEY));
    }

}
