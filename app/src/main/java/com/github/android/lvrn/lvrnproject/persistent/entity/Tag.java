package com.github.android.lvrn.lvrnproject.persistent.entity;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class Tag {

    /**
     * An id of the tag.
     */
    private String id;
    
    private String profileId;

    /**
     * A name of the tag.
     */
    private String name;
    /**
     * A date of the model's creation.
     * TODO: find out format of time
     */
    private long creationTime;

    private long updateTime;

    //TODO: unknown field. Find out what to do with it
    private int count;


}
