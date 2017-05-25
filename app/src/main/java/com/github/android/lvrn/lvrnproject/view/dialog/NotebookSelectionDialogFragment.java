package com.github.android.lvrn.lvrnproject.view.dialog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.lvrn.lvrnproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */
//TODO: implement later, when getParents method will be done.
public class NotebookSelectionDialogFragment extends Fragment {

    @BindView(R.id.recycler_view_notebook_selection) RecyclerView mNotebookSelectionRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_notebooks_list, container, false);
        ButterKnife.bind(this, view);
        mNotebookSelectionRecyclerView.setHasFixedSize(true);
        mNotebookSelectionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}
