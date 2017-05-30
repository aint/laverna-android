package com.github.android.lvrn.lvrnproject.view.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.android.lvrn.lvrnproject.util.PaginationArgs;

import io.reactivex.subjects.ReplaySubject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class RecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {

    private int mVisibleThreshold;

    private int mPreviousTotalItemCount = 0;

    private boolean mLoading = false;

    private PaginationArgs mPaginationArgs;

    private ReplaySubject<PaginationArgs> mReplaySubject;


    public RecyclerViewOnScrollListener(ReplaySubject<PaginationArgs> replaySubject) {
        mReplaySubject = replaySubject;
        mPaginationArgs = new PaginationArgs();
    }

    public RecyclerViewOnScrollListener(ReplaySubject<PaginationArgs> replaySubject, PaginationArgs PaginationArgs, int visibleThreshold) {
        mReplaySubject = replaySubject;
        mPaginationArgs = PaginationArgs;
        mVisibleThreshold = visibleThreshold;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        LinearLayoutManager layoutManager= (LinearLayoutManager) recyclerView.getLayoutManager();

        int mTotalItemCount = recyclerView.getLayoutManager().getItemCount() - 1;

        if (mLoading && mTotalItemCount > mPreviousTotalItemCount) {
            mLoading = false;
            mPreviousTotalItemCount = mTotalItemCount;
        }

        if (!mLoading && layoutManager.findLastVisibleItemPosition() >= (mTotalItemCount - mVisibleThreshold)) {
            mLoading = true;
            mPaginationArgs.offset = mTotalItemCount + 1;
            mReplaySubject.onNext(mPaginationArgs);
        }
    }

    public void changeSubject(ReplaySubject<PaginationArgs> replaySubject) {
        mPreviousTotalItemCount = 0;
        mLoading = false;
        mReplaySubject = replaySubject;
        mPaginationArgs = new PaginationArgs();

    }

    public void resetListener() {
        mPreviousTotalItemCount = 0;
        mLoading = false;
        mPaginationArgs = new PaginationArgs();
    }
}
