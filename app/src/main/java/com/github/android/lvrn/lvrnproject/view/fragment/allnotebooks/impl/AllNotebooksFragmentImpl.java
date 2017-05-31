package com.github.android.lvrn.lvrnproject.view.fragment.allnotebooks.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.view.adapter.impl.AllNotebooksAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.NotebookContentFragmentImpl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class AllNotebooksFragmentImpl extends Fragment {
    @BindView(R.id.recycler_view_all_notes)
    RecyclerView mRecyclerView;
    @Inject
    NotebookService mNotebookService;
    private List<Notebook> mNotebookData = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
    private AllNotebooksAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_notes, container, false);
        ButterKnife.bind(this, rootView);
        initRecyclerView();
        LavernaApplication.getsAppComponent().inject(this);
        reInitBaseView();
        //TODO: clean it, use ifPresent method on Optional.
//        mNotebookService.openConnection();
//        mNotebookService.create(new NotebookForm(CurrentState.profileId, null, "Animals"));
//        if (mNotebookService.getByProfile(CurrentState.profileId, 1, 10) != null) {
//            mNotebookData.addAll(mNotebookService.getByProfile(CurrentState.profileId, 1, 10));
//        }
//        mNotebookService.closeConnection();
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_all_notes, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


//    @Override
    public void onClick(View view, int position) {
        NotebookContentFragmentImpl noteAndNotebookFragment = new NotebookContentFragmentImpl();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.constraint_container, noteAndNotebookFragment)
                .addToBackStack(null)
                .commit();

    }

    /**
     * A method which initializes recycler view
     */
    private void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AllNotebooksAdapter(mNotebookData);
        mRecyclerView.setAdapter(mAdapter);
//        mAdapter.setClickListener(this);
    }

    /**
     * A method which sets defined view of main toolbar
     */
    private void reInitBaseView() {
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("All Notebook");
    }
}
