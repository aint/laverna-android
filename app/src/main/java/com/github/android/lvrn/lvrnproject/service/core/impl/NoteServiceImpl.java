package com.github.android.lvrn.lvrnproject.service.core.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.github.valhallalabs.laverna.persistent.entity.Tag;
import com.github.valhallalabs.laverna.persistent.entity.Task;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.NoteRepository;
import com.github.android.lvrn.lvrnproject.service.core.NoteService;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.service.core.ProfileService;
import com.github.android.lvrn.lvrnproject.service.core.TagService;
import com.github.android.lvrn.lvrnproject.service.core.TaskService;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.service.form.TagForm;
import com.github.android.lvrn.lvrnproject.service.form.TaskForm;
import com.github.android.lvrn.lvrnproject.service.impl.TrashDependedServiceImpl;
import com.github.android.lvrn.lvrnproject.service.util.NoteTextParser;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.github.valhallalabs.laverna.persistent.entity.Note;
import com.google.common.base.Optional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NoteServiceImpl extends TrashDependedServiceImpl<Note, NoteForm> implements NoteService {

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
    public Optional<String> create(@NonNull NoteForm noteForm) {
        String noteId = UUID.randomUUID().toString();
        noteForm.setContent(NoteTextParser.parseSingleQuotes(noteForm.getContent()));
        if (validateForCreate(noteForm.getProfileId(), noteForm.getNotebookId(), noteForm.getTitle()) && mNoteRepository.add(noteForm.toEntity(noteId))) {
            parseContent(noteForm.getProfileId(), noteId, noteForm.getContent());
            return Optional.of(noteId);
        }
        return Optional.absent();
    }

    @Override
    public void save(@NonNull Note note) {
        mNoteRepository.add(note);
    }

    @NonNull
    @Override
    public List<Note> getByTitle(@NonNull String profileId, @NonNull String title, @NonNull PaginationArgs paginationArgs) {
        return mNoteRepository.getByTitle(profileId, title, paginationArgs);
    }

    @NonNull
    @Override
    public List<Note> getTrashByTitle(@NonNull String profileId, @NonNull String title, @NonNull PaginationArgs paginationArgs) {
        return mNoteRepository.getTrashByTitle(profileId, title, paginationArgs);
    }

    @NonNull
    @Override
    public List<Note> getByNotebook(@NonNull String notebookId, @NonNull PaginationArgs paginationArgs) {
        return mNoteRepository.getByNotebook(notebookId, paginationArgs);
    }

    @NonNull
    @Override
    public List<Note> getFavourites(@NonNull String profileId, @NonNull PaginationArgs paginationArgs) {
        return mNoteRepository.getFavourites(profileId, paginationArgs);
    }

    @NonNull
    @Override
    public List<Note> getFavouritesByTitle(@NonNull String profileId, @NonNull String title, @NonNull PaginationArgs paginationArgs) {
        return mNoteRepository.getFavouritesByTitle(profileId, title, paginationArgs);
    }

    @NonNull
    @Override
    public List<Note> getByTag(@NonNull String tagId, @NonNull PaginationArgs paginationArgs) {
        return mNoteRepository.getByTag(tagId, paginationArgs);
    }

    @Override
    public boolean setNoteFavourite(String entityId) {
        return mNoteRepository.changeNoteFavouriteStatus(entityId, true);
    }

    @Override
    public boolean setNoteUnFavourite(String entityId) {
        return mNoteRepository.changeNoteFavouriteStatus(entityId,false);
    }

    @Override
    public boolean update(@NonNull String id, @NonNull NoteForm noteForm) {
        noteForm.setContent(NoteTextParser.parseSingleQuotes(noteForm.getContent()));
        if (validateForUpdate(noteForm.getNotebookId(), noteForm.getTitle()) && mNoteRepository.update(noteForm.toEntity(id))) {
            parseContent(mNoteRepository.getById(id).get().getProfileId(), id, noteForm.getContent());
            return true;
        }
        return false;
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
        mTagService.openConnection();
        mTagService.getByNote(noteId).forEach(tag -> removeTagsFromNote(tag, tagNames, noteId));
        tagNames.forEach(tagName -> createTagAndAddToNote(profileId, tagName, noteId));
        mTagService.closeConnection();
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
     * A method which creates a tag and a relation between the tag and a note.
     * @param profileId an id of a profile.
     * @param tagName a name of a tag.
     * @param noteId an id on a note.
     */
    private void createTagAndAddToNote(String profileId, String tagName, String noteId) {
        List<Tag> tags = mTagService.getByName(profileId, tagName, new PaginationArgs(0, 1));
        if (tags.size() != 0) {
            mNoteRepository.addTagToNote(noteId, tags.get(0).getId());
            return;
        }
        Optional<String> tagIdOptional = mTagService.create(new TagForm(profileId, tagName));
        if(tagIdOptional.isPresent()) {
            mNoteRepository.addTagToNote(noteId, tagIdOptional.get());
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
        mTaskService.openConnection();
        mTaskService.getByNote(noteId).forEach(task -> updateOrRemoveExistedTasks(task, tasksFromNote));
        tasksFromNote.forEach((description, status) -> mTaskService.create(new TaskForm(profileId, noteId, description, status)));
        mTaskService.closeConnection();
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
     * @return a boolean result of a validation.
     */
    private boolean validateForCreate(String profileId, String notebookId, String title) {
        return super.checkProfileExistence(profileId) && checkNotebookExistence(notebookId) && !TextUtils.isEmpty(title);
    }

    /**
     * A method which validates a form in the update method.
     * @param notebookId an parent notebook id.
     * @param title a title of an entity.
     * @return a boolean result of a validation.
     */
    private boolean validateForUpdate(String notebookId, String title) {
        return checkNotebookExistence(notebookId) && !TextUtils.isEmpty(title);
    }

    /**
     * A method which checks an existence of notebook in a database.
     * @param notebookId an id of a required notebook.
     * @return a boolean result of a validation.
     */
    private boolean checkNotebookExistence(@Nullable String notebookId) {
        mNotebookService.openConnection();
        boolean result = (notebookId == null) || mNotebookService.getById(notebookId).isPresent();
        mNotebookService.closeConnection();
        return result;
    }
}
