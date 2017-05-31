package com.github.android.lvrn.lvrnproject.view.fragment.trash;

import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;

import java.util.List;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public interface TrashPresenter {

    /**
     * A method which binds a view to a presenter.
     */
    void bindView(TrashFragment allNotesFragment);

    /**
     * A method which unbinds a view to a presenter.
     */
    void unbindView();

    void subscribeRecyclerViewForPagination(RecyclerView recyclerView);

    void subscribeSearchView(MenuItem searchitem);

    void disposePaginationAndSearch();

    List<Note> getNotesForAdapter();

    void removeNoteForever();

    void restoreNote();
}
