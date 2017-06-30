package com.github.android.lvrn.lvrnproject.dagger.modules;

import com.github.android.lvrn.lvrnproject.service.core.NoteService;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.service.core.TaskService;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.core.favouritenotes.FavouriteNotesPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.core.favouritenotes.impl.FavouriteNotesPresenterImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist.NotebooksListPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist.impl.NotebooksListPresenterImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.core.allnotes.AllNotesPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.core.allnotes.impl.AllNotesPresenterImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.taskslist.TasksListPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.taskslist.impl.TasksListPresenterImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.core.trashnotes.TrashListPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.core.trashnotes.impl.TrashListPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */
@Module
public class PresenterModule {

    @Provides
    static FavouriteNotesPresenter provideFavouritesListPresenter(NoteService noteService) {
        return new FavouriteNotesPresenterImpl(noteService);
    }

    @Provides
    static NotebooksListPresenter provideNotebooksListPresenter(NotebookService notebookService) {
        return new NotebooksListPresenterImpl(notebookService);
    }

    @Provides
    static AllNotesPresenter provideNotesListPresenter(NoteService noteService) {
        return new AllNotesPresenterImpl(noteService);
    }

    @Provides
    static TasksListPresenter provideTasksListPresenter(TaskService taskService, NoteService noteService) {
        return new TasksListPresenterImpl(taskService, noteService);
    }

    @Provides
    static TrashListPresenter provideTrashListPresenter(NoteService noteService) {
        return new TrashListPresenterImpl(noteService);
    }
}
