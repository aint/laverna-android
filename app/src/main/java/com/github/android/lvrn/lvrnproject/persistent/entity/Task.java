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
    @NonNull
    private String noteId;

    @NonNull
    private String description;

    private boolean isCompleted = false;

    public Task(String id,
                String profileId,
                @NonNull String noteId,
                @NonNull String description,
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

    @NonNull
    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(@NonNull String profileId) {
        this.profileId = profileId;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(@NonNull String noteId) {
        this.noteId = noteId;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
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
