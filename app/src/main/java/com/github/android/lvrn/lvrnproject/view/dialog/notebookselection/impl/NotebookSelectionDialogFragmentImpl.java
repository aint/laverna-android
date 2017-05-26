package com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.impl;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.service.core.NotebookService;
import com.github.android.lvrn.lvrnproject.view.adapters.NotebookSelectionRecyclerViewAdapter;
import com.github.android.lvrn.lvrnproject.view.dialog.notebookselection.NotebookSelectionDialogFragment;
import com.github.android.lvrn.lvrnproject.view.listeners.RecyclerViewOnScrollListener;
import com.github.android.lvrn.lvrnproject.view.listeners.RecyclerViewOnScrollListener.PaginationParams;
import com.github.android.lvrn.lvrnproject.view.util.CurrentState;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Emitter;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NotebookSelectionDialogFragmentImpl extends DialogFragment implements NotebookSelectionDialogFragment {

    @Inject NotebookService mNotebookService;

    @BindView(R.id.recycler_view_notebook_selection) RecyclerView mNotebookSelectionRecyclerView;

//    private NotebookSelectionPresenter mNotebookSelectionPresenter;

    private NotebookSelectionRecyclerViewAdapter mNotebookSelectionRecyclerViewAdapter;

    private Emitter<PaginationParams> mPaginationParamsEmitter;

    private Disposable mPaginationDisposable;

    private List<Notebook> mNotebooks;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(mNotebookSelectionPresenter == null) {
//            mNotebookSelectionPresenter = new NotebookSelectionPresenterImpl(mNotebookService);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_notebooks_list, container, false);
        ButterKnife.bind(this, view);
        LavernaApplication.getsAppComponent().inject(this);


        mNotebookService.openConnection();


        mNotebookSelectionRecyclerView.setHasFixedSize(true);

        mNotebookSelectionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        initPaginationDisposable();
        mNotebookSelectionRecyclerView.addOnScrollListener(
                new RecyclerViewOnScrollListener(mPaginationParamsEmitter));

        mNotebooks = mNotebookService.getByProfile(CurrentState.profileId, 1, 15);
        System.out.println(mNotebooks);
        mNotebookSelectionRecyclerViewAdapter = new NotebookSelectionRecyclerViewAdapter(mNotebooks);





        mNotebookSelectionRecyclerView.setAdapter(mNotebookSelectionRecyclerViewAdapter);

        return view;
    }

    @Override
    public void onResume() {
//        mNotebookSelectionPresenter.bindView(this);
        super.onResume();
    }


    private void initPaginationDisposable() {
        mPaginationDisposable = Flowable
                .<PaginationParams>create(emitter -> mPaginationParamsEmitter = emitter, BackpressureStrategy.BUFFER)
                .map(this::loadMoreNotebooks)
                .filter(this::isNotebooksListNotEmpty)
                .map(newNotebooks -> mNotebooks.addAll(newNotebooks))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> updateRecyclerView(),
                        throwable -> {/*TODO: find out what can happen here*/});
    }

    private List<Notebook> loadMoreNotebooks(PaginationParams params) {
        List<Notebook> notebooks = mNotebookService.getByProfile(CurrentState.profileId, params.offset, params.limit);
        System.out.println(notebooks);
        return notebooks;
    }

    private boolean isNotebooksListNotEmpty(List<Notebook> notebooks) {
        return !notebooks.isEmpty();
    }

    @Override
    public void updateRecyclerView() {
        mNotebookSelectionRecyclerViewAdapter.notifyDataSetChanged();
        System.out.println("UPDATE RV");
    }

    @Override
    public void onStop() {
        mNotebookService.closeConnection();
        if (mPaginationDisposable != null && mPaginationDisposable.isDisposed()) {
            mPaginationDisposable.dispose();
        }
        super.onStop();
    }
}
