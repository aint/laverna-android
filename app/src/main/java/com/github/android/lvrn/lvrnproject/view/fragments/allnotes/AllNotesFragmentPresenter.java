package com.github.android.lvrn.lvrnproject.view.fragments.allnotes;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.view.adapters.NotesRecyclerViewAdapter;
import com.github.android.lvrn.lvrnproject.view.adapters.EndlessRecyclerViewScrollListener;

import java.util.List;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public interface AllNotesFragmentPresenter {

    int numberEntitiesDownloadItem = 7;

    /**
     * A method which subscribe search view to DB for search user input data
     * @param searchView a search view to subscribe.
     */
    void subscribeSearchView(SearchView searchView);

    /**
     * A method which unsubscribe search view to DB
     */
    void unsubscribeSearchView();

    /**
     * A method which implement lazy loading to our list of data
     * @param linearLayoutManager a layout manager of current recycler view
     * @param adapter an adapter of current recycler view
     * @param data a data of current recycler view adapter
     * @return
     */
    EndlessRecyclerViewScrollListener getEndlessRecyclerViewScrollListener(LinearLayoutManager linearLayoutManager,
                                                                           NotesRecyclerViewAdapter adapter,
                                                                           List<Note> data);

    /**
     * A method which binds a view to a presenter.
     */
    void bindView(AllNotesFragment allNotesFragment);

    /**
     * A method which unbinds a view to a presenter.
     */
    void unBindView();
}
