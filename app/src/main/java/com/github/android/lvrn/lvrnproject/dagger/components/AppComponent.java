package com.github.android.lvrn.lvrnproject.dagger.components;

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.dagger.modules.RepositoryModule;
import com.github.android.lvrn.lvrnproject.dagger.modules.ServiceModule;
import com.github.android.lvrn.lvrnproject.view.activity.noteeditor.impl.NoteEditorActivityImpl;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreate.impl.NotebookCreateDialogFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.impl.NotebookSelectionDialogFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.dialog.tagediting.TagEditingDialogFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.NotebookContentFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.favouriteslist.impl.FavouritesListFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist.impl.NotebooksListFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.impl.NotesListFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.trashlist.impl.TrashListFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.singlenote.SingleNoteFragmentImpl;

import dagger.Component;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@Component(modules = {RepositoryModule.class, ServiceModule.class})
public interface AppComponent {
    //TODO:temp compoment, remove and change it later.
    void inject(LavernaApplication application);

    void inject(NotesListFragmentImpl allNotesFragment);

    void inject(SingleNoteFragmentImpl singleNoteFragmentImpl);

    void inject(TagEditingDialogFragmentImpl tagEditingDialogFragment);

    void inject(NoteEditorActivityImpl noteEditorActivityImpl);

    void inject(NotebooksListFragmentImpl notebooksListFragment);

    void inject(NotebookContentFragmentImpl noteAndNotebookTogetherFragment);

    void inject(NotebookSelectionDialogFragmentImpl notebookSelectionDialogFragment);

//    void inject(AllTasksFragmentImpl taskFragment);

    void inject(TrashListFragmentImpl trashFragment);

    void inject(FavouritesListFragmentImpl favouritesListFragment);

    void inject(NotebookCreateDialogFragmentImpl notebookCreateDialogFragment);

}
