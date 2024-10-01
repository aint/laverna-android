package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.impl

import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import com.github.android.lvrn.lvrnproject.service.ProfileDependedService
import com.github.android.lvrn.lvrnproject.service.form.ProfileDependedForm
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListWithSearchFragment
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListWithSearchPresenter
import com.github.android.lvrn.lvrnproject.view.listener.SearchViewOnQueryTextListener
import com.github.valhallalabs.laverna.persistent.entity.base.ProfileDependedEntity
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.ReplaySubject
import java.util.concurrent.TimeUnit

/**
 * @author Vadim Boitsov <vadimboitsov1></vadimboitsov1>@gmail.com>
 */
abstract class EntitiesListWithSearchPresenterImpl<T1 : ProfileDependedEntity, T2 : ProfileDependedForm<T1>>(
    entityService: ProfileDependedService<T1, T2>
) : EntitiesListPresenterImpl<T1, T2>(entityService), EntitiesListWithSearchPresenter<T1, T2> {
    private var mSearchDisposable: Disposable? = null

    private var mFoundedPaginationDisposable: Disposable? = null

    private var mFoundedPaginationSubject: ReplaySubject<PaginationArgs>? = null

    override fun subscribeSearchView(searchItem: MenuItem) {
        MenuItemCompat.setOnActionExpandListener(searchItem, this)
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        initSearchSubject(searchView)
        initFoundedPaginationSubject()
    }

    override fun disposeSearch() {
        if (mSearchDisposable != null && !mSearchDisposable!!.isDisposed) {
            mSearchDisposable!!.dispose()
        }
        if (mFoundedPaginationDisposable != null && !mFoundedPaginationDisposable!!.isDisposed) {
            mFoundedPaginationDisposable!!.dispose()
        }
    }

    /**
     * A method which expands search view and changes a subject of recycler view's onScrollListener.
     * @param item
     * @return
     */
    override fun onMenuItemActionExpand(item: MenuItem): Boolean {
        (mEntitiesListFragment as EntitiesListWithSearchFragment).switchToSearchMode()
        mRecyclerViewOnScrollLister?.changeSubject(mFoundedPaginationSubject)
        return true
    }

    /**
     * A method which collapses search view and changes a subject of recycler view's onScrollListener.
     * @param item
     * @return
     */
    override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
        (mEntitiesListFragment as EntitiesListWithSearchFragment).switchToNormalMode()
        mRecyclerViewOnScrollLister?.changeSubject(mPaginationSubject)
        addFirstFoundedItemsToList(loadMoreForPagination(PaginationArgs()))
        mEntitiesListFragment?.updateRecyclerView()
        return true
    }

    private fun initSearchSubject(searchView: SearchView) {
        var searchQuerySubject: ReplaySubject<String>

        mSearchDisposable =
            ReplaySubject.create<String>().also { searchQuerySubject = it }
                .debounce(400, TimeUnit.MILLISECONDS)
                .map { title: String -> loadMoreForSearch(title, PaginationArgs()) }
                .map {items -> addFirstFoundedItemsToList(items) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { mEntitiesListFragment?.updateRecyclerView() },
                    { throwable: Throwable? ->
                        Logger.e(
                            throwable,
                            "Something really strange happened with search!"
                        )
                    })

        searchView.setOnQueryTextListener(SearchViewOnQueryTextListener(searchQuerySubject))
    }

    private fun initFoundedPaginationSubject() {
        mFoundedPaginationDisposable =
            ReplaySubject.create<PaginationArgs>().also { mFoundedPaginationSubject = it }
                .observeOn(Schedulers.io())
                .map<List<T1>> { paginationArgs: PaginationArgs ->
                    loadMoreForSearch(
                        (mEntitiesListFragment as EntitiesListWithSearchFragment).getSearchQuery(),
                        paginationArgs
                    )
                }
                .filter { notes: List<T1> -> notes.isNotEmpty() }
                .map<Boolean> { newNotes: List<T1> ->
                    mEntities.addAll(
                        newNotes
                    )
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { mEntitiesListFragment?.updateRecyclerView() },
                    { throwable: Throwable? ->
                        Logger.e(
                            throwable,
                            "Something really strange happened with search pagination!"
                        )
                    })
    }

    protected abstract fun loadMoreForSearch(
        query: String,
        paginationArgs: PaginationArgs
    ): List<T1>
}
