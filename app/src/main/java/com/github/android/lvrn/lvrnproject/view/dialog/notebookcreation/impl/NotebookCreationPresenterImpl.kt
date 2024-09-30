package com.github.android.lvrn.lvrnproject.view.dialog.notebookcreation.impl

import android.text.TextUtils
import androidx.recyclerview.widget.RecyclerView
import com.github.android.lvrn.lvrnproject.service.core.NotebookService
import com.github.android.lvrn.lvrnproject.service.form.NotebookForm
import com.github.android.lvrn.lvrnproject.util.CurrentState.Companion.profileId
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter
import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreation.NotebookCreationDialogFragment
import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreation.NotebookCreationPresenter
import com.github.android.lvrn.lvrnproject.view.listener.RecyclerViewOnScrollListener
import com.github.valhallalabs.laverna.persistent.entity.Notebook
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.ReplaySubject
import javax.inject.Inject

/**
 * @author Andrii Bei <psihey1></psihey1>@gmail.com>
 */
class NotebookCreationPresenterImpl @Inject constructor(val mNotebookService: NotebookService) :
    NotebookCreationPresenter {

    private var mNotebooks: MutableList<Notebook> = mutableListOf()
    private var mNotebookCreationDialogFragment: NotebookCreationDialogFragment? = null
    private var mPaginationSubject: ReplaySubject<PaginationArgs>? = null
    private var mPaginationDisposable: Disposable? = null
    private var parentId: String? = null
    private var notebookForm: NotebookForm? = null


    init {
        mNotebookService.openConnection()
    }

    override fun bindView(notebookCreationDialogFragment: NotebookCreationDialogFragment?) {
        mNotebookCreationDialogFragment = notebookCreationDialogFragment
        if (!mNotebookService.isConnectionOpened()) {
            mNotebookService.openConnection()
        }
    }

    override fun unbindView() {
        if (mNotebookCreationDialogFragment != null) {
            mNotebookCreationDialogFragment = null
        }
        mNotebookService.closeConnection()
    }

    override fun createNotebook(name: String?): Boolean {
        notebookForm = NotebookForm(profileId!!, false, parentId, name!!)
        val newNotebookId = mNotebookService.create(
            notebookForm!!
        )
        if (newNotebookId.isPresent) {
            mNotebookCreationDialogFragment!!.getNotebook(
                mNotebookService.getById(newNotebookId.get()).get()
            )
            mNotebookCreationDialogFragment!!.updateRecyclerView()
        }

        return newNotebookId.isPresent
    }

    override fun subscribeRecyclerViewForPagination(recyclerView: RecyclerView?) {
        initPaginationSubject()
        recyclerView!!.addOnScrollListener(RecyclerViewOnScrollListener(mPaginationSubject))
    }

    override fun disposePaginationAndSearch() {
        if (mPaginationDisposable != null && !mPaginationDisposable!!.isDisposed) {
            mPaginationDisposable!!.dispose()
        }
    }

    override fun getNotebookId(notebookId: String?) {
        if (!TextUtils.isEmpty(notebookId)) {
            parentId = notebookId
        }
    }

    override fun setDataToAdapter(dataPostSetAdapter: DataPostSetAdapter<Notebook>) {
        mNotebooks = mNotebookService.getByProfile(profileId!!, PaginationArgs()).toMutableList()
        dataPostSetAdapter.setData(mNotebooks)
    }


    private fun initPaginationSubject() {
        mPaginationDisposable =
            ReplaySubject.create<PaginationArgs>().also { mPaginationSubject = it }
                .observeOn(Schedulers.io())
                .map { paginationArgs: PaginationArgs? ->
                    mNotebookService!!.getByProfile(
                        profileId!!, paginationArgs!!
                    )
                }
                .filter { notes: List<Notebook> -> notes.isNotEmpty() }
                .map { newNotes: List<Notebook>? ->
                    if (newNotes != null) {
                        mNotebooks.addAll(
                            newNotes
                        )
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { aBoolean: Unit -> mNotebookCreationDialogFragment!!.updateRecyclerView() },
                    { throwable: Throwable? -> })
    }
}
