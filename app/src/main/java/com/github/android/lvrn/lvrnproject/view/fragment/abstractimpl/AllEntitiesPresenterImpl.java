package com.github.android.lvrn.lvrnproject.view.fragment.abstractimpl;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;

import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;
import com.github.android.lvrn.lvrnproject.service.ProfileDependedService;
import com.github.android.lvrn.lvrnproject.service.form.ProfileDependedForm;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.github.android.lvrn.lvrnproject.view.adapter.DataPostSetAdapter;
import com.github.android.lvrn.lvrnproject.view.listener.RecyclerViewOnScrollListener;
import com.github.android.lvrn.lvrnproject.view.listener.SearchViewOnQueryTextListener;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class AllEntitiesPresenterImpl<T1 extends ProfileDependedEntity, T2 extends ProfileDependedForm> implements AllEntitiesPresenter<T1, T2> {
    private ProfileDependedService<T1,T2> mEntityService;

    private AllEntitiesFragment mAllEntitiesFragment;

    private RecyclerViewOnScrollListener mRecyclerViewOnScrollLister;

    private Disposable mSearchDisposable;

    private Disposable mPaginationDisposable;

    private Disposable mFoundedPaginationDisposable;

    private ReplaySubject<PaginationArgs> mPaginationSubject;

    private ReplaySubject<PaginationArgs> mFoundedPaginationSubject;

    private List<T1> mEntities;

    public AllEntitiesPresenterImpl(ProfileDependedService<T1, T2> entityService) {
        (mEntityService = entityService).openConnection();
    }

    @Override
    public void bindView(AllEntitiesFragment allNotesFragment) {
        mAllEntitiesFragment = allNotesFragment;
        if (!mEntityService.isConnectionOpened()) {
            mEntityService.openConnection();
        }
    }

    @Override
    public void unbindView() {
        mAllEntitiesFragment = null;
        mEntityService.closeConnection();
    }

    @Override
    public void subscribeRecyclerViewForPagination(RecyclerView recyclerView) {
        initPaginationSubject();
        initFoundedPaginationSubject();
        mRecyclerViewOnScrollLister = new RecyclerViewOnScrollListener(mPaginationSubject);
        recyclerView.addOnScrollListener(mRecyclerViewOnScrollLister);
    }

    @Override
    public void subscribeSearchView(MenuItem searchItem) {
        MenuItemCompat.setOnActionExpandListener(searchItem, this);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        initSearchSubject(searchView);
    }

    /**
     * A method which expands search view and changes a subject of recycler view's onScrollListener.
     * @param item
     * @return
     */
    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        mAllEntitiesFragment.switchToSearchMode();
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
        mAllEntitiesFragment.switchToNormalMode();
        mRecyclerViewOnScrollLister.changeSubject(mPaginationSubject);
        addFirstFoundedItemsToList(loadMoreForPagination(new PaginationArgs()));
        mAllEntitiesFragment.updateRecyclerView();
        return true;
    }

    @Override
    public void disposePaginationAndSearch() {
        if (mSearchDisposable != null && !mSearchDisposable.isDisposed()) {
            mSearchDisposable.dispose();
        }
        if (mPaginationDisposable != null && !mPaginationDisposable.isDisposed()) {
            mPaginationDisposable.dispose();
        }
        if (mFoundedPaginationDisposable != null && !mFoundedPaginationDisposable.isDisposed()) {
            mFoundedPaginationDisposable.dispose();
        }
    }

    @Override
    public void setDataToAdapter(DataPostSetAdapter<T1> dataPostSetAdapter) {
        mEntities = loadMoreForPagination(new PaginationArgs());
        dataPostSetAdapter.setData(mEntities);
    }

    private void initSearchSubject(SearchView searchView) {
        ReplaySubject<String> searchQuerySubject;

        mSearchDisposable = (searchQuerySubject = ReplaySubject.create())
                .debounce(400, TimeUnit.MILLISECONDS)
                .filter(title -> title.length() > 0)
                .map(title -> loadMoreForSearch(title, new PaginationArgs()))
                .map(this::addFirstFoundedItemsToList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> mAllEntitiesFragment.updateRecyclerView(),
                        throwable -> {/*TODO: find out what can happen here*/});

        searchView.setOnQueryTextListener(new SearchViewOnQueryTextListener(searchQuerySubject));
    }

    private boolean addFirstFoundedItemsToList(List<T1> firstFoundedNotes) {
        mRecyclerViewOnScrollLister.resetListener();
        mEntities.clear();
        mEntities.addAll(firstFoundedNotes);
        return true;
    }

    private void initPaginationSubject() {
        mPaginationDisposable = (mPaginationSubject = ReplaySubject.create())
                .observeOn(Schedulers.io())
                .map(paginationArgs -> loadMoreForPagination(paginationArgs))
                .filter(notes -> !notes.isEmpty())
                .map(newNotes -> mEntities.addAll(newNotes))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> mAllEntitiesFragment.updateRecyclerView(),
                        throwable -> {/*TODO: find out what can happen here*/});
    }

    private void initFoundedPaginationSubject() {
        mFoundedPaginationDisposable = (mFoundedPaginationSubject = ReplaySubject.create())
                .observeOn(Schedulers.io())
                .map(paginationArgs -> loadMoreForSearch(mAllEntitiesFragment.getSearchQuery(), paginationArgs))
                .filter(notes -> !notes.isEmpty())
                .map(newNotes -> mEntities.addAll(newNotes))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> mAllEntitiesFragment.updateRecyclerView(),
                        throwable -> {/*TODO: find out what can happen here*/});
    }

    protected abstract List<T1> loadMoreForPagination(PaginationArgs paginationArgs);

    protected abstract List<T1> loadMoreForSearch(String query, PaginationArgs paginationArgs);
}
