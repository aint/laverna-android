package com.github.android.lvrn.lvrnproject.view.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;


import com.github.android.lvrn.lvrnproject.R;
import com.github.android.lvrn.lvrnproject.view.activities.mainactivity.MainActivity;


/**
 * @author Andrii Bei <psihey1@gmail.com>

 */

public class SingleNoteFragment extends Fragment implements OnClickListener {
    private ImageButton mImageButtonInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_single_note,container,false);
        mImageButtonInfo = (ImageButton)rootView.findViewById(R.id.im_btn_information);
        mImageButtonInfo.setOnClickListener(this);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(R.id.im_btn_information == id){
            NoteDetailsFragment noteDetailsFragment = new NoteDetailsFragment();
            openSelectFragment(noteDetailsFragment);
        }
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
