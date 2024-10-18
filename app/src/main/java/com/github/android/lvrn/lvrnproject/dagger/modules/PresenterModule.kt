package com.github.android.lvrn.lvrnproject.dagger.modules

import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreation.NotebookCreationPresenter
import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreation.impl.NotebookCreationPresenterImpl
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.favouriteslist.FavouritesListPresenter
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.favouriteslist.impl.FavouritesListPresenterImpl
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.NotebookChildrenPresenter
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.impl.NotebookChildrenPresenterImpl
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist.NotebooksListPresenter
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist.impl.NotebooksListPresenterImpl
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.NotesListPresenter
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.impl.NotesListPresenterImpl
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.taskslist.TasksListPresenter
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.taskslist.impl.TasksListPresenterImpl
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.trashlist.TrashListPresenter
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.trashlist.impl.TrashListPresenterImpl
import dagger.Binds
import dagger.Module

@Module
abstract class PresenterModule {

    @Binds
    abstract fun bindFavouritesListPresenter(favouritesListPresenter : FavouritesListPresenterImpl) : FavouritesListPresenter

    @Binds
    abstract fun bindNotebookChildrenPresenter(notebookChildrenPresenter : NotebookChildrenPresenterImpl) : NotebookChildrenPresenter

    @Binds
    abstract fun bindNotebooksListPresenter(notebooksListPresenter : NotebooksListPresenterImpl) : NotebooksListPresenter

    @Binds
    abstract fun bindNotesListPresenter(notesListPresenter: NotesListPresenterImpl): NotesListPresenter

    @Binds
    abstract fun bindTasksListPresenter(tasksListPresenter: TasksListPresenterImpl): TasksListPresenter

    @Binds
    abstract fun bindTrashListPresenter(trashListPresenter: TrashListPresenterImpl): TrashListPresenter

    @Binds
    abstract fun bindNotebookCreationPresenter(notebookCreationPresenter: NotebookCreationPresenterImpl) : NotebookCreationPresenter
}