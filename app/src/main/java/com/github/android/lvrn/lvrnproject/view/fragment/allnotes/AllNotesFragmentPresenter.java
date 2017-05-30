package com.github.android.lvrn.lvrnproject.view.fragment.allnotes;

import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;

import java.util.List;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public interface AllNotesFragmentPresenter {

    /**
     * A method which binds a view to a presenter.
     */
    void bindView(AllNotesFragment allNotesFragment);

    /**
     * A method which unbinds a view to a presenter.
     */
    void unbindView();

    void subscribeRecyclerViewForPagination(RecyclerView recyclerView);

    /**
     * A method which subscribe search view to hears when user input anything with defined
     * option and send query to DB
     * @param searchView a search view to subscribe.
     */
    void subscribeSearchView(MenuItem searchitem);

    void disposePaginationAndSearch();

    List<Note> getNotesForAdapter();
}
