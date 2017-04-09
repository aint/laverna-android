package com.github.android.lvrn.lvrnproject.model.persistententities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

abstract class GeneralEntity extends RealmObject {

    /**
     * An id of the model
     */
    @PrimaryKey
    @Required
    private String id;

    /**
     * A name or a title of the entity
     */
    @Required
    private String name;

    public GeneralEntity(String id, String name) {
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
        return "GeneralEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
