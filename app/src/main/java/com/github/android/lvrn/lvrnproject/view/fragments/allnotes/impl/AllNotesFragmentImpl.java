package com.github.android.lvrn.lvrnproject.view.fragments.allnotes.impl;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.impl.ProfileRepositoryImpl;
import com.github.android.lvrn.lvrnproject.service.core.NoteService;
import com.github.android.lvrn.lvrnproject.service.core.ProfileService;
import com.github.android.lvrn.lvrnproject.service.core.impl.ProfileServiceImpl;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.view.activities.noteeditor.impl.NoteEditorActivityImpl;
import com.github.android.lvrn.lvrnproject.view.adapters.NotesRecyclerViewAdapter;
import com.github.android.lvrn.lvrnproject.view.fragments.SingleNoteFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragments.allnotes.AllNotesFragment;
import com.github.android.lvrn.lvrnproject.view.fragments.allnotes.AllNotesFragmentPresenter;
import com.github.android.lvrn.lvrnproject.view.util.CurrentState;
import com.github.android.lvrn.lvrnproject.view.util.consts.BundleKeysConst;
import com.github.android.lvrn.lvrnproject.view.util.consts.TagFragmentConst;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class AllNotesFragmentImpl extends Fragment
        implements NotesRecyclerViewAdapter.ItemClickListener, AllNotesFragment {
    @Inject
    NoteService mNoteService;
    @BindView(R.id.recycler_view_all_notes)
    RecyclerView mRecyclerView;
    @BindView(R.id.floating_action_menu_all_notes)
    FloatingActionsMenu floatingActionsMenu;
    final static private int startPositionDownloadItem = 1;
    final static private int numberEntitiesDownloadItem = 7;
    private List<Note> mDataAllNotes = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
    private NotesRecyclerViewAdapter mAdapter;
    private SearchView mSearchView;
    private MenuItem menuSearch, menuSync, menuSortBy, menuSettings, menuAbout;
    private AllNotesFragmentPresenter mAllNotesFragmentPresenter;

    //TODO: temporary, remove later
    private ProfileService profileService;

    private enum Mode {
        NORMAL, SEARCH, SORT, ABOUT, SETTINGS;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_notes, container, false);
        ButterKnife.bind(this, rootView);
        LavernaApplication.getsAppComponent().inject(this);
        mNoteService.openConnection();
        reInitBaseView();
        //TODO: temporary, remove later
        hardcode();
        initRecyclerView();
        return rootView;
    }

    @OnClick(R.id.floating_btn_start_note)
    public void openActivityA() {
        getActivity().startActivity(new Intent(getActivity(), NoteEditorActivityImpl.class));
        getActivity().finish();
    }

    @OnClick(R.id.floating_btn_start_notebook)
    public void openActivityB() {
        Toast.makeText(getContext(), "Activity2", Toast.LENGTH_SHORT).show();
        floatingActionsMenu.collapse();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAllNotesFragmentPresenter == null) {
            mAllNotesFragmentPresenter = new AllNotesFragmentPresenterImpl(mNoteService);
        }
        mRecyclerView.addOnScrollListener(mAllNotesFragmentPresenter.getEndlessRecyclerViewScrollListener((LinearLayoutManager) mLayoutManager,
                mAdapter,
                mDataAllNotes));
        mAllNotesFragmentPresenter.bindView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mAllNotesFragmentPresenter.unBindView();
        mAllNotesFragmentPresenter.unsubscribeSearchViewForSearch();
    }

    @Override
    public void onClick(View view, int position) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        SingleNoteFragmentImpl singleNoteFragmentImpl = new SingleNoteFragmentImpl();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BundleKeysConst.BUNDLE_NOTE_OBJECT_KEY, mAdapter.allNotesData.get(position));
        singleNoteFragmentImpl.setArguments(bundle);
        fragmentManager
                .beginTransaction()
                .replace(R.id.constraint_container, singleNoteFragmentImpl, TagFragmentConst.TAG_SINGLE_NOTE_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_all_notes, menu);
        menuSearch = menu.findItem(R.id.item_action_search);
        menuSync = menu.findItem(R.id.item_action_sync);
        menuAbout = menu.findItem(R.id.item_about);
        menuSortBy = menu.findItem(R.id.item_sort_by);
        menuSettings = menu.findItem(R.id.item_settings);
        mSearchView = (SearchView) MenuItemCompat.getActionView(menuSearch);
        mAllNotesFragmentPresenter.subscribeSearchViewForSearch(mSearchView);
        MenuItemCompat.setOnActionExpandListener(menuSearch, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                startMode(Mode.SEARCH);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                startMode(Mode.NORMAL);
                mAdapter.setAllNotesData(mDataAllNotes);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onStop() {
        super.onStop();
        floatingActionsMenu.collapse();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        profileService.closeConnection();
    }

    @Override
    public void setDataInList(List<Note> data) {
        mAdapter.setAllNotesData(data);

    }

    private void reInitBaseView() {
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("All Notes");
    }

    private void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mDataAllNotes.clear();
        mDataAllNotes.addAll(mNoteService.getByProfile(CurrentState.profileId, startPositionDownloadItem, numberEntitiesDownloadItem));
        mAdapter = new NotesRecyclerViewAdapter(mDataAllNotes);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(this);
    }

    private void startMode(Mode modeToStart) {
        if (modeToStart == Mode.SEARCH) {
            floatingActionsMenu.collapse();
            floatingActionsMenu.setVisibility(View.GONE);
            menuSync.setVisible(false);
            menuAbout.setVisible(false);
            menuSortBy.setVisible(false);
            menuSettings.setVisible(false);
            mSearchView.setQueryHint(getString(R.string.fragment_all_notes_menu_search_query_hint));
            mSearchView.requestFocus();
            Drawable bottomUnderline = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                bottomUnderline = getResources().getDrawable(R.drawable.search_view_bottom_underline, null);
            }
            mSearchView.setBackground(bottomUnderline);
        } else if (modeToStart == Mode.NORMAL) {
            floatingActionsMenu.setVisibility(View.VISIBLE);
            menuSync.setVisible(true);
            menuAbout.setVisible(true);
            menuSortBy.setVisible(true);
            menuSettings.setVisible(true);
        }

    }

    //TODO: temporary, remove later
    private void hardcode() {
        profileService = new ProfileServiceImpl(new ProfileRepositoryImpl());
        profileService.openConnection();
        for (Note note : mNoteService.getByProfile(CurrentState.profileId, 1, 200)) {
            System.out.println(mNoteService.remove(note.getId()));
        }
        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Dog", "Content 1", "Content 1", false));
        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Cat", "Content 2", "Content 2", false));
        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Bird", "Content 3", "Content 3", false));
        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Pig", "Content 4", "Content 4", false));
        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Tiger", "Content 5", "Content 5", false));
        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Duck", "Content 6", "Content 6", false));
        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Wild Cat", "Content 7", "Content 7", false));
        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Goose", "Content 8", "Content 8", false));
        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Rat", "Content 9", "Content 9", false));
        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Butterfly", "Content 10", "Content 10", false));
        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Elephant", "Content 11", "Content 11", false));
        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Chicken", "Content 12", "Content 12", false));
        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Cock", "Content 13", "Content 13", false));
        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Bug", "Content 14", "Content 14", false));
        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Snake", "Content 15", "Content 15", false));
    }

}