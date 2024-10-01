package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.taskslist.impl

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.android.lvrn.lvrnproject.LavernaApplication
import com.github.android.lvrn.lvrnproject.R
import com.github.android.lvrn.lvrnproject.databinding.FragmentEntitiesListBinding
import com.github.android.lvrn.lvrnproject.view.activity.notedetail.NoteDetailActivity.Start.onStart
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl.TasksListAdapter
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.taskslist.TasksListFragment
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.taskslist.TasksListPresenter
import com.github.valhallalabs.laverna.activity.MainActivity
import com.github.valhallalabs.laverna.persistent.entity.Task
import com.orhanobut.logger.Logger
import javax.inject.Inject

/**
 * @author Vadim Boitsov <vadimboitsov1></vadimboitsov1>@gmail.com>
 */
class TasksListFragmentImpl : Fragment(), TasksListFragment {
    @JvmField
    @Inject
    var mTasksListPresenter: TasksListPresenter? = null

    private var mTasksRecyclerViewAdapter: TasksListAdapter? = null

    private var mSearchView: SearchView? = null

    private var mFragmentEntitiesListBinding: FragmentEntitiesListBinding? = null

    //    TODO: introduce in future milestones
    //    private MenuItem menuSync, menuSortBy, menuSettings, menuAbout;
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mFragmentEntitiesListBinding =
            FragmentEntitiesListBinding.inflate(inflater, container, false)
        LavernaApplication.getsAppComponent().inject(this)
        setUpToolbar()
        initRecyclerView()
        return mFragmentEntitiesListBinding!!.root
    }

    override fun onResume() {
        super.onResume()
        if (mTasksListPresenter != null) {
            mTasksListPresenter!!.bindView(this)
        }
    }

    override fun onPause() {
        super.onPause()
        mTasksListPresenter!!.unbindView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_entities_list, menu)

       val mMenuSearch = menu.findItem(R.id.item_action_search)

        //        TODO: introduce in future milestones
//        menuSync = menu.findItem(R.id.item_action_sync);
//        menuAbout = menu.findItem(R.id.item_about);
//        menuSortBy = menu.findItem(R.id.item_sort_by);
//        menuSettings = menu.findItem(R.id.item_settings);
        mSearchView = MenuItemCompat.getActionView(mMenuSearch) as SearchView

        mTasksListPresenter!!.subscribeSearchView(mMenuSearch)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mTasksListPresenter!!.disposePagination()
        mTasksListPresenter!!.disposeSearch()
    }

    override fun updateRecyclerView() {
        mTasksRecyclerViewAdapter!!.notifyDataSetChanged()
        Logger.d("Recycler view is updated")
    }

    override fun openRelatedNote(task: Task?) {
        onStart(requireActivity(), mTasksListPresenter!!.getNoteByTask(task)!!)
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
        mSearchView!!.queryHint = getString(R.string.fragment_all_notes_menu_search_query_hint)
        mSearchView!!.requestFocus()
        var bottomUnderline: Drawable? = null
        bottomUnderline = resources.getDrawable(R.drawable.search_view_bottom_underline, null)
        mSearchView!!.background = bottomUnderline
    }

    override fun switchToNormalMode() {
        (activity as MainActivity?)!!.floatingActionsMenu.visibility = View.VISIBLE
        //        TODO: introduce in future milestones
//        menuSync.setVisible(true);
//        menuAbout.setVisible(true);
//        menuSortBy.setVisible(true);
//        menuSettings.setVisible(true);
    }

    /**
     * A method which initializes recycler view with data
     */
    private fun initRecyclerView() {
        val recyclerViewAllEntities = mFragmentEntitiesListBinding!!.recyclerViewAllEntities
        recyclerViewAllEntities.setHasFixedSize(true)

        recyclerViewAllEntities.layoutManager = LinearLayoutManager(context)

        mTasksRecyclerViewAdapter = TasksListAdapter(this)
        mTasksListPresenter!!.setDataToAdapter(mTasksRecyclerViewAdapter!!)
        recyclerViewAllEntities.adapter = mTasksRecyclerViewAdapter

        mTasksListPresenter!!.subscribeRecyclerViewForPagination(recyclerViewAllEntities)
    }

    /**
     * A method which sets defined view of toolbar
     */
    private fun setUpToolbar() {
        setHasOptionsMenu(true)
        if ((activity as AppCompatActivity?)!!.supportActionBar != null) {
            (activity as AppCompatActivity?)!!.supportActionBar!!.show()
            (activity as AppCompatActivity?)!!.supportActionBar!!.title =
                TOOLBAR_TITLE
        }
    }

    companion object {
        const val TOOLBAR_TITLE: String = "All tasks"
    }
}
