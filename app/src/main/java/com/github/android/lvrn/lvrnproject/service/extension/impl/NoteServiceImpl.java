package com.github.android.lvrn.lvrnproject.service.extension.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.NoteRepository;
import com.github.android.lvrn.lvrnproject.service.extension.NoteService;
import com.github.android.lvrn.lvrnproject.service.extension.NotebookService;
import com.github.android.lvrn.lvrnproject.service.extension.ProfileService;
import com.github.android.lvrn.lvrnproject.service.extension.TagService;
import com.github.android.lvrn.lvrnproject.service.extension.TaskService;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.service.form.TagForm;
import com.github.android.lvrn.lvrnproject.service.form.TaskForm;
import com.github.android.lvrn.lvrnproject.service.impl.ProfileDependedServiceImpl;
import com.github.android.lvrn.lvrnproject.service.util.NoteTextParser;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NoteServiceImpl extends ProfileDependedServiceImpl<Note, NoteForm> implements NoteService {

    private final NoteRepository mNoteRepository;

    private final TaskService mTaskService;

    private final TagService mTagService;

    private final NotebookService mNotebookService;

    @Inject
    public NoteServiceImpl(@NonNull NoteRepository noteRepository,
                           @NonNull TaskService taskService,
                           @NonNull TagService tagService,
                           @NonNull ProfileService profileService,
                           @NonNull NotebookService notebookService) {

        super(noteRepository, profileService);
        mNoteRepository = noteRepository;
        mTaskService = taskService;
        mTagService = tagService;
        mNotebookService = notebookService;
    }

    /**
     * @param profileId
     * @param notebookId
     * @param title
     * @param content
     * @param isFavorite
     * @throws IllegalArgumentException
     */
    @Override
    public void create(@NonNull NoteForm noteForm) {
        validateForCreate(noteForm.getProfileId(), noteForm.getNotebookId(), noteForm.getTitle());

        String noteId = UUID.randomUUID().toString();

        mNoteRepository.add(noteForm.toEntity(noteId));

        parseTasksAndTags(noteForm.getProfileId(), noteId, noteForm.getContent());
    }

    @NonNull
    @Override
    public List<Note> getByTitle(@NonNull String title, int from, int amount) {
        return mNoteRepository.getByTitle(title, from, amount);
    }

    @NonNull
    @Override
    public List<Note> getByNotebook(@NonNull String notebookId, int from, int amount) {
        return mNoteRepository.getByNotebook(notebookId, from, amount);
    }

    @NonNull
    @Override
    public List<Note> getByTag(@NonNull String tagId, int from, int amount) {
        return mNoteRepository.getByTag(tagId, from, amount);
    }

    /**
     * @param entity to update.
     * @throws IllegalArgumentException
     */
    @Override
    public void update(@NonNull String id, @NonNull NoteForm noteForm) {
        validateForUpdate(noteForm.getNotebookId(), noteForm.getTitle());
        mNoteRepository.update(noteForm.toEntity(id));
    }

    /**
     * @param profileId
     * @param noteId
     * @param content
     * @throws IllegalArgumentException
     */
    private void parseTasksAndTags(String profileId, String noteId, String content) {
        NoteTextParser.parseTasks(content)
                .forEach((description, status) ->
                        mTaskService.create(new TaskForm(profileId, noteId, description, status)));

        NoteTextParser.parseTags(content)
                .forEach(tagName -> mTagService.create(new TagForm(profileId, tagName)));
    }

    /**
     * @param profileId
     * @param notebookId
     * @param title
     * @throws IllegalArgumentException
     */
    private void validateForCreate(String profileId, String notebookId, String title) {
        super.checkProfileExistence(profileId);
        checkNotebookExistence(notebookId);
        super.checkName(title);
    }

    private void validateForUpdate(String notebookId, String title) {
        checkNotebookExistence(notebookId);
        super.checkName(title);
    }

    /**
     * @param notebookId
     * @throws IllegalArgumentException
     */
    private void checkNotebookExistence(@Nullable String notebookId) {
        if (notebookId != null && !mNotebookService.getById(notebookId).isPresent()) {
            throw new IllegalArgumentException("The notebook is not found!");
        }
    }
}
