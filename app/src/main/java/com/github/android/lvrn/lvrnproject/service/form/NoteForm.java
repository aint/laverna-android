package com.github.android.lvrn.lvrnproject.service.form;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.google.common.base.Optional;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NoteForm extends ProfileDependedForm<Note> {

    private String notebookId;

    @NonNull
    private String title;

    @NonNull
    private String content;

    @NonNull
    private String htmlContent;

    private boolean isFavorite;

    public NoteForm(String profileId, String notebookId, @NonNull String title, @NonNull String content, @NonNull String htmlContent, boolean isFavorite) {
        super(profileId);
        this.notebookId = notebookId;
        this.title = title;
        this.content = content;
        this.htmlContent = htmlContent;
        this.isFavorite = isFavorite;
    }

    @NonNull
    @Override
    public Note toEntity(@NonNull String id) {
        return new Note(
                id,
                profileId,
                !TextUtils.isEmpty(notebookId) ? Optional.of(notebookId): Optional.absent(),
                title,
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                content,
                htmlContent,
                isFavorite);
    }

    public String getNotebookId() {
        return notebookId;
    }

    public void setNotebookId(String notebookId) {
        this.notebookId = notebookId;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    public void setContent(@NonNull String content) {
        this.content = content;
    }

    @NonNull
    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(@NonNull String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
