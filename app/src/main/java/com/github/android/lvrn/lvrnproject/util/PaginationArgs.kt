package com.github.android.lvrn.lvrnproject.util

import androidx.annotation.Size

/**
 * @author Vadim Boitsov <vadimboitsov1></vadimboitsov1>@gmail.com>
 */
data class PaginationArgs(@Size(min = 0) var offset: Int = 0, @Size(min = 1) var limit: Int = 15) {

    override fun toString(): String {
        return "PaginationArgs{" +
                "limit=" + limit +
                ", offset=" + offset +
                '}'
    }

}
