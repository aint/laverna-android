package com.github.android.lvrn.lvrnproject.dagger.modules;

import com.github.android.lvrn.lvrnproject.service.core.NoteService;
import com.github.android.lvrn.lvrnproject.service.core.TaskService;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.favouriteslist.FavouritesListPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.favouriteslist.impl.FavouritesListPresenterImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.NotebookChildrenPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.impl.NotebookChildrenPresenterImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist.NotebooksListPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist.impl.NotebooksListPresenterImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.NotesListPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.impl.NotesListPresenterImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.taskslist.TasksListPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.taskslist.impl.TasksListPresenterImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.trashlist.TrashListPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.trashlist.impl.TrashListPresenterImpl;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 * @author Bei Anrii <bei.andrii.dev@gmail.com>
 */
@Module
public abstract class PresenterModule {

    @Binds
    abstract FavouritesListPresenter bindFavouritesListPresenter(FavouritesListPresenterImpl favouritesListPresenter);

    @Binds
    abstract NotebookChildrenPresenter bindNotebookChildrenPresenter(NotebookChildrenPresenterImpl notebookChildrenPresenter);

    @Binds
    abstract NotebooksListPresenter bindNotebooksListPresenter(NotebooksListPresenterImpl notebooksListPresenter);

    @Binds
    abstract NotesListPresenter bindNotesListPresenter(NotesListPresenterImpl notesListPresenter);

    @Binds
    abstract TasksListPresenter bindTasksListPresenter(TasksListPresenterImpl tasksListPresenter);

    @Binds
    abstract TrashListPresenter bindTrashListPresenter(TrashListPresenterImpl trashListPresenter);
}
