package com.github.android.lvrn.lvrnproject.view.fragment.notedetails;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.github.android.lvrn.lvrnproject.databinding.FragmentNoteDetailsBinding;

import static com.github.android.lvrn.lvrnproject.view.util.TimeUtilKt.convertMillisecondsToString;
import static com.github.android.lvrn.lvrnproject.view.util.consts.BundleKeysConstKt.BUNDLE_NOTEBOOK_NAME_KEY;
import static com.github.android.lvrn.lvrnproject.view.util.consts.BundleKeysConstKt.BUNDLE_NOTE_CREATED_KEY;
import static com.github.android.lvrn.lvrnproject.view.util.consts.BundleKeysConstKt.BUNDLE_NOTE_UPDATED_KEY;


/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class NoteDetailsFragmentImpl extends Fragment {

    private FragmentNoteDetailsBinding fragmentNoteDetailsBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentNoteDetailsBinding = FragmentNoteDetailsBinding.inflate(inflater, container, false);
        setUpToolbar();
        getParcelableDataAndSetInView();
        return fragmentNoteDetailsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentNoteDetailsBinding.imBtnArrowBack.setOnClickListener(view1 -> backToPreviousFragment());
    }

    /**
     * A method which hears when user click on button and goes one fragment below from current
     */
    public void backToPreviousFragment() {
        getActivity().onBackPressed();
    }

    /**
     * A method which sets defined view of main toolbar
     */
    private void setUpToolbar() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    /**
     * A method which gets data from fragment argument bundles and sets their in defined views
     */
    private void getParcelableDataAndSetInView() {
        fragmentNoteDetailsBinding.tvUpdateDate.setText(convertMillisecondsToString(getArguments().getLong(BUNDLE_NOTE_UPDATED_KEY)));
        fragmentNoteDetailsBinding.tvCreateDate.setText(convertMillisecondsToString(getArguments().getLong(BUNDLE_NOTE_CREATED_KEY)));
        fragmentNoteDetailsBinding.tvNotebookNameDetailNote.setText(getArguments().getString(BUNDLE_NOTEBOOK_NAME_KEY));
    }

}
