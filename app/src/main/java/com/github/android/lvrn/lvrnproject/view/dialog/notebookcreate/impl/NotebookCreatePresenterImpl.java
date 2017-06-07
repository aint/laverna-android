package com.github.android.lvrn.lvrnproject.view.dialog.notebookcreate.impl;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.service.form.NotebookForm;
import com.github.android.lvrn.lvrnproject.util.CurrentState;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.github.android.lvrn.lvrnproject.view.adapter.DataPostSetAdapter;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreate.NotebookCreateDialogFragment;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreate.NotebookCreatePresenter;
import com.github.android.lvrn.lvrnproject.view.listener.RecyclerViewOnScrollListener;
import com.google.common.base.Optional;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class NotebookCreatePresenterImpl implements NotebookCreatePresenter {

    private List<Notebook> mNotebooks;
    private NotebookService mNotebookService;
    private NotebookCreateDialogFragment mNotebookCreateDialogFragment;
    private ReplaySubject<PaginationArgs> mPaginationSubject;
    private Disposable mPaginationDisposable;
    private String parentId;
    private NotebookForm notebookForm;


    public NotebookCreatePresenterImpl(NotebookService mNotebookService) {
        (this.mNotebookService = mNotebookService).openConnection();
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
    public boolean createNotebook(String name) {
        notebookForm = new NotebookForm(CurrentState.profileId, false, parentId, name);
        Optional<String> newNotebookId = mNotebookService.create(notebookForm);
        mNotebookCreateDialogFragment.getNotebook(mNotebookService.getById(newNotebookId.get()).get());
        mNotebookCreateDialogFragment.updateRecyclerView();
        return  newNotebookId.isPresent();
    }

    @Override
    public void subscribeRecyclerViewForPagination(RecyclerView recyclerView) {
        initPaginationSubject();
        recyclerView.addOnScrollListener(new RecyclerViewOnScrollListener(mPaginationSubject));

    }

    @Override
    public void disposePaginationAndSearch() {
        if (mPaginationDisposable != null && !mPaginationDisposable.isDisposed()) {
            mPaginationDisposable.dispose();
        }
    }

    @Override
    public void getNotebookId(String notebookId) {
        if (!TextUtils.isEmpty(notebookId)) {
            parentId = notebookId;
        }
    }

    @Override
    public void setDataToAdapter(DataPostSetAdapter<Notebook> dataPostSetAdapter) {
        mNotebooks = mNotebookService.getByProfile(CurrentState.profileId, new PaginationArgs());
        dataPostSetAdapter.setData(mNotebooks);
    }



    private void initPaginationSubject() {
        mPaginationDisposable = (mPaginationSubject = ReplaySubject.create())
                .observeOn(Schedulers.io())
                .map(paginationArgs -> mNotebookService.getByProfile(CurrentState.profileId, paginationArgs))
                .filter(notes -> !notes.isEmpty())
                .map(newNotes -> mNotebooks.addAll(newNotes))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> mNotebookCreateDialogFragment.updateRecyclerView(),
                        throwable -> {/*TODO: find out what can happen here*/});
    }
}
