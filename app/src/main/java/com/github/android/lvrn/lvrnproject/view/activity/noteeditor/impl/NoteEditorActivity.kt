package com.github.android.lvrn.lvrnproject.view.activity.noteeditor.impl

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TabHost
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.github.android.lvrn.lvrnproject.LavernaApplication
import com.github.android.lvrn.lvrnproject.R
import com.github.android.lvrn.lvrnproject.databinding.ActivityNoteEditorBinding
import com.github.android.lvrn.lvrnproject.service.core.NoteService
import com.github.android.lvrn.lvrnproject.view.activity.BaseActivity
import com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.impl.NotebookSelectionDialogFragmentImpl
import com.github.valhallalabs.laverna.activity.MainActivity
import com.github.valhallalabs.laverna.persistent.entity.Notebook
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

class NoteEditorActivity : BaseActivity() {

    companion object {
        private const val EDITOR_TEXT_KEY = "editorText"
        private const val TITLE_TEXT_KEY = "titleText"
        private const val CURRENT_TAB_KEY = "currentTab"
        private const val EDITOR_TAB_ID = "Editor"
        private const val PREVIEW_TAB_ID = "Preview"
        private const val MIME_TYPE = "text/html"
        private const val ENCODING = "charset=UTF-8"
    }

    @Inject
    lateinit var noteService: NoteService

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: NoteEditorViewModel

    private var mHtmlText = ""
    private lateinit var mNotebookMenu: MenuItem
    private lateinit var activityNoteEditorBinding: ActivityNoteEditorBinding
    private lateinit var editTextTitle: EditText
    private lateinit var editTextEditor: EditText
    private var tabHost: TabHost? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overrideStartAnimation()
        activityNoteEditorBinding = ActivityNoteEditorBinding.inflate(layoutInflater)
        setContentView(activityNoteEditorBinding.getRoot())
        LavernaApplication.getsAppComponent().inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[NoteEditorViewModel::class.java]

        activityNoteEditorBinding.webViewPreview.settings.javaScriptEnabled = true
        editTextTitle = activityNoteEditorBinding.editTextTitle
        editTextEditor = activityNoteEditorBinding.editTextEditor
        tabHost = activityNoteEditorBinding.tabHost
        setUpToolbar()
        initTabs()
        restoreSavedInstance(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(
            EDITOR_TEXT_KEY,
            editTextEditor.text.toString()
        )
        outState.putString(
            TITLE_TEXT_KEY,
            editTextTitle.text.toString()
        )

        if (tabHost != null) {
            outState.putInt(CURRENT_TAB_KEY, tabHost!!.currentTab)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            viewModel.previewFlow.collectLatest {
                loadPreview(it)
            }
        }
        editTextEditor.addTextChangedListener {
            viewModel.setContentText(it.toString())
        }
    }

    fun loadPreview(html: String) {
        mHtmlText = html
        activityNoteEditorBinding.webViewPreview.loadDataWithBaseURL(
            null,
            html,
            MIME_TYPE,
            ENCODING,
            null
        )
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_note_editor, menu)
        mNotebookMenu = menu.findItem(R.id.item_notebook)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.item_done) {
            viewModel.saveNewNote(
                editTextTitle.text.toString(),
                editTextEditor.text.toString(),
                mHtmlText
            )
            Snackbar.make(
                findViewById<View>(R.id.relative_layout_container_activity_note_editor),
                "Note " + editTextTitle.text.toString() + " has been created",
                Snackbar.LENGTH_LONG
            ).show()
        } else if (itemId == R.id.item_notebook) {
            openNotebooksSelectionDialog()
            return true
        }
        return false
    }

    fun setNoteNotebooks(notebook: Notebook?) {
        //TODO: send mNotebook id to its presenter, and name of mNotebook to UI
        if (notebook != null) {
            viewModel.setNotebook(notebook)
        } else {
            viewModel.setNotebook(null)
        }
        viewModel.subscribeMenuForNotebook(mNotebookMenu)
    }


    private fun openNotebooksSelectionDialog() {
        val fragmentTransaction = supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
        val notebookSelectionDialogFragment = NotebookSelectionDialogFragmentImpl.newInstance(
            viewModel.getNotebook()
        )
        notebookSelectionDialogFragment.show(fragmentTransaction, "notebook_selection_tag")
    }

    private fun setUpToolbar() {
        setSupportActionBar(activityNoteEditorBinding.toolbarMain)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        activityNoteEditorBinding.toolbarMain.setNavigationOnClickListener { v ->
            this.startActivity(
                Intent(this, MainActivity::class.java)
            )
            this.finish()
            overrideFinishAnimation()
        }
    }

    /**
     * A method which restores activity state
     *
     * @param savedInstanceState a bundle with restored data.
     */
    private fun restoreSavedInstance(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(EDITOR_TEXT_KEY)) {
                editTextEditor.setText(savedInstanceState.getString(EDITOR_TEXT_KEY))
            }
            if (savedInstanceState.containsKey(TITLE_TEXT_KEY)) {
                editTextTitle.setText(savedInstanceState.getString(TITLE_TEXT_KEY))
            }
            if (savedInstanceState.containsKey(CURRENT_TAB_KEY)) {
                tabHost!!.currentTab =
                    savedInstanceState.getInt(CURRENT_TAB_KEY)
            }
        }
    }

    /**
     * A method which initializes editor and preview tabs.
     */
    private fun initTabs() {
        tabHost!!.setup()

        var tabSpec = tabHost!!.newTabSpec(EDITOR_TAB_ID)
        tabSpec.setContent(R.id.tab_editor)
        tabSpec.setIndicator(EDITOR_TAB_ID)
        tabHost!!.addTab(tabSpec)

        tabSpec = tabHost!!.newTabSpec(PREVIEW_TAB_ID)
        tabSpec.setContent(R.id.tab_preview)
        tabSpec.setIndicator(PREVIEW_TAB_ID)
        tabHost!!.addTab(tabSpec)

        tabHost!!.setOnTabChangedListener { tabId: String? -> hideSoftKeyboard() }
    }

    /**
     * A method which hides a soft keyboard when tabs are switched.
     */
    private fun hideSoftKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}