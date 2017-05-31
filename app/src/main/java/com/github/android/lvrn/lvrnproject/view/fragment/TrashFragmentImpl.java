package com.github.android.lvrn.lvrnproject.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Entity;
import com.github.android.lvrn.lvrnproject.service.core.NoteService;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.view.adapter.TrashRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class TrashFragmentImpl extends Fragment {
    @BindView(R.id.recycler_view_trash)
    RecyclerView mRecyclerViewTrash;
    @Inject
    NoteService mNoteService;
    @Inject
    NotebookService mNotebookService;
    private List<Entity> mEntityData = new ArrayList<>();
    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trash, container, false);
        mUnbinder =  ButterKnife.bind(this, view);
        LavernaApplication.getsAppComponent().inject(this);
        //TODO: temporary, remove later
        hardcode();
        setUpToolbar();
        initRecyclerView();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mUnbinder != null){
            mUnbinder.unbind();
        }
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewTrash.setLayoutManager(linearLayoutManager);
        //TODO: temporary, remove later
//        mEntityData.addAll(mNoteService.getByProfile(CurrentState.profileId, 1, 6));
//        mEntityData.addAll(mNotebookService.getByProfile(CurrentState.profileId, 1, 2));
        TrashRecyclerViewAdapter trashRecyclerViewAdapter = new TrashRecyclerViewAdapter(mEntityData);
        mRecyclerViewTrash.setAdapter(trashRecyclerViewAdapter);
    }

    private void setUpToolbar() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Trash");
        setHasOptionsMenu(true);
    }
    //TODO: temporary, remove later
    private void hardcode() {
//        mNoteService.openConnection();
//        mNotebookService.openConnection();
//        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Dog", "Content 1", "Content 1", false));
//        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Cat", "Content 2", "Content 2", false));
//        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Bird", "Content 3", "Content 3", false));
//        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Pig", "Content 4", "Content 4", false));
//        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Tiger", "Content 5", "Content 5", false));
//        mNoteService.create(new NoteForm(CurrentState.profileId, null, "Duck", "Content 6", "Content 6", false));
//        mNotebookService.create(new NotebookForm(CurrentState.profileId, null, "Animals"));
    }

}
