package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.noteslist.core.favouritenotes;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListPresenter;
import com.github.android.lvrn.lvrnproject.view.viewholder.FavouriteViewHolder;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface FavouriteNotesPresenter extends EntitiesListPresenter<Note, NoteForm, FavouriteViewHolder> {
}
