package com.github.android.lvrn.lvrnproject.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.view.adapters.AllNotesFragmentRecyclerViewAdapter;


/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class AllNotesFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private AllNotesFragmentRecyclerViewAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_notes, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_all_notes);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AllNotesFragmentRecyclerViewAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

}