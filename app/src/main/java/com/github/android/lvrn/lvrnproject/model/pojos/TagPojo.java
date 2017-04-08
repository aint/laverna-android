package com.github.android.lvrn.lvrnproject.model.pojos;

import com.github.android.lvrn.lvrnproject.model.enums.TrashStatusEnum;
import com.github.android.lvrn.lvrnproject.model.enums.TypeEnum;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class TagPojo extends GeneralPojo {

    /**
     * A name of the tag.
     */
    private String name;

    //TODO: unknown field. Find out what to do with it
    private int count;

    public TagPojo(String id,
                   TypeEnum type,
                   TrashStatusEnum trash,
                   long created,
                   long updated,
                   String name,
                   int count) {
        super(id, type, trash, created, updated);
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "TagPojo{" + super.toString() +
                "name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
