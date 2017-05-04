package com.github.android.lvrn.lvrnproject.service.form;

import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.entity.Entity;
import com.github.android.lvrn.lvrnproject.persistent.entity.Note;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NoteForm extends ProfileDependedForm<Note> {

    private String notebookId;

    private String title;

    private String content;

    private boolean isFavorite;

    public NoteForm(String profileId, String notebookId, String title, String content, boolean isFavorite) {
        super(profileId);
        this.notebookId = notebookId;
        this.title = title;
        this.content = content;
        this.isFavorite = isFavorite;
    }

    @NonNull
    @Override
    public Note toEntity(@NonNull String id) {
        return new Note(
                id,
                profileId,
                notebookId,
                title,
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                content,
                isFavorite);
    }

    public String getNotebookId() {
        return notebookId;
    }

    public void setNotebookId(String notebookId) {
        this.notebookId = notebookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
