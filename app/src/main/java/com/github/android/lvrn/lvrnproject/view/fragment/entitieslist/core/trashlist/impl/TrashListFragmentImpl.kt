package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.trashlist.impl

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.android.lvrn.lvrnproject.LavernaApplication
import com.github.android.lvrn.lvrnproject.R
import com.github.android.lvrn.lvrnproject.databinding.FragmentEntitiesListBinding
import com.github.android.lvrn.lvrnproject.view.activity.notedetail.NoteDetailActivity.Start.onStart
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl.TrashListAdapter
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.trashlist.TrashListFragment
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.trashlist.TrashListPresenter
import com.github.valhallalabs.laverna.persistent.entity.Note
import com.orhanobut.logger.Logger
import javax.inject.Inject

class TrashListFragmentImpl : Fragment(), TrashListFragment {

    @set:Inject
    var trashListPresenter: TrashListPresenter? = null

    val TOOLBAR_TITLE = "Trash"

    private lateinit var notesRecyclerViewAdapter: TrashListAdapter

    private var mSearchView: SearchView? = null

    private var mMenuSearch: MenuItem? = null

    private lateinit var fragmentEntitiesListBinding: FragmentEntitiesListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentEntitiesListBinding = FragmentEntitiesListBinding.inflate(inflater, container, false)
        LavernaApplication.getsAppComponent().inject(this)
        setUpToolbar()
        initRecyclerView()
        return fragmentEntitiesListBinding.root
    }

    override fun onResume() {
        super.onResume()
        if (trashListPresenter != null) {
            trashListPresenter?.bindView(this)
        }
    }

    override fun onPause() {
        super.onPause()
        trashListPresenter?.unbindView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_entities_list, menu)
        mMenuSearch = menu.findItem(R.id.item_action_search)
        //        menuSync = menu.findItem(R.id.item_action_sync);
//        menuAbout = menu.findItem(R.id.item_about);
//        menuSortBy = menu.findItem(R.id.item_sort_by);
//        menuSettings = menu.findItem(R.id.item_settings);
        mSearchView = MenuItemCompat.getActionView(mMenuSearch) as SearchView
        trashListPresenter?.subscribeSearchView(mMenuSearch)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        trashListPresenter?.disposePagination()
        trashListPresenter?.disposeSearch()
    }

    override fun getSearchQuery(): String {
        return mSearchView!!.query.toString()
    }

    override fun showSelectedNote(note: Note) {
        onStart(requireContext(), note)
    }

    override fun removeNoteForever(position: Int) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(getString(R.string.dialog_delete_note_title))
            setMessage(getString(R.string.dialog_delete_note_text))
            setPositiveButton(getString(R.string.dialog_delete_note_forever_text)) { dialogInterface, i ->
                trashListPresenter!!.removeNoteForever(position)
            }
            setNegativeButton(getString(R.string.dialog_delete_note_cancel_btn)) { dialogInterface, i ->
                notesRecyclerViewAdapter.notifyDataSetChanged()
            }
            show()
        }
    }

    override fun showEmptyListView() {
        val tvEmptyState = fragmentEntitiesListBinding.tvEmptyState!!
        tvEmptyState.text = getText(R.string.trash_notes_empty_text)
        tvEmptyState.visibility = View.VISIBLE
    }

    override fun updateRecyclerView() {
        notesRecyclerViewAdapter.notifyDataSetChanged()
        Logger.d("Recycler view is updated")
    }

    override fun switchToNormalMode() {
        //        menuSync.setVisible(true);
//        menuAbout.setVisible(true);
//        menuSortBy.setVisible(true);
//        menuSettings.setVisible(true);
    }

    override fun switchToSearchMode() {
        //        menuSync.setVisible(false);
//        menuAbout.setVisible(false);
//        menuSortBy.setVisible(false);
//        menuSettings.setVisible(false);

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
        val recyclerViewAllEntities: RecyclerView = fragmentEntitiesListBinding.recyclerViewAllEntities
        recyclerViewAllEntities.setHasFixedSize(true)
        recyclerViewAllEntities.layoutManager = LinearLayoutManager(context)
        notesRecyclerViewAdapter = TrashListAdapter(this, trashListPresenter!!)
        trashListPresenter?.setDataToAdapter(notesRecyclerViewAdapter)
        recyclerViewAllEntities.adapter = notesRecyclerViewAdapter
        trashListPresenter?.subscribeRecyclerViewForPagination(recyclerViewAllEntities)
        if (notesRecyclerViewAdapter.itemCount == 0) {
            showEmptyListView()
        }
    }


    /**
     * A method which sets defined view of toolbar
     */
    private fun setUpToolbar() {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = TOOLBAR_TITLE
    }
}