package com.github.android.lvrn.lvrnproject.dagger.components;

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.dagger.modules.RepositoryModule;
import com.github.android.lvrn.lvrnproject.dagger.modules.ServiceModule;
import com.github.android.lvrn.lvrnproject.view.activities.noteeditor.impl.NoteEditorActivityImpl;

import com.github.android.lvrn.lvrnproject.view.fragments.NotebookContentFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragments.NotebookFragmentImpl;

import com.github.android.lvrn.lvrnproject.view.dialog.TagEditingDialogFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.impl.NotebookSelectionDialogFragmentImpl;

import com.github.android.lvrn.lvrnproject.view.fragments.SingleNoteFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragments.TaskFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragments.allnotes.impl.AllNotesFragmentImpl;

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

}
