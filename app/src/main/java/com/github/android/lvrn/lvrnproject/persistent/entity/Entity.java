package com.github.android.lvrn.lvrnproject.persistent.entity;

import android.os.Parcelable;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class Entity implements Parcelable {

    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id='" + id + '\'' +
                '}';
    }
}
