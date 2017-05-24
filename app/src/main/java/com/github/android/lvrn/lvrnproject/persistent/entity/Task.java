package com.github.android.lvrn.lvrnproject.persistent.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class Task extends ProfileDependedEntity {

    /**
     * An id of the note, which the task is belonged.
     */
    private String noteId;

    private String description;

    private boolean isCompleted = false;

    public Task(String id,
                String profileId,
                String noteId,
                String description,
                boolean isCompleted) {
        this.profileId = profileId;
        this.id = id;
        this.noteId = noteId;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    private Task(Parcel in) {
        this.profileId = in.readString();
        this.id = in.readString();
        this.noteId = in.readString();
        this.description = in.readString();
        this.isCompleted = in.readByte() != 0;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    @NonNull
    @Override
    public String toString() {
        return "Task{" + super.toString() +
                "noteId='" + noteId + '\'' +
                ", description='" + description + '\'' +
                ", isCompleted=" + isCompleted +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.profileId);
        dest.writeString(this.id);
        dest.writeString(this.noteId);
        dest.writeString(this.description);
        dest.writeByte((byte) (this.isCompleted ? 1 : 0));
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {

        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
