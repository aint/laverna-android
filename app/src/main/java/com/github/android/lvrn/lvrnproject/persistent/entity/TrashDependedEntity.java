package com.github.android.lvrn.lvrnproject.persistent.entity;

import android.support.annotation.NonNull;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class TrashDependedEntity extends ProfileDependedEntity {

    /**
     * A field which shows trash status of an entity.
     */
    protected boolean isTrash;

    public boolean isTrash() {
        return isTrash;
    }

    public void setTrash(boolean trash) {
        isTrash = trash;
    }

    @NonNull
    @Override
    public String toString() {
        return "TrashDependedEntity{" + super.toString() +
                "isTrash=" + isTrash +
                '}';
    }
}
