package com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.impl;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.view.adapters.NotebookSelectionRecyclerViewAdapter;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.NotebookSelectionDialogFragment;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.NotebookSelectionPresenter;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NotebookSelectionDialogFragmentImpl extends DialogFragment implements NotebookSelectionDialogFragment {

    public static final String RECYCLER_VIEW_STATE = "recycler_view_state";

    @Inject NotebookService mNotebookService;

    @BindView(R.id.recycler_view_notebooks) RecyclerView mNotebooksRecyclerView;

    private Parcelable mRecyclerViewState;

    private NotebookSelectionPresenter mNotebookSelectionPresenter;

    private NotebookSelectionRecyclerViewAdapter mNotebooksRecyclerViewAdapter;

    private LinearLayoutManager mLinearLayoutManager;

    private Unbinder mUnbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_notebook_selection, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        LavernaApplication.getsAppComponent().inject(this);

        mNotebookSelectionPresenter = new NotebookSelectionPresenterImpl(mNotebookService);

        setUpNotebookSelectionRecyclerView();

        return view;
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
    public void onResume() {
        super.onResume();
        mNotebookSelectionPresenter.bindView(this);
        if (mRecyclerViewState != null) {
            mLinearLayoutManager.onRestoreInstanceState(mRecyclerViewState);
        }
    }

    /**
     * A method which makes set up of a recycler view with notebooks.
     */
    private void setUpNotebookSelectionRecyclerView() {
        mNotebooksRecyclerView.setHasFixedSize(true);

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mNotebooksRecyclerView.setLayoutManager(mLinearLayoutManager);

        mNotebooksRecyclerViewAdapter = new NotebookSelectionRecyclerViewAdapter(
                mNotebookSelectionPresenter.getNotebooksForAdapter());
        mNotebooksRecyclerView.setAdapter(mNotebooksRecyclerViewAdapter);

        mNotebookSelectionPresenter.subscribeRecyclerViewForPagination(mNotebooksRecyclerView);
    }

    @Override
    public void updateRecyclerView() {
        mNotebooksRecyclerViewAdapter.notifyDataSetChanged();
        Logger.d("Recycler view is updated");
    }

    @Override
    public void onStop() {
        mNotebookSelectionPresenter.unbindView();
        mNotebookService.closeConnection();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }
}
