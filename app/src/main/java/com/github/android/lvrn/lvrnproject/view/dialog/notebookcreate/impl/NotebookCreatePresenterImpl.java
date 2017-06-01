package com.github.android.lvrn.lvrnproject.view.dialog.notebookcreate.impl;

import android.support.v7.widget.RecyclerView;

import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.service.form.NotebookForm;
import com.github.android.lvrn.lvrnproject.util.CurrentState;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreate.NotebookCreateDialogFragment;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreate.NotebookCreatePresenter;
import com.github.android.lvrn.lvrnproject.view.listener.RecyclerViewOnScrollListener;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class NotebookCreatePresenterImpl implements NotebookCreatePresenter {

    private List<Notebook> mNotebookData;
    private NotebookService mNotebookService;
    private NotebookCreateDialogFragment mNotebookCreateDialogFragment;
    private RecyclerViewOnScrollListener mRecyclerViewOnScrollLister;
    private ReplaySubject<PaginationArgs> mPaginationSubject;
    private Disposable mPaginationDisposable;
    private String parentId;


    public NotebookCreatePresenterImpl(NotebookService mNotebookService) {
        (this.mNotebookService = mNotebookService).openConnection();
    }

    @Override
    public List<Notebook> getNotebooksForAdapter() {
        mNotebookData = mNotebookService.getByProfile(CurrentState.profileId, new PaginationArgs());
        return mNotebookData;
    }

    @Override
    public void bindView(NotebookCreateDialogFragment notebookCreateDialogFragment) {
        mNotebookCreateDialogFragment = notebookCreateDialogFragment;
        if (!mNotebookService.isConnectionOpened()) {
            mNotebookService.openConnection();
        }
    }

    @Override
    public void unbindView() {
        if (mNotebookCreateDialogFragment != null) {
            mNotebookCreateDialogFragment = null;
        }
        mNotebookService.closeConnection();
    }

    @Override
    public void createNotebook(String name) {
        mNotebookService.create(new NotebookForm(CurrentState.profileId, false, parentId, name));
        mNotebookCreateDialogFragment.updateRecyclerView();
    }

    @Override
    public void subscribeRecyclerViewForPagination(RecyclerView recyclerView) {
        initPaginationSubject();
        mRecyclerViewOnScrollLister = new RecyclerViewOnScrollListener(mPaginationSubject);
        recyclerView.addOnScrollListener(mRecyclerViewOnScrollLister);

    }

    @Override
    public void disposePaginationAndSearch() {
        if (mPaginationDisposable != null && !mPaginationDisposable.isDisposed()) {
            mPaginationDisposable.dispose();
        }
    }

    @Override
    public void getNotebookId(String notebookId) {
        if (notebookId != null && notebookId.isEmpty()) {
            parentId = notebookId;
        }
    }

    private void initPaginationSubject() {
        mPaginationDisposable = (mPaginationSubject = ReplaySubject.create())
                .observeOn(Schedulers.io())
                .map(paginationArgs -> mNotebookService.getByProfile(CurrentState.profileId, paginationArgs))
                .filter(notes -> !notes.isEmpty())
                .map(newNotes -> mNotebookData.addAll(newNotes))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> mNotebookCreateDialogFragment.updateRecyclerView(),
                        throwable -> {/*TODO: find out what can happen here*/});
    }
}
