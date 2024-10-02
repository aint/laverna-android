package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.impl

import androidx.recyclerview.widget.RecyclerView
import com.github.android.lvrn.lvrnproject.service.ProfileDependedService
import com.github.android.lvrn.lvrnproject.service.form.ProfileDependedForm
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListFragment
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListPresenter
import com.github.android.lvrn.lvrnproject.view.listener.RecyclerViewOnScrollListener
import com.github.valhallalabs.laverna.persistent.entity.base.ProfileDependedEntity
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.ReplaySubject

/**
 * @author Vadim Boitsov <vadimboitsov1></vadimboitsov1>@gmail.com>
 */
abstract class EntitiesListPresenterImpl<T1 : ProfileDependedEntity, T2 : ProfileDependedForm<*>>(
    entityService: ProfileDependedService<T1, T2>
) : EntitiesListPresenter<T1, T2> {
    private var mEntityService: ProfileDependedService<T1, T2>? = null

    protected var mEntitiesListFragment: EntitiesListFragment? = null

    protected var mRecyclerViewOnScrollLister: RecyclerViewOnScrollListener? = null

    private var mPaginationDisposable: Disposable? = null

    protected var mPaginationSubject: ReplaySubject<PaginationArgs>? = null

    protected var mEntities: MutableList<T1> = mutableListOf()

    init {
        entityService.also { mEntityService = it }.openConnection()
    }

    override fun bindView(allNotesFragment: EntitiesListFragment) {
        mEntitiesListFragment = allNotesFragment
        if (!mEntityService!!.isConnectionOpened()) {
            mEntityService!!.openConnection()
        }
    }

    override fun unbindView() {
        mEntitiesListFragment = null
        mEntityService!!.closeConnection()
    }

    override fun subscribeRecyclerViewForPagination(recyclerView: RecyclerView) {
        initPaginationSubject()
        mRecyclerViewOnScrollLister = mPaginationSubject?.let { RecyclerViewOnScrollListener(it) }
        recyclerView.addOnScrollListener(mRecyclerViewOnScrollLister!!)
    }

    override fun disposePagination() {
        if (mPaginationDisposable != null && !mPaginationDisposable!!.isDisposed) {
            mPaginationDisposable!!.dispose()
        }
    }

    override fun setDataToAdapter(dataPostSetAdapter: DataPostSetAdapter<T1>) {
        mEntities = loadMoreForPagination(PaginationArgs()).toMutableList()
        dataPostSetAdapter.setData(mEntities)
    }

    protected fun addFirstFoundedItemsToList(firstFoundedNotes: List<T1>): Boolean {
        Logger.d(firstFoundedNotes)
        mRecyclerViewOnScrollLister!!.resetListener()
        mEntities.clear()
        mEntities.addAll(firstFoundedNotes)
        return true
    }

    private fun initPaginationSubject() {
        mPaginationDisposable =
            ReplaySubject.create<PaginationArgs>().also { mPaginationSubject = it }
                .observeOn(Schedulers.io())
                .map<List<T1>> { paginationArgs: PaginationArgs ->
                    this.loadMoreForPagination(
                        paginationArgs
                    )
                }
                .filter { notes: List<T1> -> !notes.isEmpty() }
                .map { newNotes: List<T1>? ->
                    mEntities.addAll(
                        newNotes!!
                    )
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { mEntitiesListFragment!!.updateRecyclerView() },
                    { throwable: Throwable? ->
                        Logger.e(
                            throwable,
                            "Something really strange happened with pagination!"
                        )
                    })
    }

    protected abstract fun loadMoreForPagination(paginationArgs: PaginationArgs): List<T1>
}
