package com.github.android.lvrn.lvrnproject.dagger.components;

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.dagger.modules.RepositoryModule;
import com.github.android.lvrn.lvrnproject.dagger.modules.ServiceModule;
import com.github.android.lvrn.lvrnproject.view.activity.noteeditor.impl.NoteEditorActivityImpl;

import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreate.impl.NotebookCreateDialogFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.NotebookContentFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.NotebookFragmentImpl;

import com.github.android.lvrn.lvrnproject.view.dialog.tagediting.TagEditingDialogFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.impl.NotebookSelectionDialogFragmentImpl;

import com.github.android.lvrn.lvrnproject.view.fragment.singlenote.SingleNoteFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.TaskFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.TrashFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.allnotes.impl.AllNotesFragmentImpl;

import dagger.Component;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@Component(modules = {RepositoryModule.class, ServiceModule.class})
public interface AppComponent {
    //TODO:temp compoment, remove and change it later.
    void inject(LavernaApplication application);

    void inject(AllNotesFragmentImpl allNotesFragment);

    void inject(SingleNoteFragmentImpl singleNoteFragmentImpl);

    void inject(TagEditingDialogFragmentImpl tagEditingDialogFragment);

    void inject(NoteEditorActivityImpl noteEditorActivityImpl);

    void inject(NotebookFragmentImpl notebookFragment);

    void inject(NotebookContentFragmentImpl noteAndNotebookTogetherFragment);

    void inject(NotebookSelectionDialogFragmentImpl notebookSelectionDialogFragment);

    void inject(TaskFragmentImpl taskFragment);

    void inject(TrashFragmentImpl trashFragment);

    void inject(NotebookCreateDialogFragmentImpl notebookCreateDialogFragment);

}
