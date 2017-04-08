package com.github.android.lvrn.lvrnproject.model.enums;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public enum TypeEnum {
    NOTEBOOKS("notebooks"),
    NOTES("notes"),
    TAGS("tags");

    private final String type;

    TypeEnum(final String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
