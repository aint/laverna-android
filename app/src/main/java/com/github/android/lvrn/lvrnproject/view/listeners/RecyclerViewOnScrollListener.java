package com.github.android.lvrn.lvrnproject.view.listeners;

import android.support.annotation.Size;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import io.reactivex.Emitter;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class RecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {

    private Emitter<PaginationParams> mPaginationParamsEmitter;

    private int mVisibleThreshold;

    private PaginationParams mPaginationParams;

    public RecyclerViewOnScrollListener(Emitter<PaginationParams> paginationParamsEmitter, PaginationParams paginationParams, int visibleThreshold) {
        mPaginationParamsEmitter = paginationParamsEmitter;
        mPaginationParams = paginationParams;
        mVisibleThreshold = visibleThreshold;
    }

    public RecyclerViewOnScrollListener(Emitter<PaginationParams> paginationParamsEmitter) {
        mPaginationParamsEmitter = paginationParamsEmitter;
        mPaginationParams = new PaginationParams(15, 1);
        mVisibleThreshold = 5;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        LinearLayoutManager layoutManager= (LinearLayoutManager) recyclerView.getLayoutManager();

        int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();

        System.out.println("ON SCROLL!");
        System.out.println("LAST V P:" + lastVisiblePosition);
        System.out.println("RESULT " + (mPaginationParams.offset - mVisibleThreshold));
        if (lastVisiblePosition >= (mPaginationParams.offset - mVisibleThreshold)) {
            System.out.println("");
            mPaginationParams.offset += mPaginationParams.limit;
            mPaginationParamsEmitter.onNext(mPaginationParams);
            System.out.println("LAST VISIBLE: " + mPaginationParams);
        }

        super.onScrolled(recyclerView, dx, dy);
    }

    public static class PaginationParams {
        public int limit;

        public int offset;

        public PaginationParams(@Size(min = 1) int limit, @Size(min = 2) int offset) {
            this.limit = limit;
            this.offset = offset;
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
