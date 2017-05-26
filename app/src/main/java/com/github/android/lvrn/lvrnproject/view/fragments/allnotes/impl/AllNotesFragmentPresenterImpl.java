package com.github.android.lvrn.lvrnproject.view.fragments.allnotes.impl;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.service.core.NoteService;
import com.github.android.lvrn.lvrnproject.view.adapters.NotesRecyclerViewAdapter;
import com.github.android.lvrn.lvrnproject.view.adapters.EndlessRecyclerViewScrollListener;
import com.github.android.lvrn.lvrnproject.view.fragments.allnotes.AllNotesFragment;
import com.github.android.lvrn.lvrnproject.view.fragments.allnotes.AllNotesFragmentPresenter;
import com.github.android.lvrn.lvrnproject.view.util.CurrentState;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class AllNotesFragmentPresenterImpl implements AllNotesFragmentPresenter {
    NoteService mNoteService;
    private AllNotesFragment mAllNotesFragment;
    private Disposable mDisposable;

    public AllNotesFragmentPresenterImpl(NoteService mNoteService) {
        this.mNoteService = mNoteService;
    }

    @Override
    public void subscribeSearchView(SearchView searchView) {
        mDisposable = RxSearch.fromSearchView(searchView)
                .debounce(400, TimeUnit.MILLISECONDS)
                .filter(word -> word.length() > 2)
                .map(title -> mNoteService.getByTitle(CurrentState.profileId, title, 1, 10))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(foundItems -> {
                    mAllNotesFragment.setDataInList(foundItems);
                });
    }

    @Override
    public void unsubscribeSearchView() {
        if (!mDisposable.isDisposed())
            mDisposable.dispose();
    }

    @Override
    public EndlessRecyclerViewScrollListener getEndlessRecyclerViewScrollListener(LinearLayoutManager linearLayoutManager,
                                                                                  NotesRecyclerViewAdapter adapter,
                                                                                  List<Note> data) {
        EndlessRecyclerViewScrollListener mScrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                data.addAll(mNoteService.getByProfile(CurrentState.profileId, totalItemsCount + 1, numberEntitiesDownloadItem));
                view.post(() -> {
                    adapter.notifyItemRangeInserted(adapter.getItemCount(), data.size() - 1);
                });
            }
        };
        return mScrollListener;
    }

    @Override
    public void bindView(AllNotesFragment allNotesFragment) {
        mAllNotesFragment = allNotesFragment;
    }

    @Override
    public void unBindView() {
        mAllNotesFragment = null;
    }

    private static class RxSearch {
        static Observable<String> fromSearchView(@NonNull final SearchView searchView) {
            final BehaviorSubject<String> subject = BehaviorSubject.create();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (!newText.isEmpty()) {
                        subject.onNext(newText);
                    }
                    return true;
                }
            });
            return subject;
        }
    }
}
