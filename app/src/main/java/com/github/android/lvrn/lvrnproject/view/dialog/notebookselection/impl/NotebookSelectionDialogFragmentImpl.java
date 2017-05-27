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
import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.view.adapters.NotebookSelectionRecyclerViewAdapter;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.NotebookSelectionDialogFragment;
import com.github.android.lvrn.lvrnproject.view.listeners.RecyclerViewOnScrollListener;
import com.github.android.lvrn.lvrnproject.view.listeners.RecyclerViewOnScrollListener.PaginationParams;
import com.github.android.lvrn.lvrnproject.view.util.CurrentState;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NotebookSelectionDialogFragmentImpl extends DialogFragment implements NotebookSelectionDialogFragment {

    public static final String RECYCLER_VIEW_STATE = "recycler_view_state";

    @Inject NotebookService mNotebookService;

    @BindView(R.id.recycler_view_notebook_selection) RecyclerView mNotebookSelectionRecyclerView;

    private Parcelable mRecyclerViewState;

//    private NotebookSelectionPresenter mNotebookSelectionPresenter;

    private NotebookSelectionRecyclerViewAdapter mNotebookSelectionRecyclerViewAdapter;

    private LinearLayoutManager mLinearLayoutManager;

    private List<Notebook> mNotebooks;

    private ReplaySubject<PaginationParams> mPaginationParamsReplaySubject;

    private Unbinder mUnbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_notebooks_list, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        LavernaApplication.getsAppComponent().inject(this);
        initPaginationDisposable();

        mNotebookService.openConnection();

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
        if (mRecyclerViewState != null) {
            mLinearLayoutManager.onRestoreInstanceState(mRecyclerViewState);
        }
    }

    private void setUpNotebookSelectionRecyclerView() {
        mNotebookSelectionRecyclerView.setHasFixedSize(true);

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mNotebookSelectionRecyclerView.setLayoutManager(mLinearLayoutManager);

        mNotebooks = mNotebookService.getByProfile(CurrentState.profileId, 1, 15);

        mNotebookSelectionRecyclerView.addOnScrollListener(
                new RecyclerViewOnScrollListener(mPaginationParamsReplaySubject));

        mNotebookSelectionRecyclerViewAdapter = new NotebookSelectionRecyclerViewAdapter(mNotebooks);

        mNotebookSelectionRecyclerView.setAdapter(mNotebookSelectionRecyclerViewAdapter);
    }

    private void initPaginationDisposable() {
        mPaginationParamsReplaySubject = ReplaySubject.create();
        mPaginationParamsReplaySubject
                .observeOn(Schedulers.io())
                .map(this::loadMoreNotebooks)
                .filter(this::isNotebooksListNotEmpty)
                .map(newNotebooks -> mNotebooks.addAll(newNotebooks))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> updateRecyclerView(),
                        throwable -> {/*TODO: find out what can happen here*/});
    }

    private List<Notebook> loadMoreNotebooks(PaginationParams params) {
        List<Notebook> notebooks = mNotebookService.getByProfile(CurrentState.profileId, params.offset, params.limit);
        System.out.println(notebooks);
        return notebooks;
    }

    private boolean isNotebooksListNotEmpty(List<Notebook> notebooks) {
        return !notebooks.isEmpty();
    }

    @Override
    public void updateRecyclerView() {
        mNotebookSelectionRecyclerViewAdapter.notifyDataSetChanged();
        Logger.d("Recycler view is updated");
    }

    @Override
    public void onStop() {
        mNotebookService.closeConnection();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }
}
