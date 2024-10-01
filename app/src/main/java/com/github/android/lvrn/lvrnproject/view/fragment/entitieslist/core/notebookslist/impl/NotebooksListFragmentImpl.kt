package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist.impl

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
import androidx.recyclerview.widget.RecyclerView
import com.github.android.lvrn.lvrnproject.LavernaApplication
import com.github.android.lvrn.lvrnproject.R
import com.github.android.lvrn.lvrnproject.databinding.FragmentEntitiesListBinding
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl.NotebooksListAdapter
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.impl.NotebookChildrenFragmentImpl
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist.NotebooksListFragment
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist.NotebooksListPresenter
import com.github.android.lvrn.lvrnproject.view.util.consts.BUNDLE_NOTEBOOK_OBJECT_KEY
import com.github.android.lvrn.lvrnproject.view.util.consts.TAG_NOTEBOOK_CHILDREN_FRAGMENT
import com.github.valhallalabs.laverna.activity.MainActivity
import com.github.valhallalabs.laverna.persistent.entity.Notebook
import com.orhanobut.logger.Logger
import javax.inject.Inject

/**
 * @author Vadim Boitsov <vadimboitsov1></vadimboitsov1>@gmail.com>
 */
class NotebooksListFragmentImpl : Fragment(), NotebooksListFragment {


    @Inject
    lateinit var mNotebooksListPresenter: NotebooksListPresenter

    private var mNotebooksRecyclerViewAdapter: NotebooksListAdapter? = null

    private var mSearchView: SearchView? = null

    private var mMenuSearch: MenuItem? = null

    private var mFragmentEntitiesListBinding: FragmentEntitiesListBinding? = null
    private var mRecyclerViewAllEntities: RecyclerView? = null

    //    TODO: introduce in future milestones
    //    private MenuItem menuSync, menuSortBy, menuSettings, menuAbout;
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mFragmentEntitiesListBinding =
            FragmentEntitiesListBinding.inflate(inflater, container, false)
        LavernaApplication.getsAppComponent().inject(this)
        setUpToolbar()
        initRecyclerView()
        return mFragmentEntitiesListBinding!!.root
    }

    override fun onResume() {
        super.onResume()
        if (mNotebooksListPresenter != null) {
            mNotebooksListPresenter!!.bindView(this)
        }
    }

    override fun onPause() {
        super.onPause()
        mNotebooksListPresenter!!.unbindView()
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

        mNotebooksListPresenter!!.subscribeSearchView(mMenuSearch)

        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        mNotebooksListPresenter!!.disposePagination()
        mNotebooksListPresenter!!.disposeSearch()
    }

    override fun updateRecyclerView() {
        mNotebooksRecyclerViewAdapter!!.notifyDataSetChanged()
        Logger.d("Recycler view is updated")
    }

    override fun getSearchQuery(): String {
        return mSearchView!!.query.toString()
    }

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bottomUnderline = resources.getDrawable(R.drawable.search_view_bottom_underline, null)
        }
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

    /**
     * A method which initializes recycler view with data
     */
    private fun initRecyclerView() {
        mRecyclerViewAllEntities = mFragmentEntitiesListBinding!!.recyclerViewAllEntities
        mRecyclerViewAllEntities!!.setHasFixedSize(true)

        mRecyclerViewAllEntities!!.layoutManager = LinearLayoutManager(context)

        mNotebooksRecyclerViewAdapter = NotebooksListAdapter(this)
        mNotebooksListPresenter!!.setDataToAdapter(mNotebooksRecyclerViewAdapter!!)
        mRecyclerViewAllEntities!!.adapter = mNotebooksRecyclerViewAdapter

        mNotebooksListPresenter!!.subscribeRecyclerViewForPagination(mRecyclerViewAllEntities!!)
    }

    /**
     * A method which sets defined view of toolbar
     */
    private fun setUpToolbar() {
        setHasOptionsMenu(true)
        if ((activity as AppCompatActivity?)!!.supportActionBar != null) {
            (activity as AppCompatActivity?)!!.supportActionBar!!.show()
            (activity as AppCompatActivity?)!!.supportActionBar!!.setTitle(R.string.drawer_main_menu_notebooks)
        }
    }
}
