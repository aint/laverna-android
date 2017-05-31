package com.github.android.lvrn.lvrnproject.view.fragment.allnotes.impl;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.service.core.NoteService;
import com.github.android.lvrn.lvrnproject.view.activity.noteeditor.impl.NoteEditorActivityImpl;
import com.github.android.lvrn.lvrnproject.view.adapter.NoteRecyclerViewAdapter;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookcreate.impl.NotebookCreateDialogFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.allnotes.AllNotesFragment;
import com.github.android.lvrn.lvrnproject.view.fragment.allnotes.AllNotesFragmentPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.singlenote.SingleNoteFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.util.consts.BundleKeysConst;
import com.github.android.lvrn.lvrnproject.view.util.consts.TagFragmentConst;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class AllNotesFragmentImpl extends Fragment implements AllNotesFragment {

    public static final String TOOLBAR_TITLE = "All Notes";

    @Inject NoteService mNoteService;

    @BindView(R.id.recycler_view_all_notes) RecyclerView mNotesRecyclerView;

    @BindView(R.id.floating_action_menu_all_notes) FloatingActionsMenu floatingActionsMenu;

    private Unbinder mUnbinder;

    private LinearLayoutManager mLinearLayoutManager;

    private NoteRecyclerViewAdapter mNotesRecyclerViewAdapter;

    private SearchView mSearchView;

    private MenuItem menuSearch;
    //, menuSync, menuSortBy, menuSettings, menuAbout;

    private AllNotesFragmentPresenter mAllNotesFragmentPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_notes, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        LavernaApplication.getsAppComponent().inject(this);

        mAllNotesFragmentPresenter = new AllNotesFragmentPresenterImpl(mNoteService);

        setUpToolbar();
        initRecyclerView();

        return rootView;
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if(mAllNotesFragmentPresenter == null) {
            mAllNotesFragmentPresenter = new AllNotesFragmentPresenterImpl(mNoteService);
        }
        mAllNotesFragmentPresenter.bindView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        floatingActionsMenu.collapse();
        mAllNotesFragmentPresenter.unbindView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_all_notes, menu);

        menuSearch = menu.findItem(R.id.item_action_search);
//        menuSync = menu.findItem(R.id.item_action_sync);
//        menuAbout = menu.findItem(R.id.item_about);
//        menuSortBy = menu.findItem(R.id.item_sort_by);
//        menuSettings = menu.findItem(R.id.item_settings);

        mSearchView = (SearchView) MenuItemCompat.getActionView(menuSearch);

        mAllNotesFragmentPresenter.subscribeSearchView(menuSearch);

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        mAllNotesFragmentPresenter.disposePaginationAndSearch();
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

    /**
     * A method which hears when user click on button and opens new activity
     */
    @OnClick(R.id.floating_btn_start_note)
    public void startNoteEditorActivity() {
        getActivity().startActivity(new Intent(getActivity(), NoteEditorActivityImpl.class));
        getActivity().finish();
    }

    @OnClick(R.id.floating_btn_start_notebook)
    public void openNotebooksCreationDialog() {
         FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null);
        DialogFragment dialogFragment = NotebookCreateDialogFragmentImpl.newInstance();
        dialogFragment.show(fragmentTransaction, TagFragmentConst.TAG_NOTEBOOK_CREATE_FRAGMENT);
        floatingActionsMenu.collapse();
    }
    
    public void showSelectedNote(Note note) {
        SingleNoteFragmentImpl singleNoteFragmentImpl = new SingleNoteFragmentImpl();

        Bundle bundle = new Bundle();
        bundle.putParcelable(BundleKeysConst.BUNDLE_NOTE_OBJECT_KEY, note);
        singleNoteFragmentImpl.setArguments(bundle);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.constraint_container, singleNoteFragmentImpl, TagFragmentConst.TAG_SINGLE_NOTE_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    /**
     * A method which initializes recycler view with data
     */
    private void initRecyclerView() {
        mNotesRecyclerView.setHasFixedSize(true);

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mNotesRecyclerView.setLayoutManager(mLinearLayoutManager);

        mNotesRecyclerViewAdapter = new NoteRecyclerViewAdapter(this, mAllNotesFragmentPresenter.getNotesForAdapter());
        mNotesRecyclerView.setAdapter(mNotesRecyclerViewAdapter);

        mAllNotesFragmentPresenter.subscribeRecyclerViewForPagination(mNotesRecyclerView);
    }

    /**
     * A method which sets defined view of toolbar
     */
    private void setUpToolbar() {
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(TOOLBAR_TITLE);
    }

    public void startSearchMode() {
        floatingActionsMenu.collapse();
        floatingActionsMenu.setVisibility(View.GONE);
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

    public void startNormalMode() {
        floatingActionsMenu.setVisibility(View.VISIBLE);
//        menuSync.setVisible(true);
//        menuAbout.setVisible(true);
//        menuSortBy.setVisible(true);
//        menuSettings.setVisible(true);
    }

}