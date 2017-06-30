package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.core.allnotes.impl;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl.NotesListAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.core.allnotes.AllNotesFragment;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.core.allnotes.AllNotesPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.impl.NotesListFragmentImpl;

import javax.inject.Inject;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class AllNotesFragmentImpl extends NotesListFragmentImpl implements AllNotesFragment {
    public static final String TOOLBAR_TITLE = "Notes";

    @Inject
    AllNotesPresenter mAllNotesPresenter;

    public AllNotesFragmentImpl() {
        super(TOOLBAR_TITLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LavernaApplication.getsAppComponent().inject(this);
        linkPresenterAndAdapter(mAllNotesPresenter, new NotesListAdapter(this, mAllNotesPresenter));
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}