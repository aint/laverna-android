package com.github.android.lvrn.lvrnproject.view.fragments;



import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;


import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.view.activities.mainactivity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @author Andrii Bei <psihey1@gmail.com>

 */

public class SingleNoteFragment extends Fragment  {
     @BindView(R.id.im_btn_information) ImageButton mImageButtonInfo;
     @BindView(R.id.im_btn_arrow_back_single_note) ImageButton mImageButtonBackArrow;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_single_note,container,false);
        ButterKnife.bind(this,rootView);
        reInitBaseView();
        return rootView;
    }

    private void reInitBaseView() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        FloatingActionButton fa =(FloatingActionButton)(( getActivity()).findViewById(R.id.fab));
        fa.show();
        fa.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_create_white_24dp));
        fa.setOnClickListener(v -> Toast.makeText(getContext(),"Fragment №2",Toast.LENGTH_SHORT).show());
    }

    @OnClick(R.id.im_btn_information)
    public void startNoteDetailFragment() {
        NoteDetailsFragment noteDetailsFragment = new NoteDetailsFragment();
        openSelectFragment(noteDetailsFragment);
    }

    @OnClick(R.id.im_btn_arrow_back_single_note)
    public void backToPreviousFragment(){
        getActivity().onBackPressed();
    }

    public void openSelectFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Slide slideTransition = new Slide(Gravity.RIGHT);
            slideTransition.setDuration(1000);
            ChangeBounds changeBounds = new ChangeBounds();
            changeBounds.setDuration(1000);
            fragment.setEnterTransition(slideTransition);
            fragment.setReturnTransition(null);
            fragment.setAllowEnterTransitionOverlap(true);
            fragment.setSharedElementEnterTransition(changeBounds);
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.constraint_container,fragment)
                    .addSharedElement(mImageButtonInfo,mImageButtonInfo.getTransitionName())
                    .addToBackStack(null)
                    .commit();
        } else
            fragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right)
                    .replace(R.id.constraint_container,fragment)
                    .addToBackStack(null)
                    .commit();
    }

}
