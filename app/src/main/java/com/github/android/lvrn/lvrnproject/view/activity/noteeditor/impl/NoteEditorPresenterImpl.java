package com.github.android.lvrn.lvrnproject.view.activity.noteeditor.impl;

import android.view.MenuItem;
import android.widget.EditText;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.service.core.NoteService;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.util.CurrentState;
import com.github.android.lvrn.lvrnproject.view.activity.noteeditor.NoteEditorActivity;
import com.github.android.lvrn.lvrnproject.view.activity.noteeditor.NoteEditorPresenter;
import com.github.android.lvrn.lvrnproject.view.util.markdownparser.MarkdownParser;
import com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserImpl;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

class NoteEditorPresenterImpl implements NoteEditorPresenter {
    private static final String TAG = "NoteEditorPresenter";

    private NoteService mNoteService;

    private NoteEditorActivity mNoteEditorActivity;

    private MarkdownParser mMarkdownParser;

    private Disposable mEditorEditTextDisposable;

    private String mNotebookId;

    private Notebook mNotebook;

    NoteEditorPresenterImpl(NoteService noteService) {
        mNoteService = noteService;
        mMarkdownParser = new MarkdownParserImpl();
    }

    @Override
    public void subscribeEditorForPreview(EditText editText) {
        mEditorEditTextDisposable = RxTextView.textChanges(editText)
                .debounce(300, TimeUnit.MILLISECONDS)
                .map(text -> mMarkdownParser.getParsedHtml(text.toString()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(html -> mNoteEditorActivity.loadPreview(html));
    }

    @Override
    public void unsubscribeEditorForPreview() {
        if (mEditorEditTextDisposable == null || mEditorEditTextDisposable.isDisposed()) {
            return;
        }
        mEditorEditTextDisposable.dispose();
        Logger.d("Note editor is unsubscribed for preview");
    }

    @Override
    public void bindView(NoteEditorActivity noteEditorActivity) {
        this.mNoteEditorActivity = noteEditorActivity;
        Logger.d("Node editor is binded to its presenter.");
    }

    @Override
    public void unbindView() {
        mNoteEditorActivity = null;
        Logger.d("Node editor is unbinded to its presenter.");
    }

    @Override
    public void saveNewNote(String title, String content, String htmlContent) {
        NoteForm noteForm = new NoteForm(CurrentState.profileId, false, mNotebookId, title, content, htmlContent, false);

        Flowable.just(noteForm)
                .doOnNext(noteFormToSend -> mNoteService.openConnection())
                .map(noteFormToSend -> mNoteService.create(noteFormToSend))
                .doOnNext(stringOptional -> mNoteService.closeConnection())
                .filter(stringOptional -> !stringOptional.isPresent())
                .subscribe(stringOptional -> {
                    Logger.wtf("New note is note created due to unforeseen circumstances.");
                    throw new RuntimeException();
                });
    }

    @Override
    public void setNotebook(Notebook notebook) {
        mNotebook = notebook;
        if (notebook != null){
            mNotebookId = notebook.getId();
        } else  mNotebookId = null;
        System.out.println(mNotebookId);
    }

    @Override
    public Notebook getNotebook() {
        return mNotebook;
    }

    @Override
    public void subscribeMenuForNotebook(MenuItem menuItem) {
        if (mNotebook == null) {
            menuItem.setIcon(R.drawable.ic_menu_book_black_24dp);
            return;
        }
        menuItem.setIcon(R.drawable.ic_menu_book_green_24dp);
    }

}
