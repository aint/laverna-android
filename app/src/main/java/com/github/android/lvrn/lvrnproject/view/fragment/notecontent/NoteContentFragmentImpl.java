package com.github.android.lvrn.lvrnproject.view.fragment.notecontent;


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
import com.github.valhallalabs.laverna.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.view.dialog.tagediting.TagEditingDialogFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.notedetails.NoteDetailsFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.util.consts.BundleKeysConst;
import com.github.android.lvrn.lvrnproject.view.util.consts.FragmentConst;
import com.github.valhallalabs.laverna.persistent.entity.Note;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class NoteContentFragmentImpl extends Fragment {
    @BindView(R.id.im_btn_information) ImageButton mImageButtonInfo;
    @BindView(R.id.im_btn_arrow_back_single_note) ImageButton mImageButtonBackArrow;
    @BindView(R.id.edit_text_title_single_note) EditText mEditTextTitle;
    @BindView(R.id.tv_name_note_book) TextView mTextViewNotebookName;
    @BindView(R.id.edit_text_main_content_for_note) EditText mEditTextContentNote;
    @Inject NotebookService mNotebookService;
    private Note mSelectNote;
    private String mNoteBookName;
    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_note_content, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        LavernaApplication.getsAppComponent().inject(this);
        setUpToolbar();
        getParcelableDataAndSetInView();
        return rootView;
    }

    /**
     * A method which creates new fragment and set arguments with bundle to this fragment
     */
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

    /**
     * A method which hears when user click on button and goes one fragment below from current
     */
    @OnClick(R.id.im_btn_arrow_back_single_note)
    public void backToPreviousFragment() {
        getActivity().onBackPressed();
    }

    /**
     * A method which hears when user click on button and replaces to container defined fragment
     * and set arguments with bundle to this fragment
     */
    @OnClick(R.id.tv_tag_single_note)
    public void openTagEditingDialog() {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null);
        Bundle bundle =new Bundle();
        bundle.putString(BundleKeysConst.BUNDLE_NOTE_ID_KEY,mSelectNote.getId());
        DialogFragment dialogFragment = new TagEditingDialogFragmentImpl();
        dialogFragment.setArguments(bundle);
        dialogFragment.show(fragmentTransaction, FragmentConst.TAG_TAG_EDITING_DIALOG_FRAGMENT);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null){
            mUnbinder.unbind();
        }
    }

    /**
     *   A method which replaces to container defined fragment with transition animation,
     *   according as which device configuration
     * @param fragment a fragment what replaces other and
     */
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
                    .replace(R.id.constraint_container, fragment, FragmentConst.TAG_NOTE_DETAIL_FRAGMENT)
                    .addSharedElement(mImageButtonInfo, mImageButtonInfo.getTransitionName())
                    .addToBackStack(null)
                    .commit();
            return;
        }
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.constraint_container, fragment, FragmentConst.TAG_TAG_EDITING_DIALOG_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    /**
     * A method which gets data from bundle and set them in defined view element
     */
    private void getParcelableDataAndSetInView() {
         mSelectNote = getArguments().getParcelable(BundleKeysConst.BUNDLE_NOTE_OBJECT_KEY);
        if (mSelectNote.getNotebookId()!= null && !mSelectNote.getNotebookId().isEmpty()) {
            mNotebookService.openConnection();
            //TODO: clean it, use ifPresent method on Optional.
            Notebook selectNotebook = mNotebookService.getById(mSelectNote.getNotebookId()).get();
            mNotebookService.closeConnection();
            mNoteBookName = selectNotebook.getName();
            mTextViewNotebookName.setText(mNoteBookName);
        }
        mEditTextTitle.setText(mSelectNote.getTitle());
        mEditTextContentNote.setText(mSelectNote.getContent());
    }

    /**
     * A method which sets defined view of main toolbar
     */
    private void setUpToolbar() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

}
