package com.github.android.lvrn.lvrnproject.view.listener

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import io.reactivex.subjects.ReplaySubject

/**
 * @author Vadim Boitsov <vadimboitsov1></vadimboitsov1>@gmail.com>
 */
class RecyclerViewOnScrollListener(
    private var mReplaySubject: ReplaySubject<PaginationArgs>,
    private var mPaginationArgs: PaginationArgs = PaginationArgs(),
) : RecyclerView.OnScrollListener() {

    private var mVisibleThreshold = 0

    private var mPreviousTotalItemCount = 0

    private var mLoading = false

    constructor(
        replaySubject: ReplaySubject<PaginationArgs>,
        paginationArgs: PaginationArgs,
        visibleThreshold: Int,
    ) : this(replaySubject, paginationArgs) {
        mVisibleThreshold = visibleThreshold
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager

        val mTotalItemCount = recyclerView.layoutManager!!.itemCount - 1

        if (mLoading && mTotalItemCount > mPreviousTotalItemCount) {
            mLoading = false
            mPreviousTotalItemCount = mTotalItemCount
        }

        if (!mLoading && layoutManager.findLastVisibleItemPosition() >= (mTotalItemCount - mVisibleThreshold)) {
            mLoading = true
            mPaginationArgs.offset = mTotalItemCount + 1
            mReplaySubject.onNext(mPaginationArgs)
        }
    }

    fun changeSubject(replaySubject: ReplaySubject<PaginationArgs>) {
        mPreviousTotalItemCount = 0
        mLoading = false
        mReplaySubject = replaySubject
        mPaginationArgs = PaginationArgs()
    }

    fun resetListener() {
        mPreviousTotalItemCount = 0
        mLoading = false
        mPaginationArgs = PaginationArgs()
    }
}
