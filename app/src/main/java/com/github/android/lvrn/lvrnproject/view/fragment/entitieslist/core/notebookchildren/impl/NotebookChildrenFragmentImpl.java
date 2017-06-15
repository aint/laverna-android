package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.impl;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl.NotebooksListAdapter;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl.NotesListAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.NotebookChildrenFragment;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.NotebookChildrenPresenter;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

//import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl.NotebooksListAdapter;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class NotebookChildrenFragmentImpl extends Fragment implements NotebookChildrenFragment {
    @BindView(R.id.recycler_view_notebooks) RecyclerView mNotebooksRecyclerView;

    @BindView(R.id.recycler_view_notes) RecyclerView mNotesRecyclerView;

    @Inject NotebookChildrenPresenter mNotebookChildrenPresenter;

    private Unbinder mUnbinder;

    private NotesListAdapter mNotesRecyclerViewAdapter;

    private NotebooksListAdapter mNotebooksRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notebook_content, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        LavernaApplication.getsAppComponent().inject(this);
        //TODO: find out way to take notebook id
        mNotebookChildrenPresenter.initializeListsPresenters(new String());

        initRecyclerViews();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mUnbinder != null){
            mUnbinder.unbind();
        }
    }

    /**
     * A method which initializes recycler view
     */
    private void initRecyclerViews() {
        initNotesRecyclerView();
        initNotebooksRecyclerView();
    }

    private void initNotesRecyclerView() {
        mNotesRecyclerView.setHasFixedSize(true);

        mNotesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //TODO: change adapter.
        mNotesRecyclerViewAdapter = new NotesListAdapter(this);
        mNotebookChildrenPresenter.getNotesListPresenter().setDataToAdapter(mNotesRecyclerViewAdapter);
        mNotesRecyclerView.setAdapter(mNotesRecyclerViewAdapter);

        mNotebookChildrenPresenter.getNotesListPresenter().subscribeRecyclerViewForPagination(mNotesRecyclerView);
    }

    private void initNotebooksRecyclerView() {
        mNotebooksRecyclerView.setHasFixedSize(true);

        mNotebooksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //TODO: change adapter.
        mNotebooksRecyclerViewAdapter = new NotebooksListAdapter(this);
        mNotebookChildrenPresenter.getNotebooksListPresenter().setDataToAdapter(mNotebooksRecyclerViewAdapter);
        mNotebooksRecyclerView.setAdapter(mNotebooksRecyclerViewAdapter);

        mNotebookChildrenPresenter.getNotebooksListPresenter().subscribeRecyclerViewForPagination(mNotebooksRecyclerView);
    }

    @Override
    public void updateRecyclerView() {
        mNotebooksRecyclerViewAdapter.notifyDataSetChanged();
        mNotesRecyclerViewAdapter.notifyDataSetChanged();
        Logger.d("Recycler view is updated");
    }
}
