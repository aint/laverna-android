package com.github.android.lvrn.lvrnproject.view.activities.noteeditoractivity.impl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TabHost;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.view.activities.noteeditoractivity.NoteEditorActivity;
import com.github.android.lvrn.lvrnproject.view.activities.noteeditoractivity.NoteEditorPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NoteEditorActivityImpl extends AppCompatActivity implements NoteEditorActivity {
    private static final String EDITOR_TEXT_KEY = "editorText";
    public static final String CURRENT_TAB_KEY = "currentTab";
    private static final String EDITOR_TAB_ID = "Editor";
    private static final String PREVIEW_TAB_ID = "Preview";
    private static final String MIME_TYPE = "text/html";
    private static final String ENCODING = "charset=UTF-8";

    @BindView(R.id.edit_text_editor) EditText mEditorEditText;

    @BindView(R.id.web_view_preview) WebView mPreviewWebView;

    @BindView(R.id.tab_host) TabHost tabHost;

    private NoteEditorPresenter mNoteEditorPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        ButterKnife.bind(this);
        initTabs();
        restoreSavedInstance(savedInstanceState);
        mPreviewWebView.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mEditorEditText != null) {
            outState.putString(EDITOR_TEXT_KEY, mEditorEditText.getText().toString());
        }
        if (tabHost != null) {
            outState.putInt(CURRENT_TAB_KEY, tabHost.getCurrentTab());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mNoteEditorPresenter == null) {
            mNoteEditorPresenter = new NoteEditorPresenterImpl();
        }
        mNoteEditorPresenter.bindView(this);
        mNoteEditorPresenter.subscribeEditorForPreview(mEditorEditText);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mNoteEditorPresenter.unsubscribeEditorForPreview();
        mNoteEditorPresenter.unbindView();
    }

    @Override
    public void loadPreview(String html) {
        mPreviewWebView.loadDataWithBaseURL(null, html, MIME_TYPE, ENCODING, null);
    }

    /**
     * A method which restores activity state
     * @param savedInstanceState a bundle with restored data.
     */
    private void restoreSavedInstance(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(EDITOR_TEXT_KEY)) {
                mEditorEditText.setText(savedInstanceState.getString(EDITOR_TEXT_KEY));
            }
            if (savedInstanceState.containsKey(CURRENT_TAB_KEY)) {
                tabHost.setCurrentTab(savedInstanceState.getInt(CURRENT_TAB_KEY));
            }
        }
    }

    /**
     * A method which initializes editor and preview tabs.
     */
    private void initTabs() {
        tabHost.setup();
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(EDITOR_TAB_ID);
        tabSpec.setContent(R.id.tab_editor);
        tabSpec.setIndicator(EDITOR_TAB_ID);
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec(PREVIEW_TAB_ID);
        tabSpec.setContent(R.id.tab_preview);
        tabSpec.setIndicator(PREVIEW_TAB_ID);
        tabHost.addTab(tabSpec);
    }
}
