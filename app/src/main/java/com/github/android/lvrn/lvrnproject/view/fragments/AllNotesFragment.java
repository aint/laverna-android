package com.github.android.lvrn.lvrnproject.view.fragments;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.impl.ProfileRepositoryImpl;
import com.github.android.lvrn.lvrnproject.service.extension.NoteService;
import com.github.android.lvrn.lvrnproject.service.extension.ProfileService;
import com.github.android.lvrn.lvrnproject.service.extension.impl.ProfileServiceImpl;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.view.activities.noteeditoractivity.impl.NoteEditorActivityImpl;
import com.github.android.lvrn.lvrnproject.view.adapters.AllNotesFragmentRecyclerViewAdapter;
import com.github.android.lvrn.lvrnproject.view.adapters.EndlessRecyclerViewScrollListener;
import com.github.android.lvrn.lvrnproject.view.util.consts.BundleKeysConst;
import com.github.android.lvrn.lvrnproject.view.util.consts.TagFragmentConst;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;


/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class AllNotesFragment extends Fragment
           implements AllNotesFragmentRecyclerViewAdapter.ItemClickListener {
    @Inject NoteService mNoteService;
    @BindView(R.id.recycler_view_all_notes) RecyclerView mRecyclerView;
    @BindView(R.id.floating_action_menu_all_notes) FloatingActionsMenu floatingActionsMenu;
    final static private int startPositionDownloadItem = 1;
    final static private int numberEntitiesDownloadItem = 7;
    private List<Note> mDataAllNotes = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
    private AllNotesFragmentRecyclerViewAdapter mAdapter;
    private Disposable mDisposable;
    private SearchView mSearchView;
    MenuItem menuSearch,menuSync,menuSortBy,menuSettings,menuAbout;


    //TODO: temporary, remove later
    private String profileId;
    private ProfileService profileService;

    private enum Mode {
        NORMAL,SEARCH,SORT,ABOUT,SETTINGS;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_notes, container, false);
        ButterKnife.bind(this,rootView);
        LavernaApplication.getsAppComponent().inject(this);
        mNoteService.openConnection();
        setHasOptionsMenu(true);
//        getActivity().setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //TODO: temporary, remove later
        hardcode();
        initRecyclerView();
        reInitBaseView();
        return rootView;
    }

    @OnClick(R.id.floating_btn_start_note)
    public void openActivityA(){
        Toast.makeText(getContext(),"Activity1",Toast.LENGTH_SHORT).show();
        floatingActionsMenu.collapse();
    }

    @OnClick(R.id.floating_btn_start_notebook)
    public void openActivityB(){
        Toast.makeText(getContext(),"Activity2",Toast.LENGTH_SHORT).show();
        floatingActionsMenu.collapse();
    }

    @Override
    public void onClick(View view, int position) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        SingleNoteFragment singleNoteFragment = new SingleNoteFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BundleKeysConst.BUNDLE_NOTE_OBJECT_KEY,mAdapter.allNotesData.get(position));
        singleNoteFragment.setArguments(bundle);
        fragmentManager
                .beginTransaction()
                .replace(R.id.constraint_container,singleNoteFragment, TagFragmentConst.TAG_SINGLE_NOTE_FRAGMENT)
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
        searchNoteInDBListener();
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
        if(!mDisposable.isDisposed())
        mDisposable.dispose();
    }

    private void reInitBaseView() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    private void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mDataAllNotes.clear();
        mDataAllNotes.addAll(mNoteService.getByProfile(profileId,startPositionDownloadItem,numberEntitiesDownloadItem));
        mAdapter = new AllNotesFragmentRecyclerViewAdapter(mDataAllNotes);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(this);
        mRecyclerView.addOnScrollListener(initEndlessRecyclerViewScroll());
    }

    private void searchNoteInDBListener(){
        mDisposable = RxSearch.fromSearchView(mSearchView)
                .debounce(400,TimeUnit.MILLISECONDS)
                .filter(word -> word.length() > 2)
                .map(title-> mNoteService.getByTitle(profileId,title,1,10))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(foundItems-> {
                    mAdapter.setAllNotesData(foundItems);
                });
    }

    private EndlessRecyclerViewScrollListener initEndlessRecyclerViewScroll(){
        EndlessRecyclerViewScrollListener mScrollListener = new EndlessRecyclerViewScrollListener((LinearLayoutManager) mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mDataAllNotes.addAll(mNoteService.getByProfile(profileId, totalItemsCount + 1, numberEntitiesDownloadItem));
                view.post(() -> {
                    mAdapter.notifyItemRangeInserted(mAdapter.getItemCount(), mDataAllNotes.size() - 1);
                });
            }
        };
        return mScrollListener;
    }

    private void startMode(Mode modeToStart){
        if(modeToStart == Mode.SEARCH){
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
                bottomUnderline = getResources().getDrawable(R.drawable.search_view_bottom_underline,null);
            }
            mSearchView.setBackground(bottomUnderline);
        } else if(modeToStart == Mode.NORMAL){
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
        List<Profile>  profiles = profileService.getAll();
        profileService.closeConnection();
        profileId = profiles.get(0).getId();
        for (Note note : mNoteService.getByProfile(profileId, 1, 200)){
            System.out.println(mNoteService.remove(note.getId()));
        }
        mNoteService.create(new NoteForm(profiles.get(0).getId(), null, "Dog", "Content 1", "Content 1", false));
        mNoteService.create(new NoteForm(profiles.get(0).getId(), null, "Cat", "Content 2", "Content 2", false));
        mNoteService.create(new NoteForm(profiles.get(0).getId(), null, "Bird", "Content 3", "Content 3", false));
        mNoteService.create(new NoteForm(profiles.get(0).getId(), null, "Pig", "Content 4", "Content 4", false));
        mNoteService.create(new NoteForm(profiles.get(0).getId(), null, "Tiger", "Content 5", "Content 5", false));
        mNoteService.create(new NoteForm(profiles.get(0).getId(), null, "Duck", "Content 6", "Content 6", false));
        mNoteService.create(new NoteForm(profiles.get(0).getId(), null, "Wild Cat", "Content 7", "Content 7", false));
        mNoteService.create(new NoteForm(profiles.get(0).getId(), null, "Goose", "Content 8", "Content 8", false));
        mNoteService.create(new NoteForm(profiles.get(0).getId(), null, "Rat", "Content 9", "Content 9", false));
        mNoteService.create(new NoteForm(profiles.get(0).getId(), null, "Butterfly", "Content 10", "Content 10", false));
        mNoteService.create(new NoteForm(profiles.get(0).getId(), null, "Elephant", "Content 11", "Content 11", false));
        mNoteService.create(new NoteForm(profiles.get(0).getId(), null, "Chicken", "Content 12", "Content 12", false));
        mNoteService.create(new NoteForm(profiles.get(0).getId(), null, "Cock", "Content 13", "Content 13", false));
        mNoteService.create(new NoteForm(profiles.get(0).getId(), null, "Bug", "Content 14", "Content 14", false));
        mNoteService.create(new NoteForm(profiles.get(0).getId(), null, "Snake", "Content 15", "Content 15", false));
    }

     private static class RxSearch {
         static Observable<String> fromSearchView(@NonNull final SearchView searchView){
            final BehaviorSubject<String> subject = BehaviorSubject.create();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return true;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    if (!newText.isEmpty()){
                     subject.onNext(newText);
                    }
                    return true;
                }
            });
            return subject;
        }
    }

    /**
     * A method which starts the note editor activity.
     * @param view
     */
    private void startNoteEditorActivity(View view) {
        Intent intent = new Intent(getActivity(), NoteEditorActivityImpl.class);
        startActivity(intent);
        getActivity().finish();
    }

}