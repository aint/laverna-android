package com.github.android.lvrn.lvrnproject.service.extension;

import android.support.annotation.NonNull;
import android.support.annotation.Size;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.service.ProfileDependedService;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface NoteService extends ProfileDependedService<Note, NoteForm> {

    /**
     * A method which retrieves an amount of entities from a start position by a title.
     * @param title a required name.
     * @param from a start position.
     * @param amount a number of entities to retrieve.
     * @return a list of entities.
     */
    @NonNull
    List<Note> getByTitle(@NonNull String title, @Size(min = 1) int from, @Size (min = 2) int amount);

    /**
     * A method which retrieves an amount of entities from a start position by a notebook id.
     * @param notebookId an id of the notebook.
     * @param from a start position
     * @param amount a number of entities to retrieve.
     * @return a list of entities.
     */
    @NonNull
    List<Note> getByNotebook(@NonNull String notebookId, @Size(min = 1) int from, @Size (min = 2) int amount);

    /**
     * A method which retrieves an amount of entities from a start position by a tag id.
     * @param tagId an id of the tag.
     * @param from a start position
     * @param amount a number of entities to retrieve.
     * @return a list of entities.
     */
    @NonNull
    List<Note> getByTag(@NonNull String tagId, @Size(min = 1) int from, @Size(min = 2) int amount);
}
