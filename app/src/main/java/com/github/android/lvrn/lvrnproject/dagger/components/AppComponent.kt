package com.github.android.lvrn.lvrnproject.dagger.components

import com.github.android.lvrn.lvrnproject.LavernaApplication
import com.github.android.lvrn.lvrnproject.dagger.modules.PresenterModule
import com.github.android.lvrn.lvrnproject.dagger.modules.RepositoryModule
import com.github.android.lvrn.lvrnproject.dagger.modules.ServiceModule
import com.github.android.lvrn.lvrnproject.dagger.modules.UtilModule
import com.github.android.lvrn.lvrnproject.dagger.modules.ViewModelModule
import com.github.android.lvrn.lvrnproject.view.activity.notedetail.NoteDetailActivity
import com.github.android.lvrn.lvrnproject.view.activity.noteeditor.impl.NoteEditorActivity
import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreation.impl.NotebookCreationDialogFragmentImpl
import com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.impl.NotebookSelectionDialogFragmentImpl
import com.github.android.lvrn.lvrnproject.view.dialog.tagediting.TagEditingDialogFragmentImpl
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.favouriteslist.impl.FavouritesListFragmentImpl
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.impl.NotebookChildrenFragmentImpl
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist.impl.NotebooksListFragmentImpl
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.impl.NotesListFragmentImpl
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.taskslist.impl.TasksListFragmentImpl
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.trashlist.impl.TrashListFragmentImpl
import com.github.android.lvrn.lvrnproject.view.fragment.notecontent.NoteContentFragment
import com.github.valhallalabs.laverna.activity.MainActivity
import dagger.Component

@Component(modules = [RepositoryModule::class, ServiceModule::class,
    PresenterModule::class, ViewModelModule::class,
    UtilModule::class])
interface AppComponent {

    fun inject(application: LavernaApplication)

    fun inject(notesListFragment: NotesListFragmentImpl)

    fun inject(noteContentFragment: NoteContentFragment)

    fun inject(tagEditingDialogFragment: TagEditingDialogFragmentImpl)

    fun inject(noteEditorActivity: NoteEditorActivity)

    fun inject(notebooksListFragment: NotebooksListFragmentImpl)

    fun inject(notebookChildrenFragment: NotebookChildrenFragmentImpl)

    fun inject(notebookSelectionDialogFragment: NotebookSelectionDialogFragmentImpl)

    fun inject(tasksListFragment: TasksListFragmentImpl)

    fun inject(trashListFragment: TrashListFragmentImpl)

    fun inject(favouritesListFragment: FavouritesListFragmentImpl)

    fun inject(notebookCreationDialogFragment: NotebookCreationDialogFragmentImpl)

    fun inject(mainActivity: MainActivity)

    fun inject(noteDetailActivity: NoteDetailActivity)
}