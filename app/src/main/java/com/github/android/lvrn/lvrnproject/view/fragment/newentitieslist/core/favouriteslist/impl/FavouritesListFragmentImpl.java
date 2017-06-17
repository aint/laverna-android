package com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.core.favouriteslist.impl;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl.FavouritesListAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.core.favouriteslist.FavouritesListFragment;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.core.favouriteslist.FavouritesListPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.impl.EntitiesListFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.notecontent.NoteContentFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.util.consts.BundleKeysConst;
import com.github.android.lvrn.lvrnproject.view.util.consts.FragmentConst;

import javax.inject.Inject;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class FavouritesListFragmentImpl extends EntitiesListFragmentImpl<Note, NoteForm> implements FavouritesListFragment {
    @Inject FavouritesListPresenter mFavouritesListPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LavernaApplication.getsAppComponent().inject(this);

        mEntitiesListPresenter = mFavouritesListPresenter;
        mEntitiesRecyclerViewAdapter = new FavouritesListAdapter(this);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void showSelectedNote(Note note) {
        NoteContentFragmentImpl noteContentFragmentImpl = new NoteContentFragmentImpl();

        Bundle bundle = new Bundle();
        bundle.putParcelable(BundleKeysConst.BUNDLE_NOTE_OBJECT_KEY, note);
        noteContentFragmentImpl.setArguments(bundle);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.constraint_container, noteContentFragmentImpl, FragmentConst.TAG_NOTE_CONTENT_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }
}