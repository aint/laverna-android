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

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import static android.R.attr.tag;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NoteServiceImpl extends ProfileDependedServiceImpl<Note, NoteForm> implements NoteService {
    //TODO: provide method for linking tags & task with note

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

        parseTasks(noteForm.getProfileId(), noteId, noteForm.getContent());

        parseTags(noteForm.getProfileId(), noteId, noteForm.getContent());
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
        //TODO: when update, check for tags and tasks
//        validateForUpdate(noteForm.getNotebookId(), noteForm.getTitle());
//        mNoteRepository.update(noteForm.toEntity(id));
    }

    /**
     *
     * @param profileId
     * @param noteId
     * @param content
     */
    private void parseTags(String profileId, String noteId, String content) {
        NoteTextParser.parseTags(content)
                .forEach(tagName -> mTagService.create(new TagForm(profileId, tagName)));
    }

    /**
     * @param profileId
     * @param noteId
     * @param content
     * @throws IllegalArgumentException
     */
    private void parseTasks(String profileId, String noteId, String content) {
        Map<String, Boolean> tasksFromNote = NoteTextParser.parseTasks(content);
        updateOrRemoveExistedTasks(noteId, tasksFromNote);
        addNewTasks(profileId, noteId, tasksFromNote);
    }

    /**
     *
     * @param noteId
     * @param tasksFromNote
     */
    private void updateOrRemoveExistedTasks(String noteId, Map<String, Boolean> tasksFromNote) {
        mTaskService.getByNote(noteId).forEach(task -> {
            if (tasksFromNote.containsKey(task.getDescription())) {
                mTaskService.update(task.getId(), new TaskForm(
                        task.getProfileId(),
                        task.getNoteId(),
                        task.getDescription(),
                        tasksFromNote.get(task.getDescription())));

                tasksFromNote.remove(task.getDescription());
            } else {
                mTaskService.remove(task.getId());
            }
        });
    }

    /**
     *
     * @param profileId
     * @param noteId
     * @param tasksFromNote
     */
    private void addNewTasks(String profileId, String noteId, Map<String, Boolean> tasksFromNote) {
        tasksFromNote.forEach((description, status) -> {
            mTaskService.create(new TaskForm(profileId, noteId, description, status));
//            mNoteRepository.addTagsToNote();

        });
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
