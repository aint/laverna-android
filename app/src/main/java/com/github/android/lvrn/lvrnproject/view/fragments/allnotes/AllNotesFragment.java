package com.github.android.lvrn.lvrnproject.view.fragments.allnotes;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;

import java.util.List;

/**
 * @author Andrii Bei <psihey1@gmail.com>
 */

public interface AllNotesFragment {

    /**
     * A method which sets data to current recycler view adapter
     * @param data an data what set to the adapter
     */
    void setDataInList(List<Note> data);

}
