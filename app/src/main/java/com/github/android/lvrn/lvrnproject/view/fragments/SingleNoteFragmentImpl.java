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

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.view.dialog.TagEditingDialogFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.util.consts.BundleKeysConst;
import com.github.android.lvrn.lvrnproject.view.util.consts.TagFragmentConst;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class SingleNoteFragmentImpl extends Fragment {
    @BindView(R.id.im_btn_information) ImageButton mImageButtonInfo;
    @BindView(R.id.im_btn_arrow_back_single_note) ImageButton mImageButtonBackArrow;
    @BindView(R.id.edit_text_title_single_note) EditText mEditTextTitle;
    @BindView(R.id.tv_name_note_book) TextView mTextViewNotebookName;
    @BindView(R.id.edit_text_main_content_for_note) EditText mEditTextContentNote;
    @Inject NotebookService mNotebookService;
    private Note mSelectNote;
    private String mNoteBookName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_single_note, container, false);
        ButterKnife.bind(this, rootView);
        LavernaApplication.getsAppComponent().inject(this);
        reInitBaseView();
        getParcelableDataAndSetInView();
        return rootView;
    }

    @OnClick(R.id.im_btn_information)
    public void startNoteDetailFragment() {
        NoteDetailsFragmentImpl noteDetailsFragment = new NoteDetailsFragmentImpl();
        Bundle bundle = new Bundle();
        bundle.putString(BundleKeysConst.BUNDLE_NOTEBOOK_NAME_KEY,mNoteBookName);
        bundle.putLong(BundleKeysConst.BUNDLE_NOTE_UPDATED_KEY,mSelectNote.getUpdateTime());
        bundle.putLong(BundleKeysConst.BUNDLE_NOTE_CREATED_KEY,mSelectNote.getCreationTime());
        noteDetailsFragment.setArguments(bundle);
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
        Bundle bundle =new Bundle();
        bundle.putString(BundleKeysConst.BUNDLE_NOTE_ID_KEY,mSelectNote.getId());
        DialogFragment dialogFragment = TagEditingDialogFragmentImpl.newInstance();
        dialogFragment.setArguments(bundle);
        dialogFragment.show(fragmentTransaction, TagFragmentConst.TAG_TAG_EDITING_DIALOG_FRAGMENT);
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
                    .replace(R.id.constraint_container, fragment,TagFragmentConst.TAG_NOTE_DETAIL_FRAGMENT)
                    .addSharedElement(mImageButtonInfo, mImageButtonInfo.getTransitionName())
                    .addToBackStack(null)
                    .commit();
            return;
        }
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.constraint_container, fragment,TagFragmentConst.TAG_TAG_EDITING_DIALOG_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    private void getParcelableDataAndSetInView() {
         mSelectNote = getArguments().getParcelable(BundleKeysConst.BUNDLE_NOTE_OBJECT_KEY);
        if (mSelectNote.getNotebookId().isPresent()) {
            mNotebookService.openConnection();
            //TODO: clean it, use ifPresent method on Optional.
            Notebook selectNotebook = mNotebookService.getById(mSelectNote.getNotebookId().get()).get();
            mNotebookService.closeConnection();
            mNoteBookName = selectNotebook.getName();
            mTextViewNotebookName.setText(mNoteBookName);
        }
        mEditTextTitle.setText(mSelectNote.getTitle());
        mEditTextContentNote.setText(mSelectNote.getContent());
    }

    /**
     * A method which set defined view of toolbar
     */
    private void reInitBaseView() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

}
