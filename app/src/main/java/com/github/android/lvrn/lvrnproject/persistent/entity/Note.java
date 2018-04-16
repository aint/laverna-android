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
//public class Note extends TrashDependedEntity {
//
//    /**
//     * An id of a notebook, which the note is belonged. In case, if the note doesn't belong to any
//     * notebook, then notebookId equals to "0".
//     */
//    @NonNull
//    private String notebookId;
//
//    @NonNull
//    private String title;
//
//    /**
//     * A date of the model's creation in milliseconds.
//     */
//    private long creationTime;
//
//    /**
//     * A date of the model's creation in milliseconds.
//     */
//    private long updateTime;
//
//    /**
//     * A plain text of a note's content.
//     */
//    @NonNull
//    private String content;
//
//    /**
//     * A note's content in html.
//     */
//    @NonNull
//    private String htmlContent;
//
//    private boolean isFavorite = false;
//
//    public Note(String id,
//                @NonNull String profileId,
//                @NonNull String notebookId,
//                @NonNull String title,
//                long creationTime,
//                long updateTime,
//                @NonNull String content,
//                @NonNull String htmlContent,
//                boolean isFavorite,
//                boolean isTrash) {
//        this.id = id;
//        this.profileId = profileId;
//        this.notebookId = notebookId;
//        this.title = title;
//        this.creationTime = creationTime;
//        this.updateTime = updateTime;
//        this.content = content;
//        this.htmlContent = htmlContent;
//        this.isFavorite = isFavorite;
//        this.isTrash = isTrash;
//    }
//
//    private Note(Parcel in) {
//        id = in.readString();
//        profileId = in.readString();
//        notebookId = in.readString();
//        title = in.readString();
//        creationTime = in.readLong();
//        updateTime = in.readLong();
//        content = in.readString();
//        htmlContent = in.readString();
//        isFavorite = in.readByte() != 0;
//        isTrash = in.readByte() != 0;
//    }
//
//    @NonNull
//    @Override
//    public String toString() {
//        return "Note{" +
//                "title=" + title +
//                ", notebookId=" + notebookId +
//                ", profileId=" + profileId +
//                ", creationTime=" + creationTime +
//                ", updateTime=" + updateTime +
////                ", content='" + content + '\'' +
//                ", isFavorite=" + isFavorite +
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
//        dest.writeString(notebookId);
//        dest.writeString(title);
//        dest.writeLong(creationTime);
//        dest.writeLong(updateTime);
//        dest.writeString(content);
//        dest.writeString(htmlContent);
//        dest.writeByte((byte) (isFavorite ? 1 : 0));
//        dest.writeByte((byte) (isTrash ? 1 : 0));
//    }
//
//    @NonNull
//    public String getId() {
//        return id;
//    }
//
//    public void setId(@NonNull String id) {
//        this.id = id;
//    }
//
//    @NonNull
//    public String getProfileId() {
//        return profileId;
//    }
//
//    public void setProfileId(@NonNull String profileId) {
//        this.profileId = profileId;
//    }
//
//    @NonNull
//    public String getNotebookId() {
//        return notebookId;
//    }
//
//    public void setNotebookId(@NonNull String notebookId) {
//        this.notebookId = notebookId;
//    }
//
//    @NonNull
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(@NonNull String title) {
//        this.title = title;
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
//    @NonNull
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(@NonNull String content) {
//        this.content = content;
//    }
//
//    @NonNull
//    public String getHtmlContent() {
//        return htmlContent;
//    }
//
//    public void setHtmlContent(@NonNull String htmlContent) {
//        this.htmlContent = htmlContent;
//    }
//
//    public boolean isFavorite() {
//        return isFavorite;
//    }
//
//    public void setFavorite(boolean favorite) {
//        isFavorite = favorite;
//    }
//
//    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>() {
//
//        @Override
//        public Note createFromParcel(Parcel source) {
//            return new Note(source);
//        }
//
//        @Override
//        public Note[] newArray(int size) {
//            return new Note[size];
//        }
//    };
//}
