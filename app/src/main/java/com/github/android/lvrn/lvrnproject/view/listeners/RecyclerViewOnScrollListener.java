package com.github.android.lvrn.lvrnproject.view.listeners;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.android.lvrn.lvrnproject.util.PaginationArgs;

import io.reactivex.subjects.Subject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class RecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {

    private int mVisibleThreshold;

    private int mPreviousTotalItemCount = 0;

    private int mTotalItemCount;

    private boolean mLoading = false;

    private Subject<PaginationArgs> PaginationArgsSubject;

    private PaginationArgs mPaginationArgs;

    public RecyclerViewOnScrollListener(Subject<PaginationArgs> subject, PaginationArgs PaginationArgs, int visibleThreshold) {
        PaginationArgsSubject = subject;
        mPaginationArgs = PaginationArgs;
        mVisibleThreshold = visibleThreshold;
    }

    public RecyclerViewOnScrollListener(Subject<PaginationArgs> subject) {
        PaginationArgsSubject = subject;
        mPaginationArgs = new PaginationArgs();
        mVisibleThreshold = 5;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        System.out.println("mTotalItemCount" + mTotalItemCount);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        LinearLayoutManager layoutManager= (LinearLayoutManager) recyclerView.getLayoutManager();

        mTotalItemCount = recyclerView.getLayoutManager().getItemCount();

        if (mLoading && mTotalItemCount > mPreviousTotalItemCount) {
            mLoading = false;
            mPreviousTotalItemCount = mTotalItemCount;
        }

        if (!mLoading && layoutManager.findLastVisibleItemPosition() >= (mTotalItemCount - mVisibleThreshold)) {
            mLoading = true;
            mPaginationArgs.offset = mTotalItemCount + 1;
            PaginationArgsSubject.onNext(mPaginationArgs);
        }
    }
}
