package com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.impl

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.android.lvrn.lvrnproject.LavernaApplication
import com.github.android.lvrn.lvrnproject.databinding.DialogFragmentNotebookSelectionBinding
import com.github.android.lvrn.lvrnproject.service.core.NotebookService
import com.github.android.lvrn.lvrnproject.view.activity.noteeditor.impl.NoteEditorActivity
import com.github.android.lvrn.lvrnproject.view.adapter.NotebookSelectionAdapter
import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreation.impl.NotebookCreationDialogFragmentImpl
import com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.NotebookSelectionDialogFragment
import com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.NotebookSelectionPresenter
import com.github.android.lvrn.lvrnproject.view.util.consts.BUNDLE_DIALOG_NOTEBOOK_SELECTION_KEY
import com.github.android.lvrn.lvrnproject.view.util.consts.DIALOG_OPEN_FROM_NOTEBOOK_SELECTION_DIALOG_FRAGMENT
import com.github.android.lvrn.lvrnproject.view.util.consts.TAG_NOTEBOOK_CREATE_FRAGMENT
import com.github.valhallalabs.laverna.persistent.entity.Notebook
import com.orhanobut.logger.Logger
import javax.inject.Inject

/**
 * @author Vadim Boitsov <vadimboitsov1></vadimboitsov1>@gmail.com>
 */
class NotebookSelectionDialogFragmentImpl : DialogFragment(), NotebookSelectionDialogFragment {

    @Inject
    lateinit var mNotebookService: NotebookService

    private var mRecyclerViewState: Parcelable? = null

    private var mNotebookSelectionPresenter: NotebookSelectionPresenter? = null

    private var mNotebooksRecyclerViewAdapter: NotebookSelectionAdapter? = null

    private var mLinearLayoutManager: LinearLayoutManager? = null

    private var mNotebook: Notebook? = null

    private var mSelectedNotebook: Notebook? = null

    private var dialogFragmentNotebookSelectionBinding: DialogFragmentNotebookSelectionBinding? =
        null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light_Dialog);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialogFragmentNotebookSelectionBinding =
            DialogFragmentNotebookSelectionBinding.inflate(inflater, container, false)
        LavernaApplication.getsAppComponent().inject(this)

        mNotebookSelectionPresenter = NotebookSelectionPresenterImpl(mNotebookService)

        mNotebook = requireArguments().getParcelable<Notebook>(BUNDLE_DIALOG_NOTEBOOK_SELECTION_KEY)

        initRecyclerView()

        initTextView()


        //        getDialog().setTitle(DIALOG_TITLE);
        return dialogFragmentNotebookSelectionBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialogFragmentNotebookSelectionBinding!!.btnCreateNotebookDialogFragmentSelection.setOnClickListener { view1: View? -> createNotebook() }
        dialogFragmentNotebookSelectionBinding!!.btnOkDialogFragmentSelection.setOnClickListener { view1: View? -> acceptChanges() }
        dialogFragmentNotebookSelectionBinding!!.btnResetNotebookDialogFragmentSelection.setOnClickListener { view1: View? -> resetChanges() }
        dialogFragmentNotebookSelectionBinding!!.btnCancelDialogFragmentSelection.setOnClickListener { view1: View? -> cancelDialog() }
    }

    fun createNotebook() {
        val fragmentTransaction = requireActivity().supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
        val dialogFragment: DialogFragment = NotebookCreationDialogFragmentImpl.newInstance(
            DIALOG_OPEN_FROM_NOTEBOOK_SELECTION_DIALOG_FRAGMENT
        )
        dialogFragment.show(fragmentTransaction, TAG_NOTEBOOK_CREATE_FRAGMENT)
    }

    fun acceptChanges() {
        (activity as NoteEditorActivity?)!!.setNoteNotebooks(mSelectedNotebook)
        dialog!!.dismiss()
    }

    fun resetChanges() {
        (activity as NoteEditorActivity?)!!.setNoteNotebooks(null)
        dialog!!.dismiss()
    }

    fun cancelDialog() {
        dialog!!.dismiss()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mRecyclerViewState = mLinearLayoutManager!!.onSaveInstanceState()
        outState.putParcelable(RECYCLER_VIEW_STATE, mRecyclerViewState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            mRecyclerViewState = savedInstanceState.getParcelable(RECYCLER_VIEW_STATE)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog!!.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    override fun onResume() {
        super.onResume()
        mNotebookSelectionPresenter!!.bindView(this)
        if (mRecyclerViewState != null) {
            mLinearLayoutManager!!.onRestoreInstanceState(mRecyclerViewState)
        }
    }

    override fun updateRecyclerView() {
        mNotebooksRecyclerViewAdapter!!.notifyDataSetChanged()
        Logger.d("Recycler view is updated")
    }

    override fun setSelectedNotebook(notebook: Notebook?) {
        mSelectedNotebook = notebook
    }


    override fun onPause() {
        super.onPause()
        mNotebookSelectionPresenter!!.unbindView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mNotebookSelectionPresenter!!.disposePagination()
    }

    /**
     * A method which makes set up of a recycler view with notebooks.
     */
    private fun initRecyclerView() {
        val recyclerViewNotebooksDialogFragmentSelection =
            dialogFragmentNotebookSelectionBinding!!.recyclerViewNotebooksDialogFragmentSelection
        recyclerViewNotebooksDialogFragmentSelection.setHasFixedSize(true)

        mLinearLayoutManager = LinearLayoutManager(context)
        recyclerViewNotebooksDialogFragmentSelection.layoutManager = mLinearLayoutManager

        mNotebooksRecyclerViewAdapter = NotebookSelectionAdapter(
            this, mNotebookSelectionPresenter!!.notebooksForAdapter
        )

        recyclerViewNotebooksDialogFragmentSelection.adapter = mNotebooksRecyclerViewAdapter

        mNotebookSelectionPresenter!!.subscribeRecyclerViewForPagination(
            recyclerViewNotebooksDialogFragmentSelection
        )
    }

    private fun initTextView() {
        if (mNotebook == null) {
        } else dialogFragmentNotebookSelectionBinding!!.tvCreateNotebookDialogDialogFragmentSelection.text =
            mNotebook!!.name
    }

    companion object {
        const val RECYCLER_VIEW_STATE: String = "recycler_view_state"

        fun newInstance(notebook: Notebook?): NotebookSelectionDialogFragmentImpl {
            val notebookSelectionDialogFragment = NotebookSelectionDialogFragmentImpl()
            val bundle = Bundle()
            bundle.putParcelable(BUNDLE_DIALOG_NOTEBOOK_SELECTION_KEY, notebook)

            notebookSelectionDialogFragment.arguments = bundle
            return notebookSelectionDialogFragment
        }
    }
}
