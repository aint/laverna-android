package com.github.android.lvrn.lvrnproject.view.dialog.notebookcreation.impl;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.databinding.DialogFragmentNotebookCreateBinding;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.view.activity.noteeditor.impl.NoteEditorActivityImpl;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl.NotebookCreationAdapter;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreation.NotebookCreationDialogFragment;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreation.NotebookCreationPresenter;
import com.github.valhallalabs.laverna.persistent.entity.Notebook;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import static com.github.android.lvrn.lvrnproject.view.util.consts.BundleKeysConstKt.BUNDLE_DIALOG_NOTEBOOK_CREATE_KEY;
import static com.github.android.lvrn.lvrnproject.view.util.consts.FragmentConstKt.DIALOG_OPEN_FROM_MAIN_ACTIVITY;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class NotebookCreationDialogFragmentImpl extends DialogFragment implements NotebookCreationDialogFragment {

    @Inject
    NotebookService mNotebookService;

    private NotebookCreationPresenter mNotebookCreationPresenter;

    private NotebookCreationAdapter mNotebookAdapter;

    private String previousFragmentName;

    private Notebook notebook;

    private DialogFragmentNotebookCreateBinding dialogFragmentNotebookCreateBinding;

    public static NotebookCreationDialogFragmentImpl newInstance(String previousFragment) {
        NotebookCreationDialogFragmentImpl notebookCreateDialogFragment = new NotebookCreationDialogFragmentImpl();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_DIALOG_NOTEBOOK_CREATE_KEY, previousFragment);
        notebookCreateDialogFragment.setArguments(bundle);
        return notebookCreateDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dialogFragmentNotebookCreateBinding = DialogFragmentNotebookCreateBinding.inflate(inflater, container, false);
        LavernaApplication.getsAppComponent().inject(this);
        mNotebookCreationPresenter = new NotebookCreationPresenterImpl(mNotebookService);
        initRecyclerView();
        previousFragmentName = getArguments().getString(BUNDLE_DIALOG_NOTEBOOK_CREATE_KEY);

        return dialogFragmentNotebookCreateBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialogFragmentNotebookCreateBinding.btnCreateNotebookOk.setOnClickListener(view1 -> createNotebook());
        dialogFragmentNotebookCreateBinding.btnCreateNotebookCancel.setOnClickListener(view1 -> cancelDialog());
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onResume() {
        super.onResume();
        mNotebookCreationPresenter.bindView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mNotebookCreationPresenter.unbindView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mNotebookCreationPresenter.disposePaginationAndSearch();
    }

    @Override
    public void updateRecyclerView() {
        mNotebookAdapter.notifyDataSetChanged();
    }

    @Override
    public void getNotebook(Notebook notebook) {
        this.notebook = notebook;
    }

    public void createNotebook() {
        String nameNotebook = dialogFragmentNotebookCreateBinding.editTextNotebookName.getText().toString();
        if (TextUtils.equals(previousFragmentName, DIALOG_OPEN_FROM_MAIN_ACTIVITY)) {
            if (mNotebookCreationPresenter.createNotebook(nameNotebook)) {
                Snackbar.make(getActivity().findViewById(R.id.coordinator_layout_main_activity), "Notebook " + nameNotebook + " has created ", Snackbar.LENGTH_LONG).show();
                getActivity().onBackPressed();
                return;
            }
            Snackbar.make(getActivity().findViewById(R.id.coordinator_layout_main_activity), "Notebook " + nameNotebook + " hasn't created ", Snackbar.LENGTH_LONG).show();
            return;
        }
        if (mNotebookCreationPresenter.createNotebook(nameNotebook)) {
            ((NoteEditorActivityImpl) getActivity()).setNoteNotebooks(notebook);
            Snackbar.make(getActivity().findViewById(R.id.relative_layout_container_activity_note_editor), "Notebook " + nameNotebook + " has created ", Snackbar.LENGTH_LONG).show();
            getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        }
    }

    public void cancelDialog() {
        getActivity().onBackPressed();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView recyclerViewNotebookCreate = dialogFragmentNotebookCreateBinding.recyclerViewNotebookCreate;
        recyclerViewNotebookCreate.setLayoutManager(layoutManager);

        mNotebookAdapter = new NotebookCreationAdapter(mNotebookCreationPresenter);
        mNotebookCreationPresenter.setDataToAdapter(mNotebookAdapter);

        recyclerViewNotebookCreate.setAdapter(mNotebookAdapter);
        mNotebookCreationPresenter.subscribeRecyclerViewForPagination(recyclerViewNotebookCreate);
    }


}
