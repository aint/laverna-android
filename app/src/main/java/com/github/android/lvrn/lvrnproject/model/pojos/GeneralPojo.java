package com.github.android.lvrn.lvrnproject.model.pojos;

import com.github.android.lvrn.lvrnproject.model.enums.TrashStatusEnum;
import com.github.android.lvrn.lvrnproject.model.enums.TypeEnum;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

abstract class GeneralModel {

    /**
     * An id of the model
     */
    private String id;

    /**
     * A type of the model
     */
    private TypeEnum type;

    /**
     * A status of the model's existence
     */
    private TrashStatusEnum trash;

    /**
     * A date of the model's creation
     * TODO: write format of time
     */
    private long created;

    /**
     * A date of of the model's last update
     * TODO: write format of time
     */
    private long updated;

    GeneralModel(String id, TypeEnum type, TrashStatusEnum trash, long created, long updated) {
        this.id = id;
        this.type = type;
        this.trash = trash;
        this.created = created;
        this.updated = updated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    public TrashStatusEnum getTrash() {
        return trash;
    }

    public void setTrash(TrashStatusEnum trash) {
        this.trash = trash;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "GeneralModel{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", trash=" + trash +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
