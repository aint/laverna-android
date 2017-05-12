package com.github.android.lvrn.lvrnproject.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class AllNotesFragment extends Fragment {
    private List<Note> dataAllNotes = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
    private AllNotesFragmentRecyclerViewAdapter mAdapter;
    @Inject NoteService noteService;
    @BindView(R.id.recycler_view_all_notes) RecyclerView mRecyclerView;

    //TODO: temporary, remove later
    private String profileId;
    ProfileService profileService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_notes, container, false);
        ButterKnife.bind(this,rootView);
        LavernaApplication.getsAppComponent().inject(this);
        noteService.openConnection();
        hardcode();
        initRecyclerView();
        reInitBaseView();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        profileService.closeConnection();
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
        mAdapter = new AllNotesFragmentRecyclerViewAdapter(getActivity(), dataAllNotes);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(initEndlessRecyclerViewScroll());
    }

    private EndlessRecyclerViewScrollListener initEndlessRecyclerViewScroll(){
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener((LinearLayoutManager) mLayoutManager){
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                int curSize = mAdapter.getItemCount();
                dataAllNotes.addAll(noteService.getByProfile(profileId,8,10));
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
        noteService.create(new NoteForm(profiles.get(0).getId(), null, "Note 1", "Content 1", false));
        noteService.create(new NoteForm(profiles.get(0).getId(), null, "Note 2", "Content 2", false));
        noteService.create(new NoteForm(profiles.get(0).getId(), null, "Note 3", "Content 3", false));
        noteService.create(new NoteForm(profiles.get(0).getId(), null, "Note 4", "Content 4", false));
        noteService.create(new NoteForm(profiles.get(0).getId(), null, "Note 5", "Content 5", false));
        noteService.create(new NoteForm(profiles.get(0).getId(), null, "Note 6", "Content 6", false));
        noteService.create(new NoteForm(profiles.get(0).getId(), null, "Note 7", "Content 7", false));
        noteService.create(new NoteForm(profiles.get(0).getId(), null, "Note 8", "Content 8", false));
        noteService.create(new NoteForm(profiles.get(0).getId(), null, "Note 9", "Content 9", false));
        noteService.create(new NoteForm(profiles.get(0).getId(), null, "Note 10", "Content 10", false));
    }

}