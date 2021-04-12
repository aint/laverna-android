package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.impl

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.android.lvrn.lvrnproject.LavernaApplication
import com.github.android.lvrn.lvrnproject.R
import com.github.android.lvrn.lvrnproject.databinding.FragmentEntitiesListBinding
import com.github.android.lvrn.lvrnproject.view.activity.notedetail.NoteDetailActivity.Start.onStart
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl.NotesListAdapter
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.NotesListFragment
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.NotesListPresenter
import com.github.valhallalabs.laverna.activity.MainActivity
import com.github.valhallalabs.laverna.persistent.entity.Note
import com.orhanobut.logger.Logger
import javax.inject.Inject

class NotesListFragmentImpl : Fragment(), NotesListFragment {

    @set:Inject
    var mNotesListPresenter: NotesListPresenter? = null

    val TOOLBAR_TITLE = "All Notes"

    private var mNotesRecyclerViewAdapter: NotesListAdapter? = null

    private var mSearchView: SearchView? = null

    private var mMenuSearch: MenuItem? = null

    private lateinit var mFragmentEntitiesListBinding: FragmentEntitiesListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mFragmentEntitiesListBinding = FragmentEntitiesListBinding.inflate(inflater,container,false)
        LavernaApplication.getsAppComponent().inject(this)
        setUpToolbar()
        initRecyclerView()
        return mFragmentEntitiesListBinding.root
    }

    override fun onResume() {
        super.onResume()
        if (mNotesListPresenter != null) {
            mNotesListPresenter!!.bindView(this)
        }
    }

    override fun onPause() {
        super.onPause()
        mNotesListPresenter!!.unbindView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_entities_list, menu)
        mMenuSearch = menu.findItem(R.id.item_action_search)
        //        TODO: introduce in future milestones
//        menuSync = menu.findItem(R.id.item_action_sync);
//        menuAbout = menu.findItem(R.id.item_about);
//        menuSortBy = menu.findItem(R.id.item_sort_by);
//        menuSettings = menu.findItem(R.id.item_settings);
        mSearchView = MenuItemCompat.getActionView(mMenuSearch) as SearchView
        mNotesListPresenter!!.subscribeSearchView(mMenuSearch)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mNotesListPresenter!!.disposePagination()
        mNotesListPresenter!!.disposeSearch()
    }

    override fun showSelectedNote(note: Note) {
        onStart(requireActivity(), note)
    }

    override fun updateRecyclerView() {
        mNotesRecyclerViewAdapter!!.notifyDataSetChanged()
        Logger.d("Recycler view is updated")
    }

    override fun switchToNormalMode() {
        (activity as MainActivity?)!!.floatingActionsMenu.visibility = View.VISIBLE
//        TODO: introduce in future milestones
//        menuSync.setVisible(true);
//        menuAbout.setVisible(true);
//        menuSortBy.setVisible(true);
//        menuSettings.setVisible(true);
    }

    override fun getSearchQuery(): String {
        return mSearchView!!.query.toString()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun switchToSearchMode() {
        (activity as MainActivity?)!!.floatingActionsMenu.collapse()
        (activity as MainActivity?)!!.floatingActionsMenu.visibility = View.GONE
//        TODO: introduce in future milestones
//        menuSync.setVisible(false);
//        menuAbout.setVisible(false);
//        menuSortBy.setVisible(false);
//        menuSettings.setVisible(false);
        //        TODO: introduce in future milestones
//        menuSync.setVisible(false);
//        menuAbout.setVisible(false);
//        menuSortBy.setVisible(false);
//        menuSettings.setVisible(false);
        mSearchView!!.queryHint = getString(R.string.fragment_all_notes_menu_search_query_hint)
        mSearchView!!.requestFocus()
        var bottomUnderline: Drawable? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bottomUnderline = resources.getDrawable(R.drawable.search_view_bottom_underline, null)
        }
        mSearchView!!.background = bottomUnderline
    }

    /**
     * A method which initializes recycler view with data
     */
    private fun initRecyclerView() {
        val recyclerViewAllEntities = mFragmentEntitiesListBinding!!.recyclerViewAllEntities
        recyclerViewAllEntities.setHasFixedSize(true)
        recyclerViewAllEntities.layoutManager = LinearLayoutManager(context)
        mNotesRecyclerViewAdapter = NotesListAdapter(this, mNotesListPresenter!!)
        mNotesListPresenter!!.setDataToAdapter(mNotesRecyclerViewAdapter)
        recyclerViewAllEntities.adapter = mNotesRecyclerViewAdapter
        mNotesListPresenter!!.subscribeRecyclerViewForPagination(recyclerViewAllEntities)
    }

    /**
     * A method which sets defined view of toolbar
     */
    private fun setUpToolbar() {
        setHasOptionsMenu(true)
        if ((activity as AppCompatActivity?)!!.supportActionBar != null) {
            (activity as AppCompatActivity?)!!.supportActionBar!!.show()
            (activity as AppCompatActivity?)!!.supportActionBar?.title = TOOLBAR_TITLE
        }
    }
}