package com.github.android.lvrn.lvrnproject.dagger.components;

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.dagger.modules.PresenterModule;
import com.github.android.lvrn.lvrnproject.dagger.modules.RepositoryModule;
import com.github.android.lvrn.lvrnproject.dagger.modules.ServiceModule;
import com.github.android.lvrn.lvrnproject.view.activity.noteeditor.impl.NoteEditorActivityImpl;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreation.impl.NotebookCreationDialogFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.impl.NotebookSelectionDialogFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.dialog.tagediting.TagEditingDialogFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.core.favouritenotes.impl.FavouriteNotesFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist.impl.NotebooksListFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.core.allnotes.impl.AllNotesFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.taskslist.impl.TasksListFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.core.trashnotes.impl.TrashListFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.notecontent.NoteContentFragmentImpl;

import dagger.Component;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@Component(modules = {RepositoryModule.class, ServiceModule.class, PresenterModule.class})
public interface AppComponent {
    //TODO:temp compoment, remove and change it later.
    void inject(LavernaApplication application);

    void inject(AllNotesFragmentImpl notesListFragment);

    void inject(NoteContentFragmentImpl noteContentFragment);

    void inject(TagEditingDialogFragmentImpl tagEditingDialogFragment);

    void inject(NoteEditorActivityImpl noteEditorActivity);

    void inject(NotebooksListFragmentImpl notebooksListFragment);

    void inject(NotebookSelectionDialogFragmentImpl notebookSelectionDialogFragment);

    void inject(TasksListFragmentImpl tasksListFragment);

    void inject(TrashListFragmentImpl trashListFragment);

    void inject(FavouriteNotesFragmentImpl favouritesListFragment);

    void inject(NotebookCreationDialogFragmentImpl notebookCreationDialogFragment);
}
