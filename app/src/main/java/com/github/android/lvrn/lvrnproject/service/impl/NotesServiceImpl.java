package com.github.android.lvrn.lvrnproject.service.impl;

import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Notebook;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Tag;
import com.github.android.lvrn.lvrnproject.persistent.repository.NotesRepository;
import com.github.android.lvrn.lvrnproject.service.NotebooksService;
import com.github.android.lvrn.lvrnproject.service.NotesService;
import com.github.android.lvrn.lvrnproject.service.ProfilesService;
import com.github.android.lvrn.lvrnproject.service.TagsService;
import com.github.android.lvrn.lvrnproject.service.TasksService;
import com.github.android.lvrn.lvrnproject.service.core.impl.ProfileDependedServiceImpl;
import com.github.android.lvrn.lvrnproject.service.util.NoteTextParser;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NotesServiceImpl extends ProfileDependedServiceImpl<Note> implements NotesService {

    private final NotesRepository mNotesRepository;

    private final TasksService mTasksService;

    private final TagsService mTagsService;

    private final NotebooksService mNotebooksService;

    @Inject
    public NotesServiceImpl(NotesRepository notesRepository,
                            TasksService tasksService,
                            TagsService tagsService,
                            ProfilesService profilesService,
                            NotebooksService notebooksService) {

        super(notesRepository, profilesService);
        mNotesRepository = notesRepository;
        mTasksService = tasksService;
        mTagsService = tagsService;
        mNotebooksService = notebooksService;
    }

    @Override
    public void create(String profileId,
                       String notebookId,
                       String title,
                       String content,
                       boolean isFavorite) throws IllegalArgumentException {
        validate(profileId, notebookId, title);

        //TODO: find out way to generate id
        String noteId = "id";
        mNotesRepository.add(new Note(
                noteId,
                profileId,
                notebookId,
                title,
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                content,
                isFavorite));

        //TODO: fin out what to do in case of an error.
        NoteTextParser.parseTasks(content)
                .forEach((description, status) ->
                        mTasksService.create(profileId, noteId, description, status));

        NoteTextParser.parseTags(content)
                .forEach(tagName -> mTagsService.create(profileId, tagName));

    }

    @Override
    public List<Note> getByNotebook(Notebook notebook, int from, int amount) {
        return mNotesRepository.getByNotebook(notebook, from, amount);
    }

    @Override
    public List<Note> getByTag(Tag tag, int from, int amount) {
        return mNotesRepository.getByTag(tag, from, amount);
    }

    @Override
    public void update(Note entity) throws IllegalArgumentException {
        validate(entity.getProfileId(), entity.getProfileId(), entity.getTitle());
        mNotesRepository.update(entity);
    }

    private void validate(String profileId, String notebookId, String title) throws IllegalArgumentException {
        checkProfileExistence(profileId);
        checkNotebookExistence(notebookId);
        checkName(title);
    }

    private void checkNotebookExistence(String notebookId) throws IllegalArgumentException {
        if (notebookId != null && !mNotebooksService.getById(notebookId).isPresent()) {
            throw new IllegalArgumentException("The notebook is not found!");
        }
    }
}
