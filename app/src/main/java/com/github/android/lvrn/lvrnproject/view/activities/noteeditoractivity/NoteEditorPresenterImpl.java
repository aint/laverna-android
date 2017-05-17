package com.github.android.lvrn.lvrnproject.view.activities.noteeditoractivity;

import android.widget.EditText;

import com.github.android.lvrn.lvrnproject.view.util.MarkdownParser;
import com.github.android.lvrn.lvrnproject.view.util.impl.MarkdownParserImpl;
import com.jakewharton.rxbinding2.widget.RxTextView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

class NoteEditorPresenterImpl {
    private static final String TAG = "NoteEditorPresenter";

    private NoteEditorActivity mNoteEditorActivity;

    private MarkdownParser mMarkdownParser;

    private Disposable mEditorEditTextDisposable;

    NoteEditorPresenterImpl(NoteEditorActivity mNoteEditorActivity) {
        this.mNoteEditorActivity = mNoteEditorActivity;
        mMarkdownParser = new MarkdownParserImpl();
    }

    void subscribeEditorForPreview(EditText editText) {
        mEditorEditTextDisposable = RxTextView.textChanges(editText)
                .map(text -> mMarkdownParser.getParsedHtml(text.toString()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(html -> mNoteEditorActivity.loadPreview(html.toString()));
    }

    void unsubscribeEditorForPreview() {
        if (mEditorEditTextDisposable == null || mEditorEditTextDisposable.isDisposed()) {
            return;
        }
        mEditorEditTextDisposable.dispose();
    }
}
