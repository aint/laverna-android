package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.impl;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView.ViewHolder;

import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.NotesListFragment;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.impl.EntitiesListFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.fragment.notecontent.NoteContentFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.util.consts.BundleKeysConst;
import com.github.android.lvrn.lvrnproject.view.util.consts.FragmentConst;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public abstract class NotesListFragmentImpl<T3 extends ViewHolder> extends EntitiesListFragmentImpl<Note, NoteForm, T3> implements NotesListFragment {

    public NotesListFragmentImpl(String TOOLBAR_TITLE) {
        super(TOOLBAR_TITLE);
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
