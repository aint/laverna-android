package com.github.android.lvrn.lvrnproject.view.listeners;

import android.support.annotation.Size;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import io.reactivex.subjects.Subject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class RecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {

    private int mVisibleThreshold;

    private int mPreviousTotalItemCount = 0;

    private int mTotalItemCount;

    private boolean mLoading = false;

    private Subject<PaginationParams> paginationParamsSubject;

    private PaginationParams mPaginationParams;

    public RecyclerViewOnScrollListener(Subject<PaginationParams> subject, PaginationParams paginationParams, int visibleThreshold) {
        paginationParamsSubject = subject;
        mPaginationParams = paginationParams;
        mVisibleThreshold = visibleThreshold;
    }

    public RecyclerViewOnScrollListener(Subject<PaginationParams> subject) {
        paginationParamsSubject = subject;
        mPaginationParams = new PaginationParams();
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
            mPaginationParams.offset = mTotalItemCount + 1;
            paginationParamsSubject.onNext(mPaginationParams);
        }
    }

    public static class PaginationParams {
        public int limit;

        public int offset;

        public PaginationParams(@Size(min = 0) int limit, @Size(min = 1) int offset) {
            this.limit = limit;
            this.offset = offset;
        }

        public PaginationParams() {
            limit = 15;
            offset = 0;
        }

        @Override
        public String toString() {
            return "PaginationParams{" +
                    "limit=" + limit +
                    ", offset=" + offset +
                    '}';
        }
    }
}
