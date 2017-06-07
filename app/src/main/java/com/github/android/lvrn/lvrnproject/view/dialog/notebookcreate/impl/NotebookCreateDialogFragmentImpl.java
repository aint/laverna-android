package com.github.android.lvrn.lvrnproject.view.dialog.notebookcreate.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.view.activity.noteeditor.impl.NoteEditorActivityImpl;
import com.github.android.lvrn.lvrnproject.view.adapter.NotebookCreateViewAdapter;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreate.NotebookCreateDialogFragment;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreate.NotebookCreatePresenter;
import com.github.android.lvrn.lvrnproject.view.util.consts.BundleKeysConst;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class NotebookCreateDialogFragmentImpl extends DialogFragment implements NotebookCreateDialogFragment {

    @BindView(R.id.recycler_view_notebook_create)
    RecyclerView mRecyclerViewNotebook;

    @BindView(R.id.edit_text_notebook_name)
    EditText mEditText;

    @Inject
    NotebookService mNotebookService;

    private Unbinder mUnbinder;

    private NotebookCreatePresenter mNotebookCreatePresenter;

    private NotebookCreateViewAdapter mNotebookAdapter;

    private String previousFragmentName;

    private Notebook notebook;

    public static NotebookCreateDialogFragmentImpl newInstance(String previousFragment) {
        NotebookCreateDialogFragmentImpl notebookCreateDialogFragment = new NotebookCreateDialogFragmentImpl();
        Bundle bundle = new Bundle();
        bundle.putString(BundleKeysConst.BUNDLE_DIALOG_NOTEBOOK_CREATE_KEY, previousFragment);
        notebookCreateDialogFragment.setArguments(bundle);
        return notebookCreateDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_notebook_create, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        LavernaApplication.getsAppComponent().inject(this);
        mNotebookCreatePresenter = new NotebookCreatePresenterImpl(mNotebookService);
        initRecyclerView();
        if (getArguments().getString(BundleKeysConst.BUNDLE_DIALOG_NOTEBOOK_CREATE_KEY) !=null){
            previousFragmentName = getArguments().getString(BundleKeysConst.BUNDLE_DIALOG_NOTEBOOK_CREATE_KEY);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onResume() {
        super.onResume();
        mNotebookCreatePresenter.bindView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mNotebookCreatePresenter.unbindView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        mNotebookCreatePresenter.disposePaginationAndSearch();
    }

    @Override
    public void updateRecyclerView() {
        mNotebookAdapter.notifyDataSetChanged();
    }

    @Override
    public void getNotebook(Notebook notebook) {
        this.notebook =notebook;
    }

//    @Override
//    public void setSelectedNotebook(Notebook notebook) {
//        if (notebook != null) {
//
//        }
//
//    }

    @OnClick(R.id.btn_create_notebook_ok)
    public void createNotebook() {
        String nameNotebook = mEditText.getText().toString();
        if (TextUtils.equals(previousFragmentName, BundleKeysConst.BUNDLE_DIALOG_NOTEBOOK_CREATE_KEY)) {
            if (mNotebookCreatePresenter.createNotebook(nameNotebook)) {
                Snackbar.make(getActivity().findViewById(R.id.coordinator_layout_main_activity), "Notebook " + nameNotebook + " has created ", Snackbar.LENGTH_LONG).show();
                getActivity().onBackPressed();
                return;
            }
            Snackbar.make(getActivity().findViewById(R.id.coordinator_layout_main_activity), "Notebook " + nameNotebook + " hasn't created ", Snackbar.LENGTH_LONG).show();

        }else {
            if (mNotebookCreatePresenter.createNotebook(nameNotebook)) {
                ((NoteEditorActivityImpl) getActivity()).setNoteNotebooks(notebook);
                Snackbar.make(getActivity().findViewById(R.id.relative_layout_container_activity_note_editor), "Notebook " + nameNotebook + " has created ", Snackbar.LENGTH_LONG).show();
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }}

    }

    @OnClick(R.id.btn_create_notebook_cancel)
    public void cancelDialog() {
        getActivity().onBackPressed();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewNotebook.setLayoutManager(layoutManager);

        mNotebookAdapter = new NotebookCreateViewAdapter(mNotebookCreatePresenter);
        mNotebookCreatePresenter.setDataToAdapter(mNotebookAdapter);

        mRecyclerViewNotebook.setAdapter(mNotebookAdapter);
        mNotebookCreatePresenter.subscribeRecyclerViewForPagination(mRecyclerViewNotebook);
    }



}
