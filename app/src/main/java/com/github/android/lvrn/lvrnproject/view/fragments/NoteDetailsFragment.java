package com.github.android.lvrn.lvrnproject.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.github.android.lvrn.lvrnproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class NoteDetailsFragment extends Fragment {
    @BindView(R.id.im_btn_arrow_back) ImageButton mImageButtonBack;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_note_details,container,false);
        ButterKnife.bind(this,rootView);
        reInitBaseView();
        return rootView;
    }

    private void reInitBaseView() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        FloatingActionButton fa =(FloatingActionButton)(( getActivity()).findViewById(R.id.fab));
        fa.hide();
    }

    @OnClick(R.id.im_btn_arrow_back)
    public void backToPreviousFragment(){
        getActivity().onBackPressed();
    }

}
