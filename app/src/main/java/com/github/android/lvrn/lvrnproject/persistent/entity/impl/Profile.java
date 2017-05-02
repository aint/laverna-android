package com.github.android.lvrn.lvrnproject.persistent.entity.impl;

import com.github.android.lvrn.lvrnproject.persistent.entity.Entity;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class Profile extends Entity {

    /**
     * A name of the profile.
     */
    private String name;

    public Profile(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Profile{" + super.toString() +
                "name='" + name + '\'' +
                '}';
    }
}
