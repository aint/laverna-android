package com.github.android.lvrn.lvrnproject.model.enums;

import java.util.HashMap;
import java.util.Map;

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




    private static Map<Integer, TrashStatusEnum> map = new HashMap<>();

    static {
        for (TrashStatusEnum trashStatusEnum : TrashStatusEnum.values()) {
            map.put(trashStatusEnum.status, trashStatusEnum);
        }
    }

    public static TrashStatusEnum valueOf(int status) {
        return map.get(status);
    }
}
