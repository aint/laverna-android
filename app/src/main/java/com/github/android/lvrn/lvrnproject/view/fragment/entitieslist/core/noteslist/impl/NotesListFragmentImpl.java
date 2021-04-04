package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.impl;


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
import com.github.android.lvrn.lvrnproject.view.activity.notedetail.NoteDetailActivity;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl.NotesListAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.NotesListFragment;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.NotesListPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.notecontent.NoteContentFragment;
import com.github.valhallalabs.laverna.activity.MainActivity;
import com.github.valhallalabs.laverna.persistent.entity.Note;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import static com.github.android.lvrn.lvrnproject.view.util.consts.BundleKeysConstKt.BUNDLE_NOTE_OBJECT_KEY;
import static com.github.android.lvrn.lvrnproject.view.util.consts.FragmentConstKt.TAG_NOTE_CONTENT_FRAGMENT;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class NotesListFragmentImpl extends Fragment implements NotesListFragment {

    @Inject
    NotesListPresenter mNotesListPresenter;

    public static final String TOOLBAR_TITLE = "All Notes";

    private NotesListAdapter mNotesRecyclerViewAdapter;

    private SearchView mSearchView;

    private MenuItem mMenuSearch;

    private FragmentEntitiesListBinding mFragmentEntitiesListBinding;

//    TODO: introduce in future milestones
//    private MenuItem menuSync, menuSortBy, menuSettings, menuAbout;

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
        if (mNotesListPresenter != null) {
            mNotesListPresenter.bindView(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mNotesListPresenter.unbindView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_entities_list, menu);

        mMenuSearch = menu.findItem(R.id.item_action_search);
//        TODO: introduce in future milestones
//        menuSync = menu.findItem(R.id.item_action_sync);
//        menuAbout = menu.findItem(R.id.item_about);
//        menuSortBy = menu.findItem(R.id.item_sort_by);
//        menuSettings = menu.findItem(R.id.item_settings);

        mSearchView = (SearchView) MenuItemCompat.getActionView(mMenuSearch);

        mNotesListPresenter.subscribeSearchView(mMenuSearch);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mNotesListPresenter.disposePagination();
        mNotesListPresenter.disposeSearch();
    }

    @Override
    public void updateRecyclerView() {
        mNotesRecyclerViewAdapter.notifyDataSetChanged();
        Logger.d("Recycler view is updated");
    }

    @Override
    public void showSelectedNote(Note note) {
        NoteDetailActivity.Start.onStart(getActivity(),note);
    }

    @Override
    public String getSearchQuery() {
        return mSearchView.getQuery().toString();
    }

    @Override
    public void switchToSearchMode() {
        ((MainActivity) getActivity()).floatingActionsMenu.collapse();
        ((MainActivity) getActivity()).floatingActionsMenu.setVisibility(View.GONE);
//        TODO: introduce in future milestones
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

    @Override
    public void switchToNormalMode() {
        ((MainActivity) getActivity()).floatingActionsMenu.setVisibility(View.VISIBLE);
//        TODO: introduce in future milestones
//        menuSync.setVisible(true);
//        menuAbout.setVisible(true);
//        menuSortBy.setVisible(true);
//        menuSettings.setVisible(true);
    }

    /**
     * A method which initializes recycler view with data
     */
    private void initRecyclerView() {
        RecyclerView recyclerViewAllEntities = mFragmentEntitiesListBinding.recyclerViewAllEntities;
        recyclerViewAllEntities.setHasFixedSize(true);

        recyclerViewAllEntities.setLayoutManager(new LinearLayoutManager(getContext()));

        mNotesRecyclerViewAdapter = new NotesListAdapter(this, mNotesListPresenter);
        mNotesListPresenter.setDataToAdapter(mNotesRecyclerViewAdapter);
        recyclerViewAllEntities.setAdapter(mNotesRecyclerViewAdapter);

        mNotesListPresenter.subscribeRecyclerViewForPagination(recyclerViewAllEntities);
    }

    /**
     * A method which sets defined view of toolbar
     */
    private void setUpToolbar() {
        setHasOptionsMenu(true);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(TOOLBAR_TITLE);
        }
    }
}