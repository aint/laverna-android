package com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.core.trashlist.impl;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl.TrashListAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.core.trashlist.TrashListFragment;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.core.trashlist.TrashListPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.impl.EntitiesListFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.notecontent.NoteContentFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.util.consts.BundleKeysConst;
import com.github.android.lvrn.lvrnproject.view.util.consts.FragmentConst;

import javax.inject.Inject;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class TrashListFragmentImpl extends EntitiesListFragmentImpl<Note, NoteForm> implements TrashListFragment {

    @Inject TrashListPresenter mTrashListPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LavernaApplication.getsAppComponent().inject(this);

        mEntitiesListPresenter = mTrashListPresenter;
        mEntitiesRecyclerViewAdapter = new TrashListAdapter(this);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

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