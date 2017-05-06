package com.github.android.lvrn.lvrnproject.service.extension.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.Tag;
import com.github.android.lvrn.lvrnproject.persistent.entity.Task;
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
import java.util.Map;
import java.util.Set;
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

    @Override
    public void create(@NonNull NoteForm noteForm) {
        validateForCreate(noteForm.getProfileId(), noteForm.getNotebookId(), noteForm.getTitle());
        String noteId = UUID.randomUUID().toString();
        mNoteRepository.add(noteForm.toEntity(noteId));
        parseContent(noteForm.getProfileId(), noteId, noteForm.getContent());
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

    @Override
    public void update(@NonNull String id, @NonNull NoteForm noteForm) {
        validateForUpdate(noteForm.getNotebookId(), noteForm.getTitle());
        mNoteRepository.update(noteForm.toEntity(id));
        parseContent(mNoteRepository.getById(id).get().getProfileId(), id, noteForm.getContent());
    }

    private void parseContent(String profileId, String noteId, String content) {
        parseTasks(profileId, noteId, content);
        parseTags(profileId, noteId, content);
    }

    private void parseTags(String profileId, String noteId, String content) {
        Set<String> tagNames =  NoteTextParser.parseTags(content);
        mTagService.getByNote(noteId).forEach(tag -> removeTagsFromNote(tag, tagNames, noteId));
        tagNames.forEach(tagName -> mTagService.create(new TagForm(profileId, tagName)));
    }

    private void removeTagsFromNote(Tag tag, Set<String> tagNames, String noteId) {
        if (tagNames.contains(tag.getName())) {
            tagNames.remove(tag.getName());
        } else {
            mNoteRepository.removeTagFromNote(noteId, tag.getId());
        }
    }

    private void parseTasks(String profileId, String noteId, String content) {
        Map<String, Boolean> tasksFromNote = NoteTextParser.parseTasks(content);
        mTaskService.getByNote(noteId).forEach(task -> updateOrRemoveExistedTasks(task, noteId, tasksFromNote));
        tasksFromNote.forEach((description, status) -> mTaskService.create(new TaskForm(profileId, noteId, description, status)));
    }

    /**
     *
     * @param noteId
     * @param tasksFromNote
     */
    private void updateOrRemoveExistedTasks(Task task, String noteId, Map<String, Boolean> tasksFromNote) {
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
    }

    private void validateForCreate(String profileId, String notebookId, String title) {
        super.checkProfileExistence(profileId);
        checkNotebookExistence(notebookId);
        super.checkName(title);
    }

    private void validateForUpdate(String notebookId, String title) {
        checkNotebookExistence(notebookId);
        super.checkName(title);
    }

    private void checkNotebookExistence(@Nullable String notebookId) {
        if (notebookId != null && !mNotebookService.getById(notebookId).isPresent()) {
            throw new IllegalArgumentException("The notebook is not found!");
        }
    }
}
