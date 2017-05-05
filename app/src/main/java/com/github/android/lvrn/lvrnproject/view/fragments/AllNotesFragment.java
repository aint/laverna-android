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

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.view.adapters.AllNotesFragmentRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class AllNotesFragment extends Fragment {
    @BindView(R.id.recycler_view_all_notes)
     RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_notes, container, false);
        ButterKnife.bind(this,rootView);
        initRecyclerView(rootView);
        reInitBaseView();
        return rootView;
    }

    private void reInitBaseView() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        FloatingActionButton floatingBtn = (FloatingActionButton)(getActivity()).findViewById(R.id.fab);
        floatingBtn.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.ic_add_middle_white_24dp));
        floatingBtn.setOnClickListener(v -> Toast.makeText(getContext(),"Fragment №1",Toast.LENGTH_SHORT).show());
    }

    private void initRecyclerView(View rootView) {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        AllNotesFragmentRecyclerViewAdapter mAdapter = new AllNotesFragmentRecyclerViewAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }

}