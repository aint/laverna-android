package com.github.android.lvrn.lvrnproject.model.pojos;

import com.github.android.lvrn.lvrnproject.model.enums.TrashStatusEnum;
import com.github.android.lvrn.lvrnproject.model.enums.TypeEnum;

import java.util.Set;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NoteModel extends GeneralModel {

    /**
     * A title of the note.
     */
    private String title;

    /**
     * A plain text of note's content.
     */
    private String content;

    /**
     * Amount of tasks in the note
     */
    private int taskAll;

    /**
     * Amount of completed tasks in the note
     */
    private int taskCompleted;

    /**
     * An id of notebook, which the note is belonged.
     */
    private String notebookId;

    /**
     * A set of tag's name contained in the note.
     */
    private Set<String> tags;

    /**
     * A status of the note's belonging to favorites notes
     */
    private boolean isFavorite;

    //TODO: find out how to store files in Laverna
    private int files;

    /**
     * A set of task's text contained in the note.
     */
    private Set<String> task;

    public NoteModel(String id,
                     TypeEnum type,
                     TrashStatusEnum trash,
                     long created,
                     long updated,
                     String title,
                     String content,
                     int taskAll,
                     int taskCompleted,
                     String notebookId,
                     Set<String> tags,
                     boolean isFavorite,
                     int files,
                     Set<String> task) {
        super(id, type, trash, created, updated);
        this.title = title;
        this.content = content;
        this.taskAll = taskAll;
        this.taskCompleted = taskCompleted;
        this.notebookId = notebookId;
        this.tags = tags;
        this.isFavorite = isFavorite;
        this.files = files;
        this.task = task;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTaskAll() {
        return taskAll;
    }

    public void setTaskAll(int taskAll) {
        this.taskAll = taskAll;
    }

    public int getTaskCompleted() {
        return taskCompleted;
    }

    public void setTaskCompleted(int taskCompleted) {
        this.taskCompleted = taskCompleted;
    }

    public String getNotebookId() {
        return notebookId;
    }

    public void setNotebookId(String notebookId) {
        this.notebookId = notebookId;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getFiles() {
        return files;
    }

    public void setFiles(int files) {
        this.files = files;
    }

    public Set<String> getTask() {
        return task;
    }

    public void setTask(Set<String> task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "NoteModel{" +
                "id='" + super.getId() + '\'' +
                ", type=" + super.getType() +
                ", trash=" + super.getTrash() +
                ", created=" + super.getCreated() +
                ", updated=" + super.getUpdated() +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", taskAll=" + taskAll +
                ", taskCompleted=" + taskCompleted +
                ", notebookId='" + notebookId + '\'' +
                ", tags=" + tags +
                ", isFavorite=" + isFavorite +
                ", files=" + files +
                ", task=" + task +
                '}';
    }
}
