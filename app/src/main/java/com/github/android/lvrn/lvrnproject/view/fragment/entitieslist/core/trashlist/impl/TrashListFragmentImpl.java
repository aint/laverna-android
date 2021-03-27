package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.trashlist.impl;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.databinding.FragmentEntitiesListBinding;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl.TrashListAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.trashlist.TrashListFragment;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.trashlist.TrashListPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.notecontent.NoteContentFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.util.consts.BundleKeysConst;
import com.github.android.lvrn.lvrnproject.view.util.consts.FragmentConst;
import com.github.valhallalabs.laverna.persistent.entity.Note;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class TrashListFragmentImpl extends Fragment implements TrashListFragment {
    @Inject
    TrashListPresenter mTrashListPresenter;

    public static final String TOOLBAR_TITLE = "Trash";

    private TrashListAdapter mNotesRecyclerViewAdapter;

    private SearchView mSearchView;

    private MenuItem mMenuSearch;
    //, menuSync, menuSortBy, menuSettings, menuAbout;

    private FragmentEntitiesListBinding mFragmentEntitiesListBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentEntitiesListBinding = FragmentEntitiesListBinding.inflate(inflater, container, false);
        LavernaApplication.getsAppComponent().inject(this);
        setUpToolbar();
        initRecyclerView();
        return mFragmentEntitiesListBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mTrashListPresenter != null) {
            mTrashListPresenter.bindView(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mTrashListPresenter.unbindView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_entities_list, menu);

        mMenuSearch = menu.findItem(R.id.item_action_search);
//        menuSync = menu.findItem(R.id.item_action_sync);
//        menuAbout = menu.findItem(R.id.item_about);
//        menuSortBy = menu.findItem(R.id.item_sort_by);
//        menuSettings = menu.findItem(R.id.item_settings);

        mSearchView = (SearchView) MenuItemCompat.getActionView(mMenuSearch);

        mTrashListPresenter.subscribeSearchView(mMenuSearch);

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTrashListPresenter.disposePagination();
        mTrashListPresenter.disposeSearch();
    }

    @Override
    public void updateRecyclerView() {
        mNotesRecyclerViewAdapter.notifyDataSetChanged();
        Logger.d("Recycler view is updated");
    }

    @Override
    public String getSearchQuery() {
        return mSearchView.getQuery().toString();
    }

    @Override
    public void switchToNormalMode() {
//        menuSync.setVisible(true);
//        menuAbout.setVisible(true);
//        menuSortBy.setVisible(true);
//        menuSettings.setVisible(true);
    }

    @Override
    public void switchToSearchMode() {
//        menuSync.setVisible(false);
//        menuAbout.setVisible(false);
//        menuSortBy.setVisible(false);
//        menuSettings.setVisible(false);
        mSearchView.setQueryHint(getString(R.string.fragment_all_notes_menu_search_query_hint));
        mSearchView.requestFocus();
        Drawable bottomUnderline = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            bottomUnderline = getResources().getDrawable(R.drawable.search_view_bottom_underline, null);
        }
        mSearchView.setBackground(bottomUnderline);
    }

    public void showSelectedNote(Note note) {
        NoteContentFragmentImpl noteContentFragmentImpl = new NoteContentFragmentImpl();

        Bundle bundle = new Bundle();
        bundle.putParcelable(BundleKeysConst.BUNDLE_NOTE_OBJECT_KEY, note);
        noteContentFragmentImpl.setArguments(bundle);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.constraint_container, noteContentFragmentImpl, FragmentConst.TAG_NOTE_CONTENT_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    /**
     * A method which initializes recycler view with data
     */
    private void initRecyclerView() {
        RecyclerView recyclerViewAllEntities = mFragmentEntitiesListBinding.recyclerViewAllEntities;
        recyclerViewAllEntities.setHasFixedSize(true);

        recyclerViewAllEntities.setLayoutManager(new LinearLayoutManager(getContext()));

        mNotesRecyclerViewAdapter = new TrashListAdapter(this);
        mTrashListPresenter.setDataToAdapter(mNotesRecyclerViewAdapter);
        recyclerViewAllEntities.setAdapter(mNotesRecyclerViewAdapter);

        mTrashListPresenter.subscribeRecyclerViewForPagination(recyclerViewAllEntities);
    }

    /**
     * A method which sets defined view of toolbar
     */
    private void setUpToolbar() {
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(TOOLBAR_TITLE);
    }
}