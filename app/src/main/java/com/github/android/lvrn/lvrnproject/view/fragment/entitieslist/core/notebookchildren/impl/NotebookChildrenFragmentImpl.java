package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.impl;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.databinding.FragmentNotebookContentBinding;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl.ChildNotebooksAdapter;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl.ChildNotesAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.NotebookChildrenFragment;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.NotebookChildrenPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.notecontent.NoteContentFragment;
import com.github.valhallalabs.laverna.persistent.entity.Note;
import com.github.valhallalabs.laverna.persistent.entity.Notebook;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import static com.github.android.lvrn.lvrnproject.view.util.consts.BundleKeysConstKt.BUNDLE_NOTEBOOK_OBJECT_KEY;
import static com.github.android.lvrn.lvrnproject.view.util.consts.BundleKeysConstKt.BUNDLE_NOTE_OBJECT_KEY;
import static com.github.android.lvrn.lvrnproject.view.util.consts.FragmentConstKt.TAG_NOTEBOOK_CHILDREN_FRAGMENT;
import static com.github.android.lvrn.lvrnproject.view.util.consts.FragmentConstKt.TAG_NOTE_CONTENT_FRAGMENT;


/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class NotebookChildrenFragmentImpl extends Fragment implements NotebookChildrenFragment {

    @Inject
    NotebookChildrenPresenter mNotebookChildrenPresenter;
    private ChildNotesAdapter mChildNotesAdapter;
    private ChildNotebooksAdapter mChildNotebooksAdapter;
    private FragmentNotebookContentBinding mFragmentNotebookContentBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentNotebookContentBinding = FragmentNotebookContentBinding.inflate(inflater, container, false);
        LavernaApplication.getsAppComponent().inject(this);
        initListPresenters();
        initRecyclerViews();
        return mFragmentNotebookContentBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mNotebookChildrenPresenter != null) {
            mNotebookChildrenPresenter.getNotebooksListPresenter().bindView(this);
            mNotebookChildrenPresenter.getNotesListPresenter().bindView(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mNotebookChildrenPresenter.getNotebooksListPresenter().unbindView();
        mNotebookChildrenPresenter.getNotesListPresenter().unbindView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mNotebookChildrenPresenter.getNotebooksListPresenter().disposePagination();
        mNotebookChildrenPresenter.getNotesListPresenter().disposePagination();
    }

    private void initListPresenters() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mNotebookChildrenPresenter
                    .initializeListsPresenters(bundle.getParcelable(BUNDLE_NOTEBOOK_OBJECT_KEY));
        }
    }

    /**
     * A method which initializes recycler view
     */
    private void initRecyclerViews() {
        initNotesRecyclerView();
        initNotebooksRecyclerView();
    }

    private void initNotesRecyclerView() {
        RecyclerView recyclerViewNotes = mFragmentNotebookContentBinding.recyclerViewNotes;
        recyclerViewNotes.setHasFixedSize(true);

        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(getContext()));

        mChildNotesAdapter = new ChildNotesAdapter(this);
        mNotebookChildrenPresenter.getNotesListPresenter().setDataToAdapter(mChildNotesAdapter);
        recyclerViewNotes.setAdapter(mChildNotesAdapter);

        mNotebookChildrenPresenter.getNotesListPresenter().subscribeRecyclerViewForPagination(recyclerViewNotes);
    }

    private void initNotebooksRecyclerView() {
        RecyclerView recyclerViewNotebooks = mFragmentNotebookContentBinding.recyclerViewNotebooks;
        recyclerViewNotebooks.setHasFixedSize(true);

        recyclerViewNotebooks.setLayoutManager(new LinearLayoutManager(getContext()));

        mChildNotebooksAdapter = new ChildNotebooksAdapter(this);
        mNotebookChildrenPresenter.getNotebooksListPresenter().setDataToAdapter(mChildNotebooksAdapter);
        recyclerViewNotebooks.setAdapter(mChildNotebooksAdapter);

        mNotebookChildrenPresenter.getNotebooksListPresenter().subscribeRecyclerViewForPagination(recyclerViewNotebooks);
    }

    @Override
    public void updateRecyclerView() {
        mChildNotesAdapter.notifyDataSetChanged();
        mChildNotebooksAdapter.notifyDataSetChanged();
        Logger.d("Recycler view is updated");
    }

    @Override
    public void showSelectedNote(Note note) {
        NoteContentFragment noteContentFragment = new NoteContentFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_NOTE_OBJECT_KEY, note);
        noteContentFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.constraint_container, noteContentFragment, TAG_NOTE_CONTENT_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void openNotebook(Notebook notebook) {
        NotebookChildrenFragmentImpl notebookChildrenFragment = new NotebookChildrenFragmentImpl();

        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_NOTEBOOK_OBJECT_KEY, notebook);
        notebookChildrenFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.constraint_container, notebookChildrenFragment, TAG_NOTEBOOK_CHILDREN_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }
}
