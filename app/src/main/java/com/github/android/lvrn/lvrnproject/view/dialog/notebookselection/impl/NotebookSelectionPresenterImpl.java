package com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.impl;

import android.support.v7.widget.RecyclerView;

import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.view.adapters.NotebookSelectionRecyclerViewAdapter;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.NotebookSelectionDialogFragment;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.NotebookSelectionPresenter;
import com.github.android.lvrn.lvrnproject.view.listeners.RecyclerViewOnScrollListener;
import com.github.android.lvrn.lvrnproject.view.listeners.RecyclerViewOnScrollListener.PaginationParams;
import com.github.android.lvrn.lvrnproject.view.util.CurrentState;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Emitter;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NotebookSelectionPresenterImpl implements NotebookSelectionPresenter {

    private NotebookSelectionDialogFragment mNotebookSelectionDialogFragment;

    private NotebookService mNotebookService;

    private Emitter<PaginationParams> mPaginationParamsEmitter;

    private Disposable mPaginationDisposable;

    private List<Notebook> mNotebooks;


    public NotebookSelectionPresenterImpl(NotebookService notebookService) {
        mNotebookService = notebookService;
    }




    @Override
    public void bindView(NotebookSelectionDialogFragment notebookSelectionDialogFragment) {
        mNotebookSelectionDialogFragment = notebookSelectionDialogFragment;
    }

    @Override
    public void unbindView() {
        mNotebookSelectionDialogFragment = null;
    }

    @Override
    public void subscribeRecyclerViewForPagination(RecyclerView recyclerView) {
        initPaginationDisposable();
        recyclerView.addOnScrollListener(new RecyclerViewOnScrollListener(mPaginationParamsEmitter));
    }

    @Override
    public void unsubscribeRecyclerViewForPagination() {
        if(mPaginationDisposable != null && !mPaginationDisposable.isDisposed()) {
            mPaginationDisposable.dispose();
        }
    }

    @Override
    public NotebookSelectionRecyclerViewAdapter getAdapter() {
        return null;
    }

    private void initPaginationDisposable () {
        mPaginationDisposable = Flowable
                .<PaginationParams>create(emitter -> mPaginationParamsEmitter = emitter, BackpressureStrategy.BUFFER)
                .map(this::loadMoreNotebooks)
                .filter(this::isNotebooksListNotEmpty)
                .map(newNotebooks -> mNotebooks.addAll(newNotebooks))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> mNotebookSelectionDialogFragment.updateRecyclerView(),
                        throwable -> {/*TODO: find out what can happen here*/});
    }

    private List<Notebook> loadMoreNotebooks(PaginationParams params) {
        return mNotebookService.getByProfile(CurrentState.profileId, params.offset, params.limit);
    }

    private boolean isNotebooksListNotEmpty(List<Notebook> notebooks) {
        return !notebooks.isEmpty();
    }
}
