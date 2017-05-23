package com.github.android.lvrn.lvrnproject.view.activities.noteeditoractivity.impl;

import android.util.Log;
import android.widget.EditText;

import com.github.android.lvrn.lvrnproject.view.activities.noteeditoractivity.NoteEditorActivity;
import com.github.android.lvrn.lvrnproject.view.activities.noteeditoractivity.NoteEditorPresenter;
import com.github.android.lvrn.lvrnproject.view.util.markdownparser.MarkdownParser;
import com.github.android.lvrn.lvrnproject.view.util.markdownparser.impl.MarkdownParserImpl;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

class NoteEditorPresenterImpl implements NoteEditorPresenter {
    private static final String TAG = "NoteEditorPresenter";

    private NoteEditorActivity mNoteEditorActivity;

    private MarkdownParser mMarkdownParser;

    private Disposable mEditorEditTextDisposable;

    NoteEditorPresenterImpl() {
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
        Log.d(TAG, "Note editor is unsubscribed for preview");
    }

    @Override
    public void bindView(NoteEditorActivity noteEditorActivity) {
        this.mNoteEditorActivity = noteEditorActivity;
        Log.d(TAG, "Node editor is binded to its presenter.");
    }

    @Override
    public void unbindView() {
        mNoteEditorActivity = null;
        Log.d(TAG, "Node editor is unbinded to its presenter.");
    }
}
