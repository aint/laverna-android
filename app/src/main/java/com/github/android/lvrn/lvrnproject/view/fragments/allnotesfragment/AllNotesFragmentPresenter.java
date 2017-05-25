package com.github.android.lvrn.lvrnproject.view.fragments.allnotesfragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.view.adapters.AllNotesFragmentRecyclerViewAdapter;
import com.github.android.lvrn.lvrnproject.view.adapters.EndlessRecyclerViewScrollListener;

import java.util.List;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public interface AllNotesFragmentPresenter {

    int numberEntitiesDownloadItem = 7;

    void subscribeSearchViewForSearch(SearchView searchView);

    void unSubscribeSearchViewForSearch();

    EndlessRecyclerViewScrollListener subscribeEndlessRecyclerViewScrollListener(LinearLayoutManager linearLayoutManager,
                                                                                 AllNotesFragmentRecyclerViewAdapter adapter,
                                                                                 List<Note> data);

    void bindView(AllNotesFragment allNotesFragment);

    void unBindView();
}
