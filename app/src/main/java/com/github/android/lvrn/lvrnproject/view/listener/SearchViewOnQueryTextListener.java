package com.github.android.lvrn.lvrnproject.view.listener;

import android.support.v7.widget.SearchView;

import io.reactivex.subjects.ReplaySubject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class SearchViewOnQueryTextListener implements SearchView.OnQueryTextListener {

    private ReplaySubject<String> mQueryReplaySubject;

    public SearchViewOnQueryTextListener(ReplaySubject<String> queryReplaySubject) {
        mQueryReplaySubject = queryReplaySubject;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mQueryReplaySubject.onNext(newText);
        return true;
    }
}
