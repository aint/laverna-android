package com.github.android.lvrn.lvrnproject.persistent.entity;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class Notebook {

    /**
     * An id of the notebook.
     */
    private String id;

    private String profileId;

    private String parentId;

    /**
     * A name of the notebook.
     */
    private String name;

    /**
     * A date of the model's creation.
     * TODO: find out format of time
     */
    private long creationTime;

    private long updateTime;

    /**
     * An id of a notebook, which the notebook is belonged as a child. In case, if the note doesn't
     * belong to any parent notebook, then parentId equals to "0".
     */

    //TODO: unknown field. Find out what to do with it
    private int count;


}
