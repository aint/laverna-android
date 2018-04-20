package com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.impl;

import android.support.v7.widget.RecyclerView;

import com.github.valhallalabs.laverna.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.NotebookSelectionDialogFragment;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.NotebookSelectionPresenter;
import com.github.android.lvrn.lvrnproject.view.listener.RecyclerViewOnScrollListener;
import com.github.android.lvrn.lvrnproject.util.CurrentState;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

class NotebookSelectionPresenterImpl implements NotebookSelectionPresenter {

    private NotebookSelectionDialogFragment mNotebookSelectionDialogFragment;

    private NotebookService mNotebookService;

    private Disposable mPaginationDisposable;

    private List<Notebook> mNotebooks;

    NotebookSelectionPresenterImpl(NotebookService mNotebookService) {
        (this.mNotebookService = mNotebookService).openConnection();
    }

    @Override
    public void bindView(NotebookSelectionDialogFragment notebookSelectionDialogFragment) {
        mNotebookSelectionDialogFragment = notebookSelectionDialogFragment;
        if (!mNotebookService.isConnectionOpened()) {
            mNotebookService.openConnection();
        }
    }

    @Override
    public void unbindView() {
        mNotebookService.closeConnection();
    }

    @Override
    public void subscribeRecyclerViewForPagination(RecyclerView recyclerView) {
        ReplaySubject<PaginationArgs> paginationReplaySubject;

        mPaginationDisposable = (paginationReplaySubject = ReplaySubject.create())
                .observeOn(Schedulers.io())
                .map(paginationArgs -> mNotebookService.getByProfile(CurrentState.profileId, paginationArgs))
                .filter(notebooks -> !notebooks.isEmpty())
                .map(newNotebooks -> mNotebooks.addAll(newNotebooks))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> mNotebookSelectionDialogFragment.updateRecyclerView(),
                        throwable -> {/*TODO: find out what can happen here*/});

        recyclerView.addOnScrollListener(new RecyclerViewOnScrollListener(paginationReplaySubject));
    }

    @Override
    public void disposePagination() {
        if( mPaginationDisposable != null && !mPaginationDisposable.isDisposed()) {
            mPaginationDisposable.dispose();
        }
    }

    @Override
    public List<Notebook> getNotebooksForAdapter() {
        mNotebooks = mNotebookService.getByProfile(CurrentState.profileId, new PaginationArgs());
        return mNotebooks;
    }
}
