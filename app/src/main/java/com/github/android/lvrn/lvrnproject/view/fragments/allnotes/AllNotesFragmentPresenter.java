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

    void subscribeSearchViewForSearch(SearchView searchView);

    void unsubscribeSearchViewForSearch();

    EndlessRecyclerViewScrollListener getEndlessRecyclerViewScrollListener(LinearLayoutManager linearLayoutManager,
                                                                           NotesRecyclerViewAdapter adapter,
                                                                           List<Note> data);

    void bindView(AllNotesFragment allNotesFragment);

    void unBindView();
}
