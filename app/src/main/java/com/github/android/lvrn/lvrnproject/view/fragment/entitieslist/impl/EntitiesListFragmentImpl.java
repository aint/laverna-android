package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.impl;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.ProfileDependedEntity;
import com.github.android.lvrn.lvrnproject.service.form.ProfileDependedForm;
import com.github.android.lvrn.lvrnproject.view.activity.main.MainActivityImpl;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.DataPostSetAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListFragment;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListPresenter;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class EntitiesListFragmentImpl<T1 extends ProfileDependedEntity, T2 extends ProfileDependedForm<T1>, T3 extends RecyclerView.ViewHolder>
        extends Fragment implements EntitiesListFragment {

    @BindView(R.id.recycler_view_all_entities) RecyclerView mEntitiesRecyclerView;

    protected EntitiesListPresenter<T1, T2, T3> mEntitiesListPresenter;

    private final String TOOLBAR_TITLE;

    private Unbinder mUnbinder;

    protected DataPostSetAdapter<T1, T3> mEntitiesRecyclerViewAdapter;

    private SearchView mSearchView;

    private MenuItem mMenuSearch;

//    TODO: introduce in future milestones
//    private MenuItem menuSync, menuSortBy, menuSettings, menuAbout;

    public EntitiesListFragmentImpl(String TOOLBAR_TITLE) {
        this.TOOLBAR_TITLE = TOOLBAR_TITLE;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_entities_list, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        setUpToolbar();
        initRecyclerView();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mEntitiesListPresenter != null) {
            mEntitiesListPresenter.bindView(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mEntitiesListPresenter.unbindView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        mEntitiesListPresenter.disposePagination();
        mEntitiesListPresenter.disposeSearch();
    }

    @Override
    public void updateRecyclerView() {
        mEntitiesRecyclerViewAdapter.notifyDataSetChanged(); //TODO: find out how to resolve it.
        Logger.d("Recycler view is updated");
    }

    protected void linkPresenterAndAdapter(EntitiesListPresenter<T1, T2, T3> entitiesListPresenter, DataPostSetAdapter<T1, T3> entitiesRecyclerViewAdapter) {
        mEntitiesListPresenter = entitiesListPresenter;
        mEntitiesRecyclerViewAdapter = entitiesRecyclerViewAdapter;
    }

    /**
     * A method which initializes recycler view with data
     */
    private void initRecyclerView() {
        mEntitiesRecyclerView.setHasFixedSize(true);

        mEntitiesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        mEntitiesRecyclerViewAdapter = new NotesListAdapter(this, mEntitiesListPresenter);

        mEntitiesListPresenter.setDataToAdapter(mEntitiesRecyclerViewAdapter);

        mEntitiesRecyclerView.setAdapter(mEntitiesRecyclerViewAdapter);

        mEntitiesListPresenter.subscribeRecyclerViewForPagination(mEntitiesRecyclerView);
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

        mEntitiesListPresenter.subscribeSearchView(mMenuSearch);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public String getSearchQuery() {
        return mSearchView.getQuery().toString();
    }

    @Override
    public void switchToSearchMode() {
        ((MainActivityImpl) getActivity()).floatingActionsMenu.collapse();
        ((MainActivityImpl) getActivity()).floatingActionsMenu.setVisibility(View.GONE);
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
        ((MainActivityImpl) getActivity()).floatingActionsMenu.setVisibility(View.VISIBLE);
//        TODO: introduce in future milestones
//        menuSync.setVisible(true);
//        menuAbout.setVisible(true);
//        menuSortBy.setVisible(true);
//        menuSettings.setVisible(true);
    }
}
