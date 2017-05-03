package com.github.android.lvrn.lvrnproject.service.extension;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.persistent.entity.Tag;
import com.github.android.lvrn.lvrnproject.service.ProfileDependedService;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface NoteService extends ProfileDependedService<Note> {

    /**
     * @param profileId
     * @param notebookId
     * @param title
     * @param content
     * @param isFavorite
     * @throws IllegalArgumentException
     */
    void create(String profileId,
                String notebookId,
                String title,
                String content,
                boolean isFavorite);

    List<Note> getByTitle(String title, int from, int amount);

    List<Note> getByNotebook(Notebook notebook, int from, int amount);

    List<Note> getByTag(Tag tag, int from, int amount);
}
