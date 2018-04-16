package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren;

import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.service.form.NotebookForm;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListPresenter;
import com.github.valhallalabs.laverna.persistent.entity.Note;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface NotebookChildrenPresenter {

    void initializeListsPresenters(Notebook notebook);

    EntitiesListPresenter<Note, NoteForm> getNotesListPresenter();

    EntitiesListPresenter<Notebook, NotebookForm> getNotebooksListPresenter();
}
