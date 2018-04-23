package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.impl;

import android.support.v7.widget.RecyclerView;

import com.github.android.lvrn.lvrnproject.service.ProfileDependedService;
import com.github.android.lvrn.lvrnproject.service.form.ProfileDependedForm;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListFragment;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListPresenter;
import com.github.android.lvrn.lvrnproject.view.listener.RecyclerViewOnScrollListener;
import com.github.valhallalabs.laverna.persistent.entity.base.ProfileDependedEntity;
import com.orhanobut.logger.Logger;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class EntitiesListPresenterImpl<T1 extends ProfileDependedEntity, T2 extends ProfileDependedForm> implements EntitiesListPresenter<T1, T2> {
    private ProfileDependedService<T1,T2> mEntityService;

    protected EntitiesListFragment mEntitiesListFragment;

    protected RecyclerViewOnScrollListener mRecyclerViewOnScrollLister;

    private Disposable mPaginationDisposable;

    protected ReplaySubject<PaginationArgs> mPaginationSubject;

    protected List<T1> mEntities;

    public EntitiesListPresenterImpl(ProfileDependedService<T1, T2> entityService) {
        (mEntityService = entityService).openConnection();
    }

    @Override
    public void bindView(EntitiesListFragment allNotesFragment) {
        mEntitiesListFragment = allNotesFragment;
        if (!mEntityService.isConnectionOpened()) {
            mEntityService.openConnection();
        }
    }

    @Override
    public void unbindView() {
        mEntitiesListFragment = null;
        mEntityService.closeConnection();
    }

    @Override
    public void subscribeRecyclerViewForPagination(RecyclerView recyclerView) {
        initPaginationSubject();
        mRecyclerViewOnScrollLister = new RecyclerViewOnScrollListener(mPaginationSubject);
        recyclerView.addOnScrollListener(mRecyclerViewOnScrollLister);
    }

    @Override
    public void disposePagination() {
        if (mPaginationDisposable != null && !mPaginationDisposable.isDisposed()) {
            mPaginationDisposable.dispose();
        }
    }

    @Override
    public void setDataToAdapter(DataPostSetAdapter<T1> dataPostSetAdapter) {
        mEntities = loadMoreForPagination(new PaginationArgs());
        dataPostSetAdapter.setData(mEntities);
    }

    protected boolean addFirstFoundedItemsToList(List<T1> firstFoundedNotes) {
        mRecyclerViewOnScrollLister.resetListener();
        mEntities.clear();
        mEntities.addAll(firstFoundedNotes);
        return true;
    }

    private void initPaginationSubject() {
        mPaginationDisposable = (mPaginationSubject = ReplaySubject.create())
                .observeOn(Schedulers.io())
                .map(this::loadMoreForPagination)
                .filter(notes -> !notes.isEmpty())
                .map(newNotes -> mEntities.addAll(newNotes))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> mEntitiesListFragment.updateRecyclerView(),
                        throwable -> Logger.e(throwable, "Something really strange happened with pagination!"));
    }

    protected abstract List<T1> loadMoreForPagination(PaginationArgs paginationArgs);
}
