package com.github.android.lvrn.lvrnproject.view.fragment.notecontent;


import android.os.Build;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.databinding.FragmentNoteContentBinding;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.view.dialog.tagediting.TagEditingDialogFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.notedetails.NoteDetailsFragmentImpl;
import com.github.valhallalabs.laverna.persistent.entity.Note;
import com.github.valhallalabs.laverna.persistent.entity.Notebook;

import javax.inject.Inject;

import static com.github.android.lvrn.lvrnproject.view.util.consts.BundleKeysConstKt.BUNDLE_NOTEBOOK_NAME_KEY;
import static com.github.android.lvrn.lvrnproject.view.util.consts.BundleKeysConstKt.BUNDLE_NOTE_CREATED_KEY;
import static com.github.android.lvrn.lvrnproject.view.util.consts.BundleKeysConstKt.BUNDLE_NOTE_ID_KEY;
import static com.github.android.lvrn.lvrnproject.view.util.consts.BundleKeysConstKt.BUNDLE_NOTE_OBJECT_KEY;
import static com.github.android.lvrn.lvrnproject.view.util.consts.BundleKeysConstKt.BUNDLE_NOTE_UPDATED_KEY;
import static com.github.android.lvrn.lvrnproject.view.util.consts.FragmentConstKt.TAG_NOTE_DETAIL_FRAGMENT;
import static com.github.android.lvrn.lvrnproject.view.util.consts.FragmentConstKt.TAG_TAG_EDITING_DIALOG_FRAGMENT;


/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class NoteContentFragmentImpl extends Fragment {

    @Inject
    NotebookService mNotebookService;
    private Note mSelectNote;
    private String mNoteBookName;

    private FragmentNoteContentBinding fragmentNotebookContentBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentNotebookContentBinding = FragmentNoteContentBinding.inflate(inflater, container, false);
        LavernaApplication.getsAppComponent().inject(this);
        setUpToolbar();
        getParcelableDataAndSetInView();
        return fragmentNotebookContentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentNotebookContentBinding.imBtnInformation.setOnClickListener(view1 -> startNoteDetailFragment());
        fragmentNotebookContentBinding.imBtnArrowBackSingleNote.setOnClickListener(view1 -> backToPreviousFragment());
        fragmentNotebookContentBinding.tvTagSingleNote.setOnClickListener(view1 -> openTagEditingDialog());
    }

    /**
     * A method which creates new fragment and set arguments with bundle to this fragment
     */
    public void startNoteDetailFragment() {
        NoteDetailsFragmentImpl noteDetailsFragment = new NoteDetailsFragmentImpl();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_NOTEBOOK_NAME_KEY, mNoteBookName);
        bundle.putLong(BUNDLE_NOTE_UPDATED_KEY, mSelectNote.getUpdateTime());
        bundle.putLong(BUNDLE_NOTE_CREATED_KEY, mSelectNote.getCreationTime());
        noteDetailsFragment.setArguments(bundle);
        openSelectFragment(noteDetailsFragment);
    }

    /**
     * A method which hears when user click on button and goes one fragment below from current
     */
    public void backToPreviousFragment() {
        getActivity().onBackPressed();
    }

    /**
     * A method which hears when user click on button and replaces to container defined fragment
     * and set arguments with bundle to this fragment
     */
    public void openTagEditingDialog() {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null);
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_NOTE_ID_KEY, mSelectNote.getId());
        DialogFragment dialogFragment = new TagEditingDialogFragmentImpl();
        dialogFragment.setArguments(bundle);
        dialogFragment.show(fragmentTransaction, TAG_TAG_EDITING_DIALOG_FRAGMENT);
    }

    /**
     * A method which replaces to container defined fragment with transition animation,
     * according as which device configuration
     *
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
                    .replace(R.id.constraint_container, fragment, TAG_NOTE_DETAIL_FRAGMENT)
                    .addSharedElement(fragmentNotebookContentBinding.imBtnInformation, fragmentNotebookContentBinding.imBtnInformation.getTransitionName())
                    .addToBackStack(null)
                    .commit();
            return;
        }
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .replace(R.id.constraint_container, fragment, TAG_TAG_EDITING_DIALOG_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    /**
     * A method which gets data from bundle and set them in defined view element
     */
    private void getParcelableDataAndSetInView() {
        mSelectNote = getArguments().getParcelable(BUNDLE_NOTE_OBJECT_KEY);
        if (mSelectNote.getNotebookId() != null && !mSelectNote.getNotebookId().isEmpty()) {
            mNotebookService.openConnection();
            //TODO: clean it, use ifPresent method on Optional.
            Notebook selectNotebook = mNotebookService.getById(mSelectNote.getNotebookId()).get();
            mNotebookService.closeConnection();
            mNoteBookName = selectNotebook.getName();
            fragmentNotebookContentBinding.tvNameNoteBook.setText(mNoteBookName);
        }
        fragmentNotebookContentBinding.editTextTitleSingleNote.setText(mSelectNote.getTitle());
        fragmentNotebookContentBinding.editTextMainContentForNote.setText(mSelectNote.getContent());
    }

    /**
     * A method which sets defined view of main toolbar
     */
    private void setUpToolbar() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

}
