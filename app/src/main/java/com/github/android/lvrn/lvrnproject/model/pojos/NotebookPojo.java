package com.github.android.lvrn.lvrnproject.model.pojos;


import com.github.android.lvrn.lvrnproject.model.enums.TrashStatusEnum;
import com.github.android.lvrn.lvrnproject.model.enums.TypeEnum;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NotebookPojo extends GeneralPojo {

    /**
     * An id of a notebook, which the notebook is belonged as a child. In case, if the note doesn't
     * belong to any parent notebook, then parentId equals to "0".
     */
    private String parentId = "0";

    /**
     * A name of the notebook.
     */
    private String name;

    //TODO: unknown field. Find out what to do with it
    private int count;

    public NotebookPojo(String id,
                        TypeEnum type,
                        TrashStatusEnum trash,
                        long created,
                        long updated,
                        String parentId,
                        String name,
                        int count) {
        super(id, type, trash, created, updated);
        this.parentId = parentId;
        this.name = name;
        this.count = count;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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
        return "NotebookPojo{" + super.toString() +
                "parentId='" + parentId + '\'' +
                ", name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
