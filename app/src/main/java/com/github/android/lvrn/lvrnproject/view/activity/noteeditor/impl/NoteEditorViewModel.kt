package com.github.android.lvrn.lvrnproject.view.activity.noteeditor.impl

import android.view.MenuItem
import androidx.lifecycle.ViewModel
import com.github.android.lvrn.lvrnproject.R
import com.github.android.lvrn.lvrnproject.service.core.NoteService
import com.github.android.lvrn.lvrnproject.service.form.NoteForm
import com.github.android.lvrn.lvrnproject.util.CurrentState.Companion.profileId
import com.github.android.lvrn.lvrnproject.view.util.markdownparser.MarkdownParser
import com.github.valhallalabs.laverna.persistent.entity.Notebook
import com.orhanobut.logger.Logger
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import java.util.Optional
import javax.inject.Inject
import kotlin.String

class NoteEditorViewModel @Inject constructor(
    val mNoteService: NoteService,
    val mMarkdownParser: MarkdownParser,
) : ViewModel() {

    private val contentText = MutableStateFlow("")

    private var mNotebookId: String? = null

    private var mNotebook: Notebook? = null

    var previewFlow: Flow<String> = contentText
        .debounce(300)
        .map {
            mMarkdownParser.getParsedHtml(it)
        }


    fun setContentText(value: String) {
        if (contentText.value != value) {
            contentText.value = value
        }
    }

    fun saveNewNote(title: String?, content: String?, htmlContent: String?) {
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

    fun setNotebook(notebook: Notebook?) {
        mNotebook = notebook
        mNotebookId = notebook?.id
        println(mNotebookId)
    }

    fun getNotebook(): Notebook? {
        return mNotebook
    }

    fun subscribeMenuForNotebook(menuItem: MenuItem) {
        if (mNotebook == null) {
            menuItem.setIcon(R.drawable.ic_menu_book_black_24dp)
            return
        }
        menuItem.setIcon(R.drawable.ic_menu_book_green_24dp)
    }
}