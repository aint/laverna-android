package com.github.android.lvrn.lvrnproject.service;

import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Notebook;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Profile;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface NotesService extends ProfileDependedService<Note> {

    void create(Profile profile,
                Notebook notebook,
                String title,
                String content,
                boolean isFavorite);

}
