package com.github.android.lvrn.lvrnproject.view.activity.noteeditor.impl

import android.view.MenuItem
import android.widget.EditText
import com.github.android.lvrn.lvrnproject.R
import com.github.android.lvrn.lvrnproject.service.core.NoteService
import com.github.android.lvrn.lvrnproject.service.form.NoteForm
import com.github.android.lvrn.lvrnproject.util.CurrentState.Companion.profileId
import com.github.android.lvrn.lvrnproject.view.activity.noteeditor.NoteEditorActivity
import com.github.android.lvrn.lvrnproject.view.activity.noteeditor.NoteEditorPresenter
import com.github.android.lvrn.lvrnproject.view.util.markdownparser.MarkdownParser
import com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserImpl
import com.github.valhallalabs.laverna.persistent.entity.Notebook
import com.jakewharton.rxbinding2.widget.RxTextView
import com.orhanobut.logger.Logger
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.Optional
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

class NoteEditorPresenterImpl @Inject constructor(
    private val mNoteService: NoteService,
    private val mMarkdownParser: MarkdownParser = MarkdownParserImpl(),
) : NoteEditorPresenter {

    private val TAG = "NoteEditorPresenter"

    private var mNoteEditorActivity: NoteEditorActivity? = null

    private var mEditorEditTextDisposable: Disposable? = null

    private var mNotebookId: String? = null

    private var mNotebook: Notebook? = null
    override fun subscribeEditorForPreview(editText: EditText?) {
        mEditorEditTextDisposable = RxTextView.textChanges(editText!!)
            .debounce(300, TimeUnit.MILLISECONDS)
            .map { text: CharSequence ->
                mMarkdownParser.getParsedHtml(
                    text.toString()
                )
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { html: String? ->
                mNoteEditorActivity!!.loadPreview(
                    html!!
                )
            }
    }

    override fun unsubscribeEditorForPreview() {
        if (mEditorEditTextDisposable == null || mEditorEditTextDisposable!!.isDisposed) {
            return
        }
        mEditorEditTextDisposable!!.dispose()
        Logger.d("Note editor is unsubscribed for preview")
    }

    override fun bindView(noteEditorActivity: NoteEditorActivity?) {
        this.mNoteEditorActivity = noteEditorActivity
        Logger.d("Node editor is binded to its presenter.")
    }

    override fun unbindView() {
        mNoteEditorActivity = null
        Logger.d("Node editor is unbinded to its presenter.")
    }

    override fun saveNewNote(title: String?, content: String?, htmlContent: String?) {
        val noteForm = NoteForm(
            profileId!!, false, mNotebookId,
            title!!, content!!, htmlContent!!, false
        )

        Flowable.just(noteForm)
            .doOnNext { noteFormToSend: NoteForm? -> mNoteService!!.openConnection() }
            .map { noteFormToSend: NoteForm ->
                mNoteService!!.create(
                    noteFormToSend
                )
            }
            .doOnNext { stringOptional: Optional<String>? -> mNoteService!!.closeConnection() }
            .filter { stringOptional: Optional<String> -> !stringOptional.isPresent }
            .subscribe { stringOptional: Optional<String>? ->
                Logger.wtf("New note is note created due to unforeseen circumstances.")
                throw RuntimeException()
            }
    }

    override fun setNotebook(notebook: Notebook?) {
        mNotebook = notebook
        mNotebookId = notebook?.id
        println(mNotebookId)
    }

    override fun getNotebook(): Notebook? {
        return mNotebook
    }

    override fun subscribeMenuForNotebook(menuItem: MenuItem) {
        if (mNotebook == null) {
            menuItem.setIcon(R.drawable.ic_menu_book_black_24dp)
            return
        }
        menuItem.setIcon(R.drawable.ic_menu_book_green_24dp)
    }
}