package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.core.trashnotes.impl;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl.TrashListAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.core.trashnotes.TrashListFragment;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.core.trashnotes.TrashListPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.impl.NotesListFragmentImpl;

import javax.inject.Inject;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class TrashListFragmentImpl extends NotesListFragmentImpl implements TrashListFragment {
    public static final String TOOLBAR_TITLE = "Trash";


    @Inject TrashListPresenter mTrashListPresenter;

    public TrashListFragmentImpl() {
        super(TOOLBAR_TITLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LavernaApplication.getsAppComponent().inject(this);

        mEntitiesListPresenter = mTrashListPresenter;
        mEntitiesRecyclerViewAdapter = new TrashListAdapter(this);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}