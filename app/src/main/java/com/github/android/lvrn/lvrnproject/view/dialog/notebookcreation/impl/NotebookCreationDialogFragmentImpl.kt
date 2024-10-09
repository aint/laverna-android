package com.github.android.lvrn.lvrnproject.view.dialog.notebookcreation.impl

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.android.lvrn.lvrnproject.LavernaApplication
import com.github.android.lvrn.lvrnproject.R
import com.github.android.lvrn.lvrnproject.databinding.DialogFragmentNotebookCreateBinding
import com.github.android.lvrn.lvrnproject.service.core.NotebookService
import com.github.android.lvrn.lvrnproject.view.activity.noteeditor.impl.NoteEditorActivity
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl.NotebookCreationAdapter
import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreation.NotebookCreationDialogFragment
import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreation.NotebookCreationPresenter
import com.github.android.lvrn.lvrnproject.view.util.consts.BUNDLE_DIALOG_NOTEBOOK_CREATE_KEY
import com.github.android.lvrn.lvrnproject.view.util.consts.DIALOG_OPEN_FROM_MAIN_ACTIVITY
import com.github.valhallalabs.laverna.persistent.entity.Notebook
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

/**
 * @author Andrii Bei <psihey1></psihey1>@gmail.com>
 */
class NotebookCreationDialogFragmentImpl : DialogFragment(), NotebookCreationDialogFragment {

    @Inject
    lateinit var mNotebookService: NotebookService

    private var mNotebookCreationPresenter: NotebookCreationPresenter? = null

    private var mNotebookAdapter: NotebookCreationAdapter? = null

    private var previousFragmentName: String? = null

    private var notebook: Notebook? = null

    private var dialogFragmentNotebookCreateBinding: DialogFragmentNotebookCreateBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialogFragmentNotebookCreateBinding =
            DialogFragmentNotebookCreateBinding.inflate(inflater, container, false)
        LavernaApplication.getsAppComponent().inject(this)
        mNotebookCreationPresenter = NotebookCreationPresenterImpl(mNotebookService)
        initRecyclerView()
        previousFragmentName = requireArguments().getString(BUNDLE_DIALOG_NOTEBOOK_CREATE_KEY)

        return dialogFragmentNotebookCreateBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialogFragmentNotebookCreateBinding!!.btnCreateNotebookOk.setOnClickListener { view1: View? -> createNotebook() }
        dialogFragmentNotebookCreateBinding!!.btnCreateNotebookCancel.setOnClickListener { view1: View? -> cancelDialog() }
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
        mNotebookCreationPresenter!!.bindView(this)
    }

    override fun onPause() {
        super.onPause()
        mNotebookCreationPresenter!!.unbindView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mNotebookCreationPresenter!!.disposePaginationAndSearch()
    }

    override fun updateRecyclerView() {
        mNotebookAdapter!!.notifyDataSetChanged()
    }

    override fun getNotebook(notebook: Notebook) {
        this.notebook = notebook
    }

    fun createNotebook() {
        val nameNotebook =
            dialogFragmentNotebookCreateBinding!!.editTextNotebookName.text.toString()
        if (TextUtils.equals(previousFragmentName, DIALOG_OPEN_FROM_MAIN_ACTIVITY)) {
            if (mNotebookCreationPresenter!!.createNotebook(nameNotebook)) {
                Snackbar.make(
                    requireActivity().findViewById(R.id.coordinator_layout_main_activity),
                    "Notebook $nameNotebook has created ",
                    Snackbar.LENGTH_LONG
                ).show()
                requireActivity().onBackPressed()
                return
            }
            Snackbar.make(
                requireActivity().findViewById(R.id.coordinator_layout_main_activity),
                "Notebook $nameNotebook hasn't created ",
                Snackbar.LENGTH_LONG
            ).show()
            return
        }
        if (mNotebookCreationPresenter!!.createNotebook(nameNotebook)) {
            (activity as NoteEditorActivity?)!!.setNoteNotebooks(notebook)
            Snackbar.make(
                requireActivity().findViewById(R.id.relative_layout_container_activity_note_editor),
                "Notebook $nameNotebook has created ",
                Snackbar.LENGTH_LONG
            ).show()
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    fun cancelDialog() {
        requireActivity().onBackPressed()
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        val recyclerViewNotebookCreate =
            dialogFragmentNotebookCreateBinding!!.recyclerViewNotebookCreate
        recyclerViewNotebookCreate.layoutManager = layoutManager

        mNotebookAdapter = NotebookCreationAdapter(mNotebookCreationPresenter!!)
        mNotebookCreationPresenter!!.setDataToAdapter(mNotebookAdapter!!)

        recyclerViewNotebookCreate.adapter = mNotebookAdapter
        mNotebookCreationPresenter!!.subscribeRecyclerViewForPagination(recyclerViewNotebookCreate)
    }


    companion object {
        @JvmStatic
        fun newInstance(previousFragment: String?): NotebookCreationDialogFragmentImpl {
            val notebookCreateDialogFragment = NotebookCreationDialogFragmentImpl()
            val bundle = Bundle()
            bundle.putString(BUNDLE_DIALOG_NOTEBOOK_CREATE_KEY, previousFragment)
            notebookCreateDialogFragment.arguments = bundle
            return notebookCreateDialogFragment
        }
    }
}
