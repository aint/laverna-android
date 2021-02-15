package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.impl;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.R;
import com.github.valhallalabs.laverna.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl.ChildNotebooksAdapter;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl.ChildNotesAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.NotebookChildrenFragment;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookchildren.NotebookChildrenPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.notecontent.NoteContentFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.util.consts.BundleKeysConst;
import com.github.android.lvrn.lvrnproject.view.util.consts.FragmentConst;
import com.github.valhallalabs.laverna.persistent.entity.Note;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.github.android.lvrn.lvrnproject.view.util.consts.BundleKeysConst.BUNDLE_NOTEBOOK_OBJECT_KEY;


/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class NotebookChildrenFragmentImpl extends Fragment implements NotebookChildrenFragment {
    @BindView(R.id.recycler_view_notebooks) RecyclerView mNotebooksRecyclerView;

    @BindView(R.id.recycler_view_notes) RecyclerView mNotesRecyclerView;

    @Inject NotebookChildrenPresenter mNotebookChildrenPresenter;

    private Unbinder mUnbinder;

    private ChildNotesAdapter mChildNotesAdapter;

    private ChildNotebooksAdapter mChildNotebooksAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notebook_content, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        LavernaApplication.getsAppComponent().inject(this);
        initListPresenters();
        initRecyclerViews();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mNotebookChildrenPresenter != null) {
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
        if(mUnbinder != null){
            mUnbinder.unbind();
        }
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
        mNotesRecyclerView.setHasFixedSize(true);

        mNotesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mChildNotesAdapter = new ChildNotesAdapter(this);
        mNotebookChildrenPresenter.getNotesListPresenter().setDataToAdapter(mChildNotesAdapter);
        mNotesRecyclerView.setAdapter(mChildNotesAdapter);

        mNotebookChildrenPresenter.getNotesListPresenter().subscribeRecyclerViewForPagination(mNotesRecyclerView);
    }

    private void initNotebooksRecyclerView() {
        mNotebooksRecyclerView.setHasFixedSize(true);

        mNotebooksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mChildNotebooksAdapter = new ChildNotebooksAdapter(this);
        mNotebookChildrenPresenter.getNotebooksListPresenter().setDataToAdapter(mChildNotebooksAdapter);
        mNotebooksRecyclerView.setAdapter(mChildNotebooksAdapter);

        mNotebookChildrenPresenter.getNotebooksListPresenter().subscribeRecyclerViewForPagination(mNotebooksRecyclerView);
    }

    @Override
    public void updateRecyclerView() {
        mChildNotesAdapter.notifyDataSetChanged();
        mChildNotebooksAdapter.notifyDataSetChanged();
        Logger.d("Recycler view is updated");
    }

    @Override
    public void showSelectedNote(Note note) {
        NoteContentFragmentImpl noteContentFragment = new NoteContentFragmentImpl();

        Bundle bundle = new Bundle();
        bundle.putParcelable(BundleKeysConst.BUNDLE_NOTE_OBJECT_KEY, note);
        noteContentFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.constraint_container, noteContentFragment, FragmentConst.TAG_NOTE_CONTENT_FRAGMENT)
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
                .replace(R.id.constraint_container, notebookChildrenFragment, FragmentConst.TAG_NOTEBOOK_CHILDREN_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }
}
