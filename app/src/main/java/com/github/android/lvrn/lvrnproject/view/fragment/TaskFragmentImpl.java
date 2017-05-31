package com.github.android.lvrn.lvrnproject.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Task;
import com.github.android.lvrn.lvrnproject.service.core.NoteService;
import com.github.android.lvrn.lvrnproject.service.core.TaskService;
import com.github.android.lvrn.lvrnproject.view.adapter.TaskRecyclerViewAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.singlenote.SingleNoteFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.util.consts.BundleKeysConst;
import com.github.android.lvrn.lvrnproject.view.util.consts.TagFragmentConst;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class TaskFragmentImpl extends Fragment {
    @BindView(R.id.recycler_view_task)
    RecyclerView mRecyclerViewNote;
    @Inject
    TaskService mTaskService;
    @Inject
    NoteService mNoteService;
    private  TaskRecyclerViewAdapter mTaskRecyclerViewAdapter;
    private List<Task> mTaskData = new ArrayList<>();
    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        mUnbinder = ButterKnife.bind(this, view);
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
        if (mUnbinder != null){
            mUnbinder.unbind();
        }
    }

    //    @Override
    public void onClick(View view, int position) {
        SingleNoteFragmentImpl singleNoteFragment = new SingleNoteFragmentImpl();
        Bundle bundle = new Bundle();
        //TODO: temporary, change later
        bundle.putParcelable(BundleKeysConst.BUNDLE_NOTE_OBJECT_KEY,mTaskRecyclerViewAdapter.mTaskData.get(position));
        singleNoteFragment.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.constraint_container,singleNoteFragment, TagFragmentConst.TAG_SINGLE_NOTE_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        mRecyclerViewNote.setLayoutManager(linearLayout);
//        mTaskData.addAll(mTaskService.getByProfile(CurrentState.profileId, 0, 7));
        mTaskRecyclerViewAdapter = new TaskRecyclerViewAdapter(mTaskData);
        mRecyclerViewNote.setAdapter(mTaskRecyclerViewAdapter);
        mTaskService.closeConnection();
//        mTaskRecyclerViewAdapter.setClickListener(this);
    }

    private void setUpToolbar() {
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("All Tasks");
    }

    //TODO: temporary, remove later
    private void hardcode() {
//        mTaskService.openConnection();
//        NoteForm mNote = new NoteForm(CurrentState.profileId, null, "Monkey", "This is monkey", "", false);
//        mNoteService.openConnection();
//        mTaskService.create(new TaskForm(CurrentState.profileId, mNoteService.create(mNote).get(), "Feed Animal", false));
    }


}
