package com.github.android.lvrn.lvrnproject.view.fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.service.extension.NotebookService;
import com.github.android.lvrn.lvrnproject.view.dialog.TagEditingDialogFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class SingleNoteFragment extends Fragment {
    @BindView(R.id.im_btn_information) ImageButton mImageButtonInfo;
    @BindView(R.id.im_btn_arrow_back_single_note) ImageButton mImageButtonBackArrow;
    @BindView(R.id.edit_text_title_single_note) EditText mEditTextTitle;
    @BindView(R.id.tv_name_note_book) TextView mTextViewNotebookName;
    @BindView(R.id.edit_text_main_content_for_note) EditText mEditTextContentNote;
    @Inject NotebookService notebookService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_single_note, container, false);
        ButterKnife.bind(this, rootView);
        reInitBaseView();
        getParcelableDataAndSetInView();
        return rootView;
    }

    @OnClick(R.id.im_btn_information)
    public void startNoteDetailFragment() {
        NoteDetailsFragment noteDetailsFragment = new NoteDetailsFragment();
        openSelectFragment(noteDetailsFragment);
    }

    @OnClick(R.id.im_btn_arrow_back_single_note)
    public void backToPreviousFragment() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.tv_tag_single_note)
    public void openTagEditingDialog() {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null);
        DialogFragment dialogFragment = TagEditingDialogFragment.newInstance();
        dialogFragment.show(fragmentTransaction, "dialog");
    }

    public void openSelectFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slideTransition = new Slide(Gravity.RIGHT);
            slideTransition.setDuration(1000);
            ChangeBounds changeBounds = new ChangeBounds();
            changeBounds.setDuration(1000);
            fragment.setEnterTransition(slideTransition);
            fragment.setReturnTransition(null);
            fragment.setAllowEnterTransitionOverlap(true);
            fragment.setSharedElementEnterTransition(changeBounds);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.constraint_container, fragment)
                    .addSharedElement(mImageButtonInfo, mImageButtonInfo.getTransitionName())
                    .addToBackStack(null)
                    .commit();
            return;
        }
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.constraint_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void getParcelableDataAndSetInView() {
        Note selectNote = getArguments().getParcelable("noteForm");
        if (selectNote.getNotebookId() != null) {
            notebookService.openConnection();
            Notebook selectNotebook = notebookService.getById(selectNote.getNotebookId()).get();
            notebookService.closeConnection();
            mTextViewNotebookName.setText(selectNotebook.getName());
        }
        mEditTextTitle.setText(selectNote.getTitle());
        mEditTextContentNote.setText(selectNote.getContent());
    }

    private void reInitBaseView() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

}
