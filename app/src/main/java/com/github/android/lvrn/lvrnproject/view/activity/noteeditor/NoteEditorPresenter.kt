package com.github.android.lvrn.lvrnproject.view.activity.noteeditor

import android.view.MenuItem
import android.widget.EditText
import com.github.valhallalabs.laverna.persistent.entity.Notebook

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

interface NoteEditorPresenter {

    /**
     * A method which subscribes an edit text to webview for showing previews of a markdown editor.
     * @param editText an edit text to subscribe.
     */
    fun subscribeEditorForPreview(editText: EditText?)

    /**
     * A method which unsubscribes and edit text to webiew for showing previews of a markdown editor.
     */
    fun unsubscribeEditorForPreview()

    /**
     * A method which binds a view to a presenter.
     */
    fun bindView(noteEditorActivity: NoteEditorActivity?)

    /**
     * A method which unbinds a view to a presenter.
     */
    fun unbindView()

    /**
     * A method which saves new note.
     */
    fun saveNewNote(
        /*String notebookId, */
        title: String?, content: String?, htmlContent: String?,
    )

    fun setNotebook(notebook: Notebook?)

    fun getNotebook(): Notebook?

    fun subscribeMenuForNotebook(menuItem: MenuItem)
}