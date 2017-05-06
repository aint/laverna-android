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

    /**
     * A method which calls methods of parsing tasks and tags from a note content.
     * @param profileId an id of a profile.
     * @param noteId an id of a note.
     * @param content a text content from a note.
     */
    private void parseContent(String profileId, String noteId, String content) {
        parseTasks(profileId, noteId, content);
        parseTags(profileId, noteId, content);
    }

    /**
     * A method which parses tags from a note content and then creates new tags or removes relations
     * between a note and tags.
     * @param profileId an id of a profile.
     * @param noteId an id of note.
     * @param content a text content from a note.
     */
    private void parseTags(String profileId, String noteId, String content) {
        Set<String> tagNames =  NoteTextParser.parseTags(content);
        mTagService.getByNote(noteId).forEach(tag -> removeTagsFromNote(tag, tagNames, noteId));
        tagNames.forEach(tagName -> mTagService.create(new TagForm(profileId, tagName)));
    }

    /**
     * A method which removes a relation a note and tag, if the last one is not present in content
     * anymore.
     * @param tag a tag to check.
     * @param tagNames a set of tags parsed from a note.
     * @param noteId an id of a note.
     */
    private void removeTagsFromNote(Tag tag, Set<String> tagNames, String noteId) {
        if (tagNames.contains(tag.getName())) {
            tagNames.remove(tag.getName());
        } else {
            mNoteRepository.removeTagFromNote(noteId, tag.getId());
        }
    }

    /**
     * A method which parses tasks from a note content and then creates/updated/removes it in database.
     * @param profileId an id of a profile.
     * @param noteId an id of note.
     * @param content a text content from a note.
     */
    private void parseTasks(String profileId, String noteId, String content) {
        Map<String, Boolean> tasksFromNote = NoteTextParser.parseTasks(content);
        mTaskService.getByNote(noteId).forEach(task -> updateOrRemoveExistedTasks(task, tasksFromNote));
        tasksFromNote.forEach((description, status) -> mTaskService.create(new TaskForm(profileId, noteId, description, status)));
    }

    /**
     * A method which updates a received task if it presents in database or removes it in other case.
     * @param task a task to update or remove.
     * @param tasksFromNote a map of tasks parsed from a note content.
     */
    private void updateOrRemoveExistedTasks(Task task, Map<String, Boolean> tasksFromNote) {
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

    /**
     * A method which validates a form in the create method.
     * @param profileId an id of a profile.
     * @param notebookId an parent notebook id.
     * @param title a title of an entity.
     */
    private void validateForCreate(String profileId, String notebookId, String title) {
        super.checkProfileExistence(profileId);
        checkNotebookExistence(notebookId);
        super.checkName(title);
    }

    /**
     * A method which validates a form in the update method.
     * @param notebookId an parent notebook id.
     * @param title a title of an entity.
     */
    private void validateForUpdate(String notebookId, String title) {
        checkNotebookExistence(notebookId);
        super.checkName(title);
    }

    /**
     * A method which checks an existence of notebook in a database.
     * @param notebookId an id of a required notebook.
     * @throws IllegalArgumentException in case if notebook is not found.
     */
    private void checkNotebookExistence(@Nullable String notebookId) {
        if (notebookId != null && !mNotebookService.getById(notebookId).isPresent()) {
            throw new IllegalArgumentException("The notebook is not found!");
        }
    }
}
