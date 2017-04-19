package com.github.android.lvrn.lvrnproject.persistent.entity;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class ProfileEntity extends BasicEntity {

    /**
     * A name of the profile.
     */
    private String name;

    public ProfileEntity(String id, String name) {
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
}
