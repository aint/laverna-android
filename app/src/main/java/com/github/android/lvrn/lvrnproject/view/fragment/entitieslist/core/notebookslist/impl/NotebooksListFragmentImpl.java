package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.lvrn.lvrnproject.LavernaApplication;
import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.service.form.NotebookForm;
import com.github.android.lvrn.lvrnproject.view.adapter.datapostset.impl.NotebooksListAdapter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist.NotebooksListFragment;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.notebookslist.NotebooksListPresenter;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.impl.EntitiesListFragmentImpl;
import com.github.android.lvrn.lvrnproject.view.viewholder.NotebookViewHolder;

import javax.inject.Inject;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public class NotebooksListFragmentImpl extends EntitiesListFragmentImpl<Notebook, NotebookForm, NotebookViewHolder> implements NotebooksListFragment {
    public static final String TOOLBAR_TITLE = "Notebooks";

    @Inject NotebooksListPresenter mNotebooksListPresenter;

    public NotebooksListFragmentImpl() {
        super(TOOLBAR_TITLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LavernaApplication.getsAppComponent().inject(this);
        linkPresenterAndAdapter(mNotebooksListPresenter, new NotebooksListAdapter(this));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void openNotebook(Notebook notebook) {
        //TODO: make interface for it.
//        NotebookChildrenFragmentImpl notebookChildrenFragment = new NotebookChildrenFragmentImpl();
//
//        Bundle bundle = new Bundle();
//        bundle.putParcelable(BundleKeysConst.BUNDLE_NOTEBOOK_OBJECT_KEY, notebook);
//        notebookChildrenFragment.setArguments(bundle);
//
//        getActivity().getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.constraint_container, notebookChildrenFragment, FragmentConst.TAG_NOTEBOOK_CHILDREN_FRAGMENT)
//                .addToBackStack(null)
//                .commit();
    }

}
