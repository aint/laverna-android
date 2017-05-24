package com.github.android.lvrn.lvrnproject.persistent.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class Notebook extends ProfileDependedEntity {

    /**
     * An id of a notebook, which the notebook is belonged as a child. In case, if the note doesn't
     * belong to any parent notebook, then parentId equals to "0".
     */
    private String parentId;

    private String name;

    /**
     * A date of the model's creation in milliseconds.
     */
    private long creationTime;

    /**
     * A date of the model's update in milliseconds.
     */
    private long updateTime;

    //TODO: unknown field. Find out what to do with it
    private int count;

    public Notebook(String id,
                    String profileId,
                    String parentId,
                    String name,
                    long creationTime,
                    long updateTime,
                    int count) {
        this.id = id;
        this.profileId = profileId;
        this.parentId = parentId;
        this.name = name;
        this.creationTime = creationTime;
        this.updateTime = updateTime;
        this.count = count;
    }

    private Notebook(Parcel in) {
        this.id = in.readString();
        this.profileId = in.readString();
        this.parentId = in.readString();
        this.name = in.readString();
        this.creationTime = in.readLong();
        this.updateTime = in.readLong();
        this.count = in.readInt();
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

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @NonNull
    @Override
    public String toString() {
        return "Notebook{" + super.toString() +
                "parentId='" + parentId + '\'' +
                ", name='" + name + '\'' +
                ", creationTime=" + creationTime +
                ", updateTime=" + updateTime +
                ", count=" + count +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.profileId);
        dest.writeString(this.parentId );
        dest.writeString(this.name);
        dest.writeLong(this.creationTime);
        dest.writeLong(this.updateTime);
        dest.writeInt(this.count);
    }

    public static final Parcelable.Creator<Notebook> CREATOR = new Parcelable.Creator<Notebook>() {

        @Override
        public Notebook createFromParcel(Parcel source) {
            return new Notebook(source);
        }

        @Override
        public Notebook[] newArray(int size) {
            return new Notebook[size];
        }
    };
}
