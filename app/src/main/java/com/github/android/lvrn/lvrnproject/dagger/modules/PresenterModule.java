package com.github.android.lvrn.lvrnproject.dagger.modules;

import com.github.android.lvrn.lvrnproject.service.core.NoteService;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.service.core.TaskService;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.core.favouriteslist.FavouritesListPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.core.favouriteslist.impl.FavouritesListPresenterImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.NotebookChildrenPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.impl.NotebookChildrenPresenterImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.core.notebookslist.NotebooksListPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.core.notebookslist.impl.NotebooksListPresenterImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.core.noteslist.NotesListPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.core.noteslist.impl.NotesListPresenterImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.core.taskslist.TasksListPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.core.taskslist.impl.TasksListPresenterImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.core.trashlist.TrashListPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.core.trashlist.impl.TrashListPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */
@Module
public class PresenterModule {

    @Provides
    static FavouritesListPresenter provideFavouritesListPresenter(NoteService noteService) {
        return new FavouritesListPresenterImpl(noteService);
    }

    @Provides
    static NotebookChildrenPresenter provideNotebookChildrenPresenter(NotebookService notebookService, NoteService noteService) {
        return new NotebookChildrenPresenterImpl(notebookService, noteService);
    }

    @Provides
    static NotebooksListPresenter provideNotebooksListPresenter(NotebookService notebookService) {
        return new NotebooksListPresenterImpl(notebookService);
    }

    @Provides
    static NotesListPresenter provideNotesListPresenter(NoteService noteService) {
        return new NotesListPresenterImpl(noteService);
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
