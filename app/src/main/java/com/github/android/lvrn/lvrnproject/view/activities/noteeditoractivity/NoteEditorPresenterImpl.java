package com.github.android.lvrn.lvrnproject.view.activities.noteeditoractivity;

import com.github.android.lvrn.lvrnproject.view.util.MarkdownParser;
import com.github.android.lvrn.lvrnproject.view.util.impl.MarkdownParserImpl;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

class NoteEditorPresenterImpl implements NoteEditorPresenter {
    private static final String TAG = "NoteEditorPresenter";

    private NoteEditorActivity mNoteEditorActivity;

    private MarkdownParser mMarkdownParser;

    NoteEditorPresenterImpl(NoteEditorActivity mNoteEditorActivity) {
        this.mNoteEditorActivity = mNoteEditorActivity;
        mMarkdownParser = new MarkdownParserImpl();

    }

    String parseMarkdown(String markdownText) {
        return mMarkdownParser.getParsedHtml(markdownText);
    }



}
