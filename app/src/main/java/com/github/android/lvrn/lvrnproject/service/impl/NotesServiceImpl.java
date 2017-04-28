package com.github.android.lvrn.lvrnproject.service.impl;

import com.github.android.lvrn.lvrnproject.dagger.DaggerComponentsContainer;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Notebook;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Tag;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.NotesRepositoryImpl;
import com.github.android.lvrn.lvrnproject.service.NotebooksService;
import com.github.android.lvrn.lvrnproject.service.NotesService;
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

    @Inject NotesRepositoryImpl mNotesRepository;

    private TasksService tasksService;

    private TagsService tagsService;


    public NotesServiceImpl() {
        DaggerComponentsContainer.getRepositoryComponent().injectNotesService(this);
        tasksService = new TasksServiceImpl();
        tagsService = new TagsServiceImpl();
    }

    @Override
    public void create(String profileId,
                       String notebookId,
                       String title,
                       String content,
                       boolean isFavorite) {
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
                        tasksService.create(profileId, noteId, description, status));

        NoteTextParser.parseTags(content)
                .forEach(tagName -> tagsService.create(profileId, tagName));

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
    public void update(Note entity) {
        validate(entity.getProfileId(), entity.getProfileId(), entity.getTitle());
        mNotesRepository.update(entity);
    }

    private void parseTagsAndTasks(String content) {

    }

    private void validate(String profileId, String notebookId, String title) {
        checkProfileExistence(profileId);
        checkNotebookExistence(notebookId);
        checkName(title);
    }

    private void checkNotebookExistence(String notebookId) throws NullPointerException {
        NotebooksService notebooksService = new NotebooksServiceImpl();
        if (notebookId != null && !notebooksService.getById(notebookId).isPresent()) {
            throw new NullPointerException("The notebook is not found!");
        }
    }
}
