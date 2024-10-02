package com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.impl

import androidx.recyclerview.widget.RecyclerView
import com.github.android.lvrn.lvrnproject.service.core.NotebookService
import com.github.android.lvrn.lvrnproject.util.CurrentState.Companion.profileId
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.NotebookSelectionDialogFragment
import com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.NotebookSelectionPresenter
import com.github.android.lvrn.lvrnproject.view.listener.RecyclerViewOnScrollListener
import com.github.valhallalabs.laverna.persistent.entity.Notebook
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.ReplaySubject
import javax.inject.Inject

/**
 * @author Vadim Boitsov <vadimboitsov1></vadimboitsov1>@gmail.com>
 */
internal class NotebookSelectionPresenterImpl @Inject constructor(val mNotebookService: NotebookService) :
    NotebookSelectionPresenter {
    private var mNotebookSelectionDialogFragment: NotebookSelectionDialogFragment? = null

    private var mPaginationDisposable: Disposable? = null

    private var mNotebooks: MutableList<Notebook> = mutableListOf()

    init {
        mNotebookService.openConnection()
    }

    override fun bindView(notebookSelectionDialogFragment: NotebookSelectionDialogFragment?) {
        mNotebookSelectionDialogFragment = notebookSelectionDialogFragment
        if (!mNotebookService.isConnectionOpened()) {
            mNotebookService.openConnection()
        }
    }

    override fun unbindView() {
        mNotebookService.closeConnection()
    }

    override fun subscribeRecyclerViewForPagination(recyclerView: RecyclerView) {
        var paginationReplaySubject: ReplaySubject<PaginationArgs>

        mPaginationDisposable =
            ReplaySubject.create<PaginationArgs>().also { paginationReplaySubject = it }
                .observeOn(Schedulers.io())
                .map { paginationArgs: PaginationArgs? ->
                    mNotebookService.getByProfile(
                        profileId!!, paginationArgs!!
                    )
                }
                .filter { notebooks: List<Notebook> -> !notebooks.isEmpty() }
                .map { newNotebooks: List<Notebook> ->
                    mNotebooks.addAll(
                        newNotebooks
                    )
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { aBoolean: Boolean? -> mNotebookSelectionDialogFragment!!.updateRecyclerView() },
                    { throwable: Throwable? -> })

        recyclerView.addOnScrollListener(RecyclerViewOnScrollListener(paginationReplaySubject))
    }

    override fun disposePagination() {
        if (mPaginationDisposable != null && !mPaginationDisposable!!.isDisposed) {
            mPaginationDisposable!!.dispose()
        }
    }

    override val notebooksForAdapter: List<Notebook>
        get() {
            mNotebooks = mNotebookService.getByProfile(profileId!!, PaginationArgs()).toMutableList()
            return mNotebooks
        }
}
