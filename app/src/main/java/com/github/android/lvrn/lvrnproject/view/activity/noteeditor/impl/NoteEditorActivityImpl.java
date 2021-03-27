package com.github.android.lvrn.lvrnproject.view.activity.noteeditor.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TabHost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.databinding.ActivityNoteEditorBinding;
import com.github.android.lvrn.lvrnproject.service.core.NoteService;
import com.github.android.lvrn.lvrnproject.view.activity.noteeditor.NoteEditorActivity;
import com.github.android.lvrn.lvrnproject.view.activity.noteeditor.NoteEditorPresenter;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.impl.NotebookSelectionDialogFragmentImpl;
import com.github.valhallalabs.laverna.activity.MainActivity;
import com.github.valhallalabs.laverna.persistent.entity.Notebook;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NoteEditorActivityImpl extends AppCompatActivity implements NoteEditorActivity {

    @Inject
    NoteService noteService;

    private static final String EDITOR_TEXT_KEY = "editorText";
    private static final String TITLE_TEXT_KEY = "titleText";
    private static final String CURRENT_TAB_KEY = "currentTab";
    private static final String EDITOR_TAB_ID = "Editor";
    private static final String PREVIEW_TAB_ID = "Preview";
    private static final String MIME_TYPE = "text/html";
    private static final String ENCODING = "charset=UTF-8";

    private NoteEditorPresenter mNoteEditorPresenter;
    private String mHtmlText = "";
    private MenuItem mNotebookMenu;
    private ActivityNoteEditorBinding activityNoteEditorBinding;
    private EditText editTextTitle;
    private EditText editTextEditor;
    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityNoteEditorBinding = ActivityNoteEditorBinding.inflate(getLayoutInflater());
        setContentView(activityNoteEditorBinding.getRoot());
        LavernaApplication.getsAppComponent().inject(this);
        activityNoteEditorBinding.webViewPreview.getSettings().setJavaScriptEnabled(true);
        editTextTitle = activityNoteEditorBinding.editTextTitle;
        editTextEditor = activityNoteEditorBinding.editTextEditor;
        tabHost = activityNoteEditorBinding.tabHost;
        setUpToolbar();
        initTabs();
        restoreSavedInstance(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        if (editTextEditor != null) {
            outState.putString(EDITOR_TEXT_KEY, editTextEditor.getText().toString());
        }

        if (editTextTitle != null) {
            outState.putString(TITLE_TEXT_KEY, editTextTitle.getText().toString());
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
            mNoteEditorPresenter = new NoteEditorPresenterImpl(noteService);
        }
        mNoteEditorPresenter.bindView(this);
        mNoteEditorPresenter.subscribeEditorForPreview(editTextEditor);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mNoteEditorPresenter.unsubscribeEditorForPreview();
        mNoteEditorPresenter.unbindView();
    }

    @Override
    public void loadPreview(String html) {
        mHtmlText = html;
        activityNoteEditorBinding.webViewPreview.loadDataWithBaseURL(null, html, MIME_TYPE, ENCODING, null);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_note_editor, menu);
        mNotebookMenu = menu.findItem(R.id.item_notebook);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.item_done) {
            mNoteEditorPresenter.saveNewNote(
                    editTextTitle.getText().toString(),
                    editTextEditor.getText().toString(),
                    mHtmlText);
            Snackbar.make(findViewById(R.id.relative_layout_container_activity_note_editor), "Note " + editTextTitle.getText().toString() + " has been created", Snackbar.LENGTH_LONG).show();
        } else if (itemId == R.id.item_notebook) {
            openNotebooksSelectionDialog();
            return true;
        }
        return false;
    }

    public void setNoteNotebooks(Notebook notebook) {
        //TODO: send mNotebook id to its presenter, and name of mNotebook to UI
        if (notebook != null) {
            mNoteEditorPresenter.setNotebook(notebook);
        } else {
            mNoteEditorPresenter.setNotebook(null);
        }
        mNoteEditorPresenter.subscribeMenuForNotebook(mNotebookMenu);
    }


    private void openNotebooksSelectionDialog() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null);
        NotebookSelectionDialogFragmentImpl notebookSelectionDialogFragment = NotebookSelectionDialogFragmentImpl.newInstance(mNoteEditorPresenter.getNotebook());
        notebookSelectionDialogFragment.show(fragmentTransaction, "notebook_selection_tag");
    }

    private void setUpToolbar() {
        setSupportActionBar(activityNoteEditorBinding.toolbarMain);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        activityNoteEditorBinding.toolbarMain.setNavigationOnClickListener(v -> {
            this.startActivity(new Intent(this, MainActivity.class));
            this.finish();
        });
    }

    /**
     * A method which restores activity state
     *
     * @param savedInstanceState a bundle with restored data.
     */
    private void restoreSavedInstance(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(EDITOR_TEXT_KEY)) {
                editTextEditor.setText(savedInstanceState.getString(EDITOR_TEXT_KEY));
            }
            if (savedInstanceState.containsKey(TITLE_TEXT_KEY)) {
                editTextTitle.setText(savedInstanceState.getString(TITLE_TEXT_KEY));
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

        tabHost.setOnTabChangedListener(tabId -> hideSoftKeyboard());
    }

    /**
     * A method which hides a soft keyboard when tabs are switched.
     */
    private void hideSoftKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
