package com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.impl;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.databinding.DialogFragmentNotebookSelectionBinding;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.view.activity.noteeditor.impl.NoteEditorActivityImpl;
import com.github.android.lvrn.lvrnproject.view.adapter.NotebookSelectionAdapter;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreation.impl.NotebookCreationDialogFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.NotebookSelectionDialogFragment;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.NotebookSelectionPresenter;
import com.github.valhallalabs.laverna.persistent.entity.Notebook;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import static com.github.android.lvrn.lvrnproject.view.util.consts.BundleKeysConstKt.BUNDLE_DIALOG_NOTEBOOK_SELECTION_KEY;
import static com.github.android.lvrn.lvrnproject.view.util.consts.FragmentConstKt.DIALOG_OPEN_FROM_NOTEBOOK_SELECTION_DIALOG_FRAGMENT;
import static com.github.android.lvrn.lvrnproject.view.util.consts.FragmentConstKt.TAG_NOTEBOOK_CREATE_FRAGMENT;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NotebookSelectionDialogFragmentImpl extends DialogFragment implements NotebookSelectionDialogFragment {

    public static final String RECYCLER_VIEW_STATE = "recycler_view_state";
//    public static final String DIALOG_TITLE = "Notebooks";

    @Inject
    NotebookService mNotebookService;

    private Parcelable mRecyclerViewState;

    private NotebookSelectionPresenter mNotebookSelectionPresenter;

    private NotebookSelectionAdapter mNotebooksRecyclerViewAdapter;

    private LinearLayoutManager mLinearLayoutManager;

    private Notebook mNotebook;

    private Notebook mSelectedNotebook;

    private DialogFragmentNotebookSelectionBinding dialogFragmentNotebookSelectionBinding;

    public static NotebookSelectionDialogFragmentImpl newInstance(Notebook notebook) {
        NotebookSelectionDialogFragmentImpl notebookSelectionDialogFragment = new NotebookSelectionDialogFragmentImpl();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_DIALOG_NOTEBOOK_SELECTION_KEY, notebook);

        notebookSelectionDialogFragment.setArguments(bundle);
        return notebookSelectionDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light_Dialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dialogFragmentNotebookSelectionBinding = DialogFragmentNotebookSelectionBinding.inflate(inflater, container, false);
        LavernaApplication.getsAppComponent().inject(this);

        mNotebookSelectionPresenter = new NotebookSelectionPresenterImpl(mNotebookService);

        mNotebook = getArguments().getParcelable(BUNDLE_DIALOG_NOTEBOOK_SELECTION_KEY);

        initRecyclerView();

        initTextView();


//        getDialog().setTitle(DIALOG_TITLE);

        return dialogFragmentNotebookSelectionBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialogFragmentNotebookSelectionBinding.btnCreateNotebookDialogFragmentSelection.setOnClickListener(view1 -> createNotebook());
        dialogFragmentNotebookSelectionBinding.btnOkDialogFragmentSelection.setOnClickListener(view1 -> acceptChanges());
        dialogFragmentNotebookSelectionBinding.btnResetNotebookDialogFragmentSelection.setOnClickListener(view1 -> resetChanges());
        dialogFragmentNotebookSelectionBinding.btnCancelDialogFragmentSelection.setOnClickListener(view1 -> cancelDialog());
    }

    public void createNotebook() {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null);
        DialogFragment dialogFragment = NotebookCreationDialogFragmentImpl.newInstance(DIALOG_OPEN_FROM_NOTEBOOK_SELECTION_DIALOG_FRAGMENT);
        dialogFragment.show(fragmentTransaction, TAG_NOTEBOOK_CREATE_FRAGMENT);
    }

    public void acceptChanges() {
        ((NoteEditorActivityImpl) getActivity()).setNoteNotebooks(mSelectedNotebook);
        getDialog().dismiss();
    }

    public void resetChanges() {
        ((NoteEditorActivityImpl) getActivity()).setNoteNotebooks(null);
        getDialog().dismiss();
    }

    public void cancelDialog() {
        getDialog().dismiss();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mRecyclerViewState = mLinearLayoutManager.onSaveInstanceState();
        outState.putParcelable(RECYCLER_VIEW_STATE, mRecyclerViewState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mRecyclerViewState = savedInstanceState.getParcelable(RECYCLER_VIEW_STATE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onResume() {
        super.onResume();
        mNotebookSelectionPresenter.bindView(this);
        if (mRecyclerViewState != null) {
            mLinearLayoutManager.onRestoreInstanceState(mRecyclerViewState);
        }
    }

    @Override
    public void updateRecyclerView() {
        mNotebooksRecyclerViewAdapter.notifyDataSetChanged();
        Logger.d("Recycler view is updated");
    }

    @Override
    public void setSelectedNotebook(Notebook notebook) {
        mSelectedNotebook = notebook;

    }


    @Override
    public void onPause() {
        super.onPause();
        mNotebookSelectionPresenter.unbindView();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mNotebookSelectionPresenter.disposePagination();
    }

    /**
     * A method which makes set up of a recycler view with notebooks.
     */
    private void initRecyclerView() {
        RecyclerView recyclerViewNotebooksDialogFragmentSelection = dialogFragmentNotebookSelectionBinding.recyclerViewNotebooksDialogFragmentSelection;
        recyclerViewNotebooksDialogFragmentSelection.setHasFixedSize(true);

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewNotebooksDialogFragmentSelection.setLayoutManager(mLinearLayoutManager);

        mNotebooksRecyclerViewAdapter = new NotebookSelectionAdapter(
                this, mNotebookSelectionPresenter.getNotebooksForAdapter());

        recyclerViewNotebooksDialogFragmentSelection.setAdapter(mNotebooksRecyclerViewAdapter);

        mNotebookSelectionPresenter.subscribeRecyclerViewForPagination(recyclerViewNotebooksDialogFragmentSelection);

    }

    private void initTextView() {
        if (mNotebook == null) {
        } else
            dialogFragmentNotebookSelectionBinding.tvCreateNotebookDialogDialogFragmentSelection.setText(mNotebook.getName());
    }
}
