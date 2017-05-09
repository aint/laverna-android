package com.github.android.lvrn.lvrnproject.view.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.android.lvrn.lvrnproject.R;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public class TagEditingDialogFragment extends DialogFragment {

   public static TagEditingDialogFragment newInstance(){
        TagEditingDialogFragment tagEditingDialogFragment = new TagEditingDialogFragment();
        return tagEditingDialogFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
//        int width = 1000;
//        int height = 1000;
//        getDialog().getWindow().setLayout(width,height);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_tag_editing,container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
