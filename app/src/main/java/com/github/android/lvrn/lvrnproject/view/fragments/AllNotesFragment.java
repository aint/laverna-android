package com.github.android.lvrn.lvrnproject.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.impl.ProfileRepositoryImpl;
import com.github.android.lvrn.lvrnproject.service.extension.NoteService;
import com.github.android.lvrn.lvrnproject.service.extension.ProfileService;
import com.github.android.lvrn.lvrnproject.service.extension.impl.ProfileServiceImpl;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.view.adapters.AllNotesFragmentRecyclerViewAdapter;
import com.github.android.lvrn.lvrnproject.view.adapters.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;


/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class AllNotesFragment extends Fragment {
    private List<Note> dataAllNotes = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
    private AllNotesFragmentRecyclerViewAdapter mAdapter;

    private int mLazyCount = 1;


    SearchView searchView;
    @Inject NoteService noteService;
    @BindView(R.id.recycler_view_all_notes) RecyclerView mRecyclerView;

    //TODO: temporary, remove later
    private String profileId;
    ProfileService profileService;

    private Disposable disposable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_notes, container, false);
        ButterKnife.bind(this,rootView);
        LavernaApplication.getsAppComponent().inject(this);
        noteService.openConnection();
        setHasOptionsMenu(true);
        hardcode();
        initRecyclerView();
        reInitBaseView();
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.acitivity_main, menu);
        MenuItem searchItem = menu.findItem(R.id.item_action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        disposable = RxSearch.fromSearchView(searchView)
                .debounce(400, TimeUnit.MILLISECONDS)
                .filter(word -> word.length() > 2)
                .map(title-> noteService.getByTitle(profileId,title,1,10))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(foundItems-> {
                    mAdapter.setFilteredItems(foundItems);
                });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        profileService.closeConnection();
        if(!disposable.isDisposed())
        disposable.dispose();
    }

    private void reInitBaseView() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        FloatingActionButton floatingBtn = (FloatingActionButton)(getActivity()).findViewById(R.id.fab);
        floatingBtn.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.ic_add_middle_white_24dp));
        floatingBtn.setOnClickListener(v -> Toast.makeText(getContext(),"Fragment №1",Toast.LENGTH_SHORT).show());
    }

    private void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        dataAllNotes.addAll(noteService.getByProfile(profileId,1,7));
        mAdapter = new AllNotesFragmentRecyclerViewAdapter(getActivity(),dataAllNotes);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(initEndlessRecyclerViewScroll());
    }

    private EndlessRecyclerViewScrollListener initEndlessRecyclerViewScroll(){
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener((LinearLayoutManager) mLayoutManager){
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                int curSize = mAdapter.getItemCount();
                dataAllNotes.addAll(noteService.getByProfile(profileId, mLazyCount*8,10));
                mLazyCount++;
                view.post(() -> mAdapter.notifyItemRangeInserted(curSize, dataAllNotes.size()-1));
            }
        };
        return scrollListener;
    }
    //TODO: temporary, remove later
    private void hardcode() {
        profileService = new ProfileServiceImpl(new ProfileRepositoryImpl());
        profileService.openConnection();
        List<Profile> profiles = profileService.getAll();
        profileService.closeConnection();
        profileId = profiles.get(0).getId();
        noteService.create(new NoteForm(profiles.get(0).getId(), null, "Dog", "Content 1", false));
        noteService.create(new NoteForm(profiles.get(0).getId(), null, "Cat", "Content 2", false));
        noteService.create(new NoteForm(profiles.get(0).getId(), null, "Bird", "Content 3", false));
        noteService.create(new NoteForm(profiles.get(0).getId(), null, "Pig", "Content 4", false));
        noteService.create(new NoteForm(profiles.get(0).getId(), null, "Tiger", "Content 5", false));
        noteService.create(new NoteForm(profiles.get(0).getId(), null, "Duck", "Content 6", false));
        noteService.create(new NoteForm(profiles.get(0).getId(), null, "Wild Cat", "Content 7", false));
        noteService.create(new NoteForm(profiles.get(0).getId(), null, "Goose", "Content 8", false));
        noteService.create(new NoteForm(profiles.get(0).getId(), null, "Rat", "Content 9", false));
        noteService.create(new NoteForm(profiles.get(0).getId(), null, "Butterfly", "Content 10", false));
        noteService.create(new NoteForm(profiles.get(0).getId(), null, "Elephant", "Content 11", false));
        noteService.create(new NoteForm(profiles.get(0).getId(), null, "Chicken", "Content 12", false));
        noteService.create(new NoteForm(profiles.get(0).getId(), null, "Cock", "Content 13", false));
        noteService.create(new NoteForm(profiles.get(0).getId(), null, "Bug", "Content 14", false));
        noteService.create(new NoteForm(profiles.get(0).getId(), null, "Snake", "Content 15", false));
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


}