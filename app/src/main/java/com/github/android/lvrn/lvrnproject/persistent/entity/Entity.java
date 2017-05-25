package com.github.android.lvrn.lvrnproject.persistent.entity;

import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class Entity implements Parcelable {

    @NonNull
    protected String id;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id='" + id + '\'' +
                '}';
    }
}
