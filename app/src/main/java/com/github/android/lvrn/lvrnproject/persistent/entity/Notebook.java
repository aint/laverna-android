//package com.github.android.lvrn.lvrnproject.persistent.entity;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//import android.support.annotation.NonNull;
//
///**
// * @author Vadim Boitsov <vadimboitsov1@gmail.com>
// */
//
//public class Notebook extends TrashDependedEntity {
//
//    /**
//     * An id of a notebook, which the notebook is belonged as a child. In case, if the note doesn't
//     * belong to any parent notebook, then parentId equals to "0".
//     */
//    @NonNull
//    private String parentId;
//
//    @NonNull
//    private String name;
//
//    /**
//     * A date of the model's creation in milliseconds.
//     */
//    private long creationTime;
//
//    /**
//     * A date of the model's update in milliseconds.
//     */
//    private long updateTime;
//
//    //TODO: unknown field. Find out what to do with it
//    private int count;
//
//    public Notebook(String id,
//                    String profileId,
//                    String parentId,
//                    @NonNull String name,
//                    long creationTime,
//                    long updateTime,
//                    int count,
//                    boolean isTrash) {
//        this.id = id;
//        this.profileId = profileId;
//        this.parentId = parentId;
//        this.name = name;
//        this.creationTime = creationTime;
//        this.updateTime = updateTime;
//        this.count = count;
//        this.isTrash = isTrash;
//    }
//
//    private Notebook(Parcel in) {
//        id = in.readString();
//        profileId = in.readString();
//        parentId = in.readString();
//        name = in.readString();
//        creationTime = in.readLong();
//        updateTime = in.readLong();
//        count = in.readInt();
//        isTrash = in.readByte() != 0;
//    }
//
//    @NonNull
//    @Override
//    public String toString() {
//        return "Notebook{" + super.toString() +
//                "parentId='" + parentId +
//                ", name='" + name + '\'' +
//                ", creationTime=" + creationTime +
//                ", updateTime=" + updateTime +
//                ", count=" + count +
//                '}';
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(id);
//        dest.writeString(profileId);
//        dest.writeString(parentId);
//        dest.writeString(name);
//        dest.writeLong(creationTime);
//        dest.writeLong(updateTime);
//        dest.writeInt(count);
//        dest.writeByte((byte) (isTrash ? 1 : 0));
//    }
//
//    public String getParentId() {
//        return parentId;
//    }
//
//    public void setParentId(String parentId) {
//        this.parentId = parentId;
//    }
//
//    @NonNull
//    public String getName() {
//        return name;
//    }
//
//    public void setName(@NonNull String name) {
//        this.name = name;
//    }
//
//    public long getCreationTime() {
//        return creationTime;
//    }
//
//    public void setCreationTime(long creationTime) {
//        this.creationTime = creationTime;
//    }
//
//    public long getUpdateTime() {
//        return updateTime;
//    }
//
//    public void setUpdateTime(long updateTime) {
//        this.updateTime = updateTime;
//    }
//
//    public int getCount() {
//        return count;
//    }
//
//    public void setCount(int count) {
//        this.count = count;
//    }
//
//    public static final Parcelable.Creator<Notebook> CREATOR = new Parcelable.Creator<Notebook>() {
//
//        @Override
//        public Notebook createFromParcel(Parcel source) {
//            return new Notebook(source);
//        }
//
//        @Override
//        public Notebook[] newArray(int size) {
//            return new Notebook[size];
//        }
//    };
//}
