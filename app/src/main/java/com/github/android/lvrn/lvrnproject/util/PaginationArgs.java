package com.github.android.lvrn.lvrnproject.util;

import android.support.annotation.Size;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public final class PaginationArgs {
    @Size(min = 0) public int offset;

    @Size(min = 1) public int limit;

    public PaginationArgs(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public PaginationArgs() {
        limit = 15;
        offset = 0;
    }

    @Override
    public String toString() {
        return "PaginationArgs{" +
                "limit=" + limit +
                ", offset=" + offset +
                '}';
    }
}
