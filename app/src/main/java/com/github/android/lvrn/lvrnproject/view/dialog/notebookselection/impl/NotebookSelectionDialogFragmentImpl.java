package com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.impl;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.view.activity.noteeditor.impl.NoteEditorActivityImpl;
import com.github.android.lvrn.lvrnproject.view.adapter.NotebookSelectionAdapter;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreation.impl.NotebookCreationDialogFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.NotebookSelectionDialogFragment;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.NotebookSelectionPresenter;
import com.github.android.lvrn.lvrnproject.view.util.consts.BundleKeysConst;
import com.github.android.lvrn.lvrnproject.view.util.consts.FragmentConst;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NotebookSelectionDialogFragmentImpl extends DialogFragment implements NotebookSelectionDialogFragment {

    public static final String RECYCLER_VIEW_STATE = "recycler_view_state";
//    public static final String DIALOG_TITLE = "Notebooks";

    @Inject
    NotebookService mNotebookService;

    @BindView(R.id.recycler_view_notebooks_dialog_fragment_selection)
    RecyclerView mNotebooksRecyclerView;

    @BindView(R.id.tv_create_notebook_dialog_dialog_fragment_selection)
    TextView mTextViewNotebookName;

    private Parcelable mRecyclerViewState;

    private NotebookSelectionPresenter mNotebookSelectionPresenter;

    private NotebookSelectionAdapter mNotebooksRecyclerViewAdapter;

    private LinearLayoutManager mLinearLayoutManager;

    private Unbinder mUnbinder;

    private Notebook mNotebook;

    private Notebook mSelectedNotebook;

    public static NotebookSelectionDialogFragmentImpl newInstance(Notebook notebook) {
        NotebookSelectionDialogFragmentImpl notebookSelectionDialogFragment = new NotebookSelectionDialogFragmentImpl();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BundleKeysConst.BUNDLE_DIALOG_NOTEBOOK_SELECTION_KEY, notebook);

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
        View view = inflater.inflate(R.layout.dialog_fragment_notebook_selection, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        LavernaApplication.getsAppComponent().inject(this);

        mNotebookSelectionPresenter = new NotebookSelectionPresenterImpl(mNotebookService);

        mNotebook = getArguments().getParcelable(BundleKeysConst.BUNDLE_DIALOG_NOTEBOOK_SELECTION_KEY);

        initRecyclerView();

        initTextView();


//        getDialog().setTitle(DIALOG_TITLE);

        return view;
    }

    private void initTextView() {
        if (mNotebook == null){
        }else mTextViewNotebookName.setText(mNotebook.getName());
    }

    @OnClick(R.id.btn_create_notebook_dialog_fragment_selection)
    public void createNotebook() {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null);
        DialogFragment dialogFragment = NotebookCreationDialogFragmentImpl.newInstance(FragmentConst.DIALOG_OPEN_FROM_NOTEBOOK_SELECTION_DIALOG_FRAGMENT);
        dialogFragment.show(fragmentTransaction, FragmentConst.TAG_NOTEBOOK_CREATE_FRAGMENT);
    }

    @OnClick(R.id.btn_ok_dialog_fragment_selection)
    public void acceptChanges() {
        ((NoteEditorActivityImpl) getActivity()).setNoteNotebooks(mSelectedNotebook);
        getDialog().dismiss();
    }

    @OnClick(R.id.btn_reset_notebook_dialog_fragment_selection)
    public void resetChanges() {
        ((NoteEditorActivityImpl) getActivity()).setNoteNotebooks(null);
        getDialog().dismiss();
    }

    @OnClick(R.id.btn_cancel_dialog_fragment_selection)
    public void cancelDialog(){
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
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        mNotebookSelectionPresenter.disposePagination();
    }

    /**
     * A method which makes set up of a recycler view with notebooks.
     */
    private void initRecyclerView() {
        mNotebooksRecyclerView.setHasFixedSize(true);

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mNotebooksRecyclerView.setLayoutManager(mLinearLayoutManager);

        mNotebooksRecyclerViewAdapter = new NotebookSelectionAdapter(
                this, mNotebookSelectionPresenter.getNotebooksForAdapter());

        mNotebooksRecyclerView.setAdapter(mNotebooksRecyclerViewAdapter);

        mNotebookSelectionPresenter.subscribeRecyclerViewForPagination(mNotebooksRecyclerView);

    }
}
