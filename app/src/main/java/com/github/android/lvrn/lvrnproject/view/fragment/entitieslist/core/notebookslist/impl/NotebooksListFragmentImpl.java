package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist.impl;

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

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.view.activity.main.MainActivityImpl;
import com.github.android.lvrn.lvrnproject.view.adapter.impl.AllNotebooksAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist.NotebooksListFragment;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist.NotebooksListPresenter;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NotebooksListFragmentImpl extends Fragment implements NotebooksListFragment {
    public static final String TOOLBAR_TITLE = "All Notes";

    @Inject NotebookService mNotebookService;

    @BindView(R.id.recycler_view_all_notes) RecyclerView mNotebooksRecyclerView;

    private Unbinder mUnbinder;

    private AllNotebooksAdapter mNotebooksRecyclerViewAdapter;

    private SearchView mSearchView;

    private MenuItem mMenuSearch;

//    TODO: introduce in future milestones
//    private MenuItem menuSync, menuSortBy, menuSettings, menuAbout;

    private NotebooksListPresenter mNotebooksListPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_notes, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        LavernaApplication.getsAppComponent().inject(this);

        mNotebooksListPresenter = new NotebooksListPresenterImpl(mNotebookService);

        setUpToolbar();
        initRecyclerView();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mNotebooksListPresenter == null) {
            mNotebooksListPresenter = new NotebooksListPresenterImpl(mNotebookService);
        }
        mNotebooksListPresenter.bindView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mNotebooksListPresenter.unbindView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_all_notes, menu);

        mMenuSearch = menu.findItem(R.id.item_action_search);
//        TODO: introduce in future milestones
//        menuSync = menu.findItem(R.id.item_action_sync);
//        menuAbout = menu.findItem(R.id.item_about);
//        menuSortBy = menu.findItem(R.id.item_sort_by);
//        menuSettings = menu.findItem(R.id.item_settings);

        mSearchView = (SearchView) MenuItemCompat.getActionView(mMenuSearch);

        mNotebooksListPresenter.subscribeSearchView(mMenuSearch);

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        mNotebooksListPresenter.disposePaginationAndSearch();
    }

    @Override
    public void updateRecyclerView() {
        mNotebooksRecyclerViewAdapter.notifyDataSetChanged();
        Logger.d("Recycler view is updated");
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

    /**
     * A method which initializes recycler view with data
     */
    private void initRecyclerView() {
        mNotebooksRecyclerView.setHasFixedSize(true);

        mNotebooksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mNotebooksRecyclerViewAdapter = new AllNotebooksAdapter(this);
        mNotebooksListPresenter.setDataToAdapter(mNotebooksRecyclerViewAdapter);
        mNotebooksRecyclerView.setAdapter(mNotebooksRecyclerViewAdapter);

        mNotebooksListPresenter.subscribeRecyclerViewForPagination(mNotebooksRecyclerView);
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
