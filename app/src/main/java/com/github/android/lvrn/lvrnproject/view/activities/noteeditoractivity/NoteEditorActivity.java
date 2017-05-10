package com.github.android.lvrn.lvrnproject.view.activities.noteeditoractivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TabHost;

import com.github.android.lvrn.lvrnproject.R;



//TODO: temporary implementation.
public class NoteEditorActivity extends AppCompatActivity {
    private static final String EDITOR_TAB_ID = "Editor" , PREVIEW_TAB_ID = "Preview";
    private EditText mEditorEditText;
//    private MarkdownView mMarkdownViewPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        mEditorEditText = (EditText) findViewById(R.id.edit_text_editor);
//        mMarkdownViewPreview = (MarkdownView) findViewById(R.id.markdown_view_preview);
        initTabs();
    }

    private void initTabs() {
        TabHost tabHost = (TabHost) findViewById(R.id.tab_host);
        tabHost.setup();
        tabHost.setOnTabChangedListener(this::showPreview);
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(EDITOR_TAB_ID);
        tabSpec.setContent(R.id.tab_editor);
        tabSpec.setIndicator(EDITOR_TAB_ID);
        tabHost.addTab(tabSpec);
        tabSpec = tabHost.newTabSpec(PREVIEW_TAB_ID);
        tabSpec.setContent(R.id.tab_preview);
        tabSpec.setIndicator(PREVIEW_TAB_ID);
        tabHost.addTab(tabSpec);
    }

    private void showPreview(String tabId) {
        if (PREVIEW_TAB_ID.equals(tabId)) {
            CharSequence text = mEditorEditText.getText();
//            mMarkdownViewPreview.loadMarkdown(text.toString());
        }
    }
}
