package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.impl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.android.lvrn.lvrnproject.LavernaApplication
import com.github.android.lvrn.lvrnproject.R
import com.github.android.lvrn.lvrnproject.databinding.FragmentNotebookContentBinding
import com.github.android.lvrn.lvrnproject.view.activity.notedetail.NoteDetailActivity.Start.onStart
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl.ChildNotebooksAdapter
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl.ChildNotesAdapter
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.NotebookChildrenFragment
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.NotebookChildrenPresenter
import com.github.android.lvrn.lvrnproject.view.util.consts.BUNDLE_NOTEBOOK_OBJECT_KEY
import com.github.android.lvrn.lvrnproject.view.util.consts.TAG_NOTEBOOK_CHILDREN_FRAGMENT
import com.github.valhallalabs.laverna.persistent.entity.Note
import com.github.valhallalabs.laverna.persistent.entity.Notebook
import com.orhanobut.logger.Logger
import javax.inject.Inject

/**
 * @author Andrii Bei <psihey1></psihey1>@gmail.com>
 */
class NotebookChildrenFragmentImpl : Fragment(), NotebookChildrenFragment,
    ChildNotesAdapter.NotebookChildrenListener, ChildNotebooksAdapter.ChildNotebooksAdapterListener {

    @Inject
    lateinit var mNotebookChildrenPresenter: NotebookChildrenPresenter

    private var mChildNotesAdapter: ChildNotesAdapter? = null
    private var mChildNotebooksAdapter: ChildNotebooksAdapter? = null
    private var mFragmentNotebookContentBinding: FragmentNotebookContentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mFragmentNotebookContentBinding =
            FragmentNotebookContentBinding.inflate(inflater, container, false)
        LavernaApplication.getsAppComponent().inject(this)
        initListPresenters()
        initRecyclerViews()
        return mFragmentNotebookContentBinding!!.root
    }

    override fun onResume() {
        super.onResume()
        mNotebookChildrenPresenter.getNotebooksListPresenter().bindView(this)
        mNotebookChildrenPresenter.getNotesListPresenter().bindView(this)
    }

    override fun onPause() {
        super.onPause()
        mNotebookChildrenPresenter.getNotebooksListPresenter().unbindView()
        mNotebookChildrenPresenter.getNotesListPresenter().unbindView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mNotebookChildrenPresenter.getNotebooksListPresenter().disposePagination()
        mNotebookChildrenPresenter.getNotesListPresenter().disposePagination()
    }

    private fun initListPresenters() {
        val bundle = arguments
        if (bundle != null) {
            mNotebookChildrenPresenter.initializeListsPresenters(
                bundle.getParcelable(
                    BUNDLE_NOTEBOOK_OBJECT_KEY
                )
            )
        }
    }

    /**
     * A method which initializes recycler view
     */
    private fun initRecyclerViews() {
        initNotesRecyclerView()
        initNotebooksRecyclerView()
    }

    private fun initNotesRecyclerView() {
        val recyclerViewNotes = mFragmentNotebookContentBinding!!.recyclerViewNotes
        recyclerViewNotes.setHasFixedSize(true)

        recyclerViewNotes.layoutManager = LinearLayoutManager(context)

        mChildNotesAdapter = ChildNotesAdapter(this)
        mNotebookChildrenPresenter.getNotesListPresenter().setDataToAdapter(mChildNotesAdapter!!)
        recyclerViewNotes.adapter = mChildNotesAdapter

        mNotebookChildrenPresenter.getNotesListPresenter().subscribeRecyclerViewForPagination(
            recyclerViewNotes
        )
    }

    private fun initNotebooksRecyclerView() {
        val recyclerViewNotebooks = mFragmentNotebookContentBinding!!.recyclerViewNotebooks
        recyclerViewNotebooks.setHasFixedSize(true)

        recyclerViewNotebooks.layoutManager = LinearLayoutManager(context)

        mChildNotebooksAdapter = ChildNotebooksAdapter(this)
        mNotebookChildrenPresenter.getNotebooksListPresenter()
            .setDataToAdapter(mChildNotebooksAdapter!!)
        recyclerViewNotebooks.adapter = mChildNotebooksAdapter

        mNotebookChildrenPresenter.getNotebooksListPresenter().subscribeRecyclerViewForPagination(
            recyclerViewNotebooks
        )
    }

    override fun updateRecyclerView() {
        mChildNotesAdapter!!.notifyDataSetChanged()
        mChildNotebooksAdapter!!.notifyDataSetChanged()
        Logger.d("Recycler view is updated")
    }

    override fun showSelectedNote(note: Note) {
        onStart(requireActivity(), note)
    }

    override fun openNotebook(notebook: Notebook) {
        val notebookChildrenFragment = NotebookChildrenFragmentImpl()

        val bundle = Bundle()
        bundle.putParcelable(BUNDLE_NOTEBOOK_OBJECT_KEY, notebook)
        notebookChildrenFragment.arguments = bundle

        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.constraint_container,
                notebookChildrenFragment,
                TAG_NOTEBOOK_CHILDREN_FRAGMENT
            )
            .addToBackStack(null)
            .commit()
    }
}
