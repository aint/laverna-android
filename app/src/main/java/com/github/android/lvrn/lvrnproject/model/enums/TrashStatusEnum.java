package com.github.android.lvrn.lvrnproject.model;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public enum TrashStatusEnum {
    NOT_REMOVED(0),
    REMOVED(1),
    REMOVED_4EVER(2);

    private final int status;

    TrashStatusEnum(final int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
