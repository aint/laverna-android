package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.favouriteslist.impl

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
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
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl.FavouritesListAdapter
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.favouriteslist.FavouritesListFragment
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.favouriteslist.FavouritesListPresenter
import com.github.valhallalabs.laverna.activity.MainActivity
import com.github.valhallalabs.laverna.persistent.entity.Note
import com.orhanobut.logger.Logger
import javax.inject.Inject


/**
 * @author Andrii Bei <psihey1></psihey1>@gmail.com>
 */
class FavouritesListFragmentImpl : Fragment(), FavouritesListFragment,
    FavouritesListAdapter.FavouriteAdapterListener {

    @Inject
    lateinit var mFavouritesListPresenter: FavouritesListPresenter

    private var mFavouritesRecyclerViewAdapter: FavouritesListAdapter? = null

    private var mSearchView: SearchView? = null


    private var mFragmentEntitiesListBinding: FragmentEntitiesListBinding? = null

    //    TODO: introduce in future milestones
    //    private MenuItem menuSync, menuSortBy, menuSettings, menuAbout;
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
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
        mFavouritesListPresenter.bindView(this)
    }

    override fun onPause() {
        super.onPause()
        mFavouritesListPresenter.unbindView()
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
        mFavouritesListPresenter.subscribeSearchView(mMenuSearch)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        mFavouritesListPresenter.disposePagination()
        mFavouritesListPresenter.disposeSearch()
    }

    override fun updateRecyclerView() {
        mFavouritesRecyclerViewAdapter?.notifyDataSetChanged()
        Logger.d("Recycler view is updated")
    }

    override fun showSelectedNote(note: Note) {
        onStart(requireActivity(), note)
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


    override fun showEmptyListView() {
        mFragmentEntitiesListBinding!!.tvEmptyState!!.visibility = View.VISIBLE
    }

    override fun changeNoteFavouriteStatus(
        note: Note,
    ) {
        mFavouritesListPresenter.changeNoteFavouriteStatus(note)
    }

    /**
     * A method which initializes recycler view with data
     */
    private fun initRecyclerView() {
        val recyclerViewAllEntities = mFragmentEntitiesListBinding!!.recyclerViewAllEntities
        recyclerViewAllEntities.setHasFixedSize(true)

        recyclerViewAllEntities.layoutManager = LinearLayoutManager(context)

        mFavouritesRecyclerViewAdapter = FavouritesListAdapter(this)
        mFavouritesListPresenter.setDataToAdapter(mFavouritesRecyclerViewAdapter!!)
        recyclerViewAllEntities.adapter = mFavouritesRecyclerViewAdapter

        mFavouritesListPresenter.subscribeRecyclerViewForPagination(recyclerViewAllEntities)
        if (mFavouritesRecyclerViewAdapter!!.itemCount == 0) {
            showEmptyListView()
        }
    }

    /**
     * A method which sets defined view of toolbar
     */
    private fun setUpToolbar() {
        setHasOptionsMenu(true)
        if ((activity as AppCompatActivity?)!!.supportActionBar != null) {
            (activity as AppCompatActivity?)!!.supportActionBar!!.show()
            (activity as AppCompatActivity?)!!.supportActionBar!!.setTitle(R.string.drawer_main_menu_favourites)
        }
    }
}