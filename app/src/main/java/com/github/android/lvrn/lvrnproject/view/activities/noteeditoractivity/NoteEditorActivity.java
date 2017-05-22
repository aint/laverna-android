package com.github.android.lvrn.lvrnproject.view.activities.noteeditoractivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TabHost;

import com.github.android.lvrn.lvrnproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;


//TODO: temporary implementation.
public class NoteEditorActivity extends AppCompatActivity {
    private static final String EDITOR_TAB_ID = "Editor";
    private static final String PREVIEW_TAB_ID = "Preview";

    private NoteEditorPresenterImpl mNoteEditorPresenter;

    @BindView(R.id.edit_text_editor) EditText mEditorEditText;

    @BindView(R.id.web_view_preview) WebView mPreviewWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        ButterKnife.bind(this);
        initTabs();
        mNoteEditorPresenter = new NoteEditorPresenterImpl(this);
        mPreviewWebView.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNoteEditorPresenter.subscribeEditorForPreview(mEditorEditText);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mNoteEditorPresenter.unsubscribeEditorForPreview();
    }

    private void initTabs() {
        TabHost tabHost = (TabHost) findViewById(R.id.tab_host);
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

    void loadPreview(String html) {
        mPreviewWebView.loadDataWithBaseURL(null, html, "text/html", "charset=UTF-8", null);
    }
}
