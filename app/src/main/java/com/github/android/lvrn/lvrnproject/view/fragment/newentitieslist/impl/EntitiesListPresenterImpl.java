package com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.impl;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;

import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;
import com.github.android.lvrn.lvrnproject.service.ProfileDependedService;
import com.github.android.lvrn.lvrnproject.service.form.ProfileDependedForm;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.EntitiesListFragment;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.EntitiesListPresenter;
import com.github.android.lvrn.lvrnproject.view.listener.RecyclerViewOnScrollListener;
import com.github.android.lvrn.lvrnproject.view.listener.SearchViewOnQueryTextListener;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

    private Disposable mSearchDisposable;

    private Disposable mFoundedPaginationDisposable;

    private ReplaySubject<PaginationArgs> mFoundedPaginationSubject;

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

    @Override
    public void subscribeSearchView(MenuItem searchItem) {
        MenuItemCompat.setOnActionExpandListener(searchItem, this);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        initSearchSubject(searchView);
        initFoundedPaginationSubject();
    }

    @Override
    public void disposeSearch() {
        if (mSearchDisposable != null && !mSearchDisposable.isDisposed()) {
            mSearchDisposable.dispose();
        }
        if (mFoundedPaginationDisposable != null && !mFoundedPaginationDisposable.isDisposed()) {
            mFoundedPaginationDisposable.dispose();
        }
    }

    /**
     * A method which expands search view and changes a subject of recycler view's onScrollListener.
     * @param item
     * @return
     */
    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        mEntitiesListFragment.switchToSearchMode();
        mRecyclerViewOnScrollLister.changeSubject(mFoundedPaginationSubject);
        return true;
    }

    /**
     * A method which collapses search view and changes a subject of recycler view's onScrollListener.
     * @param item
     * @return
     */
    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        mEntitiesListFragment.switchToNormalMode();
        mRecyclerViewOnScrollLister.changeSubject(mPaginationSubject);
        addFirstFoundedItemsToList(loadMoreForPagination(new PaginationArgs()));
        mEntitiesListFragment.updateRecyclerView();
        return true;
    }

    private void initSearchSubject(SearchView searchView) {
        ReplaySubject<String> searchQuerySubject;

        mSearchDisposable = (searchQuerySubject = ReplaySubject.create())
                .debounce(400, TimeUnit.MILLISECONDS)
                .map(title -> loadMoreForSearch(title, new PaginationArgs()))
                .map(this::addFirstFoundedItemsToList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> mEntitiesListFragment.updateRecyclerView(),
                        throwable -> Logger.e(throwable, "Something really strange happened with search!"));

        searchView.setOnQueryTextListener(new SearchViewOnQueryTextListener(searchQuerySubject));
    }

    private void initFoundedPaginationSubject() {
        mFoundedPaginationDisposable = (mFoundedPaginationSubject = ReplaySubject.create())
                .observeOn(Schedulers.io())
                .map(paginationArgs -> loadMoreForSearch(mEntitiesListFragment.getSearchQuery(), paginationArgs))
                .filter(notes -> !notes.isEmpty())
                .map(newNotes -> mEntities.addAll(newNotes))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> mEntitiesListFragment.updateRecyclerView(),
                        throwable -> Logger.e(throwable, "Something really strange happened with search pagination!"));
    }

    protected abstract List<T1> loadMoreForSearch(String query, PaginationArgs paginationArgs);
}
