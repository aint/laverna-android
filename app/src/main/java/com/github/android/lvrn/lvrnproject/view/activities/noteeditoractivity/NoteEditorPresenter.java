package com.github.android.lvrn.lvrnproject.view.activities.noteeditoractivity;

import android.widget.EditText;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface NoteEditorPresenter {

    /**
     * A method which subscribes an edit text to webview for showing previews of a markdown editor.
     * @param editText an edit text to subscribe.
     */
    void subscribeEditorForPreview(EditText editText);

    /**
     * A method which unsubscribes and edit text to webiew for showing previews of a markdown editor.
     */
    void unsubscribeEditorForPreview();

    /**
     * A method which binds a view to a presenter.
     */
    void bindView(NoteEditorActivity noteEditorActivity);

    /**
     * A method which unbinds a view to a presenter.
     */
    void unbindView();
}
