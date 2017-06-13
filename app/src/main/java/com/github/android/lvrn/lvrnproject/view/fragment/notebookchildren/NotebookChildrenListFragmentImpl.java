package com.github.android.lvrn.lvrnproject.view.fragment.notebookchildren;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.service.core.NoteService;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

//import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl.NotebooksListAdapter;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class NotebookChildrenListFragmentImpl extends Fragment {

    @Inject NotebookService mNotebookService;

    @Inject NoteService mNoteService;

    @BindView(R.id.recycler_view_notebooks) RecyclerView mNotebookRecyclerView;

    @BindView(R.id.recycler_view_notes) RecyclerView mNotesRecyclerView;

    private Unbinder mUnbinder;

    private List<Notebook> mNotebookData = new ArrayList<>();

    private List<Note> mNoteData = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notebook_content, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        LavernaApplication.getsAppComponent().inject(this);
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

    /**
     * A method which initializes recycler view
     */
    private void initRecyclerView() {
    }
}
