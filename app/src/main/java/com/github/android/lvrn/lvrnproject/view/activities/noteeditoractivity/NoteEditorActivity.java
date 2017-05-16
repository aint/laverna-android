package com.github.android.lvrn.lvrnproject.view.activities.noteeditoractivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TabHost;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.view.util.MarkdownParser;
import com.github.android.lvrn.lvrnproject.view.util.impl.MarkdownParserImpl;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


//TODO: temporary implementation.
public class NoteEditorActivity extends AppCompatActivity {
    private static final String EDITOR_TAB_ID = "Editor" , PREVIEW_TAB_ID = "Preview";

    private NoteEditorPresenter mNoteEditorPresenter;

    @BindView(R.id.edit_text_editor) EditText mEditorEditText;

    @BindView(R.id.web_view_preview) WebView mPreviewWebView;

    private MarkdownParser mMarkdownParser;


    private Disposable mEditorEditTextDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        ButterKnife.bind(this);
        initTabs();

        mMarkdownParser = new MarkdownParserImpl();



        mNoteEditorPresenter = new NoteEditorPresenterImpl(this);

        mEditorEditText = (EditText) findViewById(R.id.edit_text_editor);
        mEditorEditTextDisposable = RxTextView.textChanges(mEditorEditText)
                .debounce(100, TimeUnit.MILLISECONDS)
                .map(text -> mMarkdownParser.getParsedHtml(text.toString()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(html -> mPreviewWebView.loadData(html, "text/html", "charset=UTF-8"));
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




    @Override
    protected void onStop() {
        super.onStop();
        if(!mEditorEditTextDisposable.isDisposed()) {
            mEditorEditTextDisposable.dispose();
        }
    }
}
