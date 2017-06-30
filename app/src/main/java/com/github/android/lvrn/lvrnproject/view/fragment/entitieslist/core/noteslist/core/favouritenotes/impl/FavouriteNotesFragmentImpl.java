package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.core.favouritenotes.impl;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl.FavouritesListAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.core.favouritenotes.FavouriteNotesFragment;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.core.favouritenotes.FavouriteNotesPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.impl.NotesListFragmentImpl;

import javax.inject.Inject;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class FavouriteNotesFragmentImpl extends NotesListFragmentImpl implements FavouriteNotesFragment {
    public static final String TOOLBAR_TITLE = "Favourites";

    @Inject FavouriteNotesPresenter mFavouriteNotesPresenter;

    public FavouriteNotesFragmentImpl() {
        super(TOOLBAR_TITLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LavernaApplication.getsAppComponent().inject(this);
        linkPresenterAndAdapter(mFavouriteNotesPresenter, new FavouritesListAdapter(this));
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}