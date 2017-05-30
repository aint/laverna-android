package com.github.android.lvrn.lvrnproject.view.activity.noteeditor;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface NoteEditorActivity {

    /**
     * A method which loads preview of a markdown text in web view.
     * @param html a html to load.
     */
    void loadPreview(String html);
}
