package com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.impl;

import android.support.v7.widget.RecyclerView;

import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.NotebookSelectionDialogFragment;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.NotebookSelectionPresenter;
import com.github.android.lvrn.lvrnproject.view.listeners.RecyclerViewOnScrollListener;
import com.github.android.lvrn.lvrnproject.view.listeners.RecyclerViewOnScrollListener.PaginationParams;
import com.github.android.lvrn.lvrnproject.view.util.CurrentState;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

class NotebookSelectionPresenterImpl implements NotebookSelectionPresenter {

    private NotebookSelectionDialogFragment mNotebookSelectionDialogFragment;

    private NotebookService mNotebookService;

    private ReplaySubject<PaginationParams> mPaginationParamsReplaySubject;

    private List<Notebook> mNotebooks;

    NotebookSelectionPresenterImpl(NotebookService mNotebookService) {
        this.mNotebookService = mNotebookService;
        initPaginationDisposable();
        mNotebookService.openConnection();
    }

    @Override
    public void bindView(NotebookSelectionDialogFragment notebookSelectionDialogFragment) {
        mNotebookSelectionDialogFragment = notebookSelectionDialogFragment;
    }

    @Override
    public void unbindView() {
        mNotebookService.closeConnection();
    }

    @Override
    public void subscribeRecyclerViewForPagination(RecyclerView recyclerView) {
        initPaginationDisposable();
        recyclerView.addOnScrollListener(
                new RecyclerViewOnScrollListener(mPaginationParamsReplaySubject));
    }

    @Override
    public List<Notebook> getNotebooksForAdapter() {
        PaginationParams paginationParams = new PaginationParams();
        mNotebooks = mNotebookService.getByProfile(CurrentState.profileId, paginationParams.offset, paginationParams.limit);
        return mNotebooks;
    }

    private void initPaginationDisposable() {
        mPaginationParamsReplaySubject = ReplaySubject.create();
        mPaginationParamsReplaySubject
                .observeOn(Schedulers.io())
                .map(this::loadMoreNotebooks)
                .filter(this::isNotebooksListNotEmpty)
                .map(newNotebooks -> mNotebooks.addAll(newNotebooks))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> mNotebookSelectionDialogFragment.updateRecyclerView(),
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
}
