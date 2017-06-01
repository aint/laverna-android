package com.github.android.lvrn.lvrnproject.service.core;

import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.service.TrashDependedService;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface NoteService extends TrashDependedService<Note, NoteForm> {

    /**
     * A method which retrieves an amount of entities from a start position by a title.
     * @param title a required name.
     * @param offset a start position.
     * @param limit a number of entities to retrieve.
     * @return a list of entities.
     */
    @NonNull
    List<Note> getByTitle(@NonNull String profileId, @NonNull String title, @NonNull PaginationArgs paginationArgs);

    @NonNull
    List<Note> getTrashByTitle(@NonNull String profileId, @NonNull String title, @NonNull PaginationArgs paginationArgs);

    /**
     * A method which retrieves an amount of entities from a start position by a notebook id.
     * @param notebookId an id of the notebook.
     * @param offset a start position
     * @param limit a number of entities to retrieve.
     * @return a list of entities.
     */
    @NonNull
    List<Note> getByNotebook(@NonNull String notebookId, @NonNull PaginationArgs paginationArgs);

    @NonNull
    List<Note> getFavourites(@NonNull String profileId, @NonNull PaginationArgs paginationArgs);

    @NonNull
    List<Note> getFavouritesByTitle(@NonNull String profileId, @NonNull String title, @NonNull PaginationArgs paginationArgs);


    /**
     * A method which retrieves an amount of entities from a start position by a tag id.
     * @param tagId an id of the tag.
     * @param offset a start position
     * @param limit a number of entities to retrieve.
     * @return a list of entities.
     */
    @NonNull
    List<Note> getByTag(@NonNull String tagId, @NonNull PaginationArgs paginationArgs);
}
