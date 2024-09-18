package com.github.android.lvrn.lvrnproject.view.dialog.notebookcreation.impl;

import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;

import com.github.android.lvrn.lvrnproject.util.CurrentState;
import com.github.valhallalabs.laverna.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.service.form.NotebookForm;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreation.NotebookCreationDialogFragment;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreation.NotebookCreationPresenter;
import com.github.android.lvrn.lvrnproject.view.listener.RecyclerViewOnScrollListener;

import java.util.List;
import java.util.Optional;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class NotebookCreationPresenterImpl implements NotebookCreationPresenter {

    private List<Notebook> mNotebooks;
    private NotebookService mNotebookService;
    private NotebookCreationDialogFragment mNotebookCreationDialogFragment;
    private ReplaySubject<PaginationArgs> mPaginationSubject;
    private Disposable mPaginationDisposable;
    private String parentId;
    private NotebookForm notebookForm;


    public NotebookCreationPresenterImpl(NotebookService mNotebookService) {
        (this.mNotebookService = mNotebookService).openConnection();
    }

    @Override
    public void bindView(NotebookCreationDialogFragment notebookCreationDialogFragment) {
        mNotebookCreationDialogFragment = notebookCreationDialogFragment;
        if (!mNotebookService.isConnectionOpened()) {
            mNotebookService.openConnection();
        }
    }

    @Override
    public void unbindView() {
        if (mNotebookCreationDialogFragment != null) {
            mNotebookCreationDialogFragment = null;
        }
        mNotebookService.closeConnection();
    }

    @Override
    public boolean createNotebook(String name) {
        notebookForm = new NotebookForm(CurrentState.Companion.getProfileId(), false, parentId, name);
        Optional<String> newNotebookId = mNotebookService.create(notebookForm);
        if (newNotebookId.isPresent()){
            mNotebookCreationDialogFragment.getNotebook(mNotebookService.getById(newNotebookId.get()).get());
            mNotebookCreationDialogFragment.updateRecyclerView();
        }

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
        mNotebooks = mNotebookService.getByProfile(CurrentState.Companion.getProfileId(), new PaginationArgs());
        dataPostSetAdapter.setData(mNotebooks);
    }



    private void initPaginationSubject() {
        mPaginationDisposable = (mPaginationSubject = ReplaySubject.create())
                .observeOn(Schedulers.io())
                .map(paginationArgs -> mNotebookService.getByProfile(CurrentState.Companion.getProfileId(), paginationArgs))
                .filter(notes -> !notes.isEmpty())
                .map(newNotes -> mNotebooks.addAll(newNotes))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> mNotebookCreationDialogFragment.updateRecyclerView(),
                        throwable -> {/*TODO: find out what can happen here*/});
    }
}
