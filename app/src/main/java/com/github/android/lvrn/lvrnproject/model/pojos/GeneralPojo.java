package com.github.android.lvrn.lvrnproject.model.pojos;



import com.github.android.lvrn.lvrnproject.model.enums.TrashStatusEnum;
import com.github.android.lvrn.lvrnproject.model.enums.TypeEnum;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

abstract class GeneralPojo extends RealmObject {

    /**
     * An id of the model
     */
    @PrimaryKey
    private String id;

    /**
     * A type of the model
     */
    private String type;

    /**
     * A status of the model's existence
     */
    private int trash;

    /**
     * A date of the model's creation
     * TODO: find out format of time
     */
    private long created;

    /**
     * A date of of the model's last update
     * TODO: find out format of time
     */
    private long updated;

    GeneralPojo(String id, TypeEnum type, TrashStatusEnum trash, long created, long updated) {
        this.id = id;
        this.type = type.toString();
        this.trash = trash.getStatus();
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
        return TypeEnum.valueOf(type);
    }

    public void setType(TypeEnum type) {
        this.type = type.toString();
    }

    public TrashStatusEnum getTrash() {
        return TrashStatusEnum.valueOf(trash);
    }

    public void setTrash(TrashStatusEnum trash) {
        this.trash = trash.getStatus();
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
        return "GeneralPojo{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", trash=" + trash +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
