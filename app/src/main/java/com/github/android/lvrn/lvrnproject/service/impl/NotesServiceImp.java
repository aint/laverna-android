package com.github.android.lvrn.lvrnproject.service.impl;

import com.github.android.lvrn.lvrnproject.dagger.DaggerComponentsContainer;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Notebook;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Profile;
import com.github.android.lvrn.lvrnproject.persistent.repository.impl.NotesRepositoryImp;
import com.github.android.lvrn.lvrnproject.service.NotesService;
import com.github.android.lvrn.lvrnproject.service.util.NoteTextParser;
import com.google.common.base.Optional;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NotesServiceImp implements NotesService {
    //TODO: implement builder pattern for adding new Notes.

    @Inject
    NotesRepositoryImp notesRepository;

    public NotesServiceImp() {
        DaggerComponentsContainer.getRepositoryComponent().injectNotesService(this);
    }

    @Override
    public void create(Profile profile,
                       Notebook notebook,
                       String title,
                       String content,
                       boolean isFavorite) {
        //TODO: throw exception if profile is null, or not exist.
        //TODO: throw exception if notebook is not exist.
        //TODO: throw exception if title is null or equals "".


        //TODO: find out way to generate id
        String notebookId = notebook != null ? notebook.getId() : null;

        Note note = new Note(
                "id" +System.currentTimeMillis(),
                profile.getId(),
                notebook != null ? notebook.getId() : null,
                title,
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                content,
                isFavorite);

        //TODO: come up to way to saves tasks and tags.
        Map<String, Boolean> tasksNames = NoteTextParser.parseTasks(content);

    }

    @Override
    public void openConnection() {
        notesRepository.openDatabaseConnection();
    }

    @Override
    public void closeConnection() {
        notesRepository.closeDatabaseConnection();
    }

    @Override
    public void remove(Note entity) {
        notesRepository.remove(entity);
    }

    @Override
    public Optional<Note> getById(String id) {
        return notesRepository.getById(id);
    }

    @Override
    public List<Note> getByProfile(Profile profile, int from, int amount) {
        return notesRepository.getByProfile(profile, from, amount);
    }

}
