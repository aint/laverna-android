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

    void save(@NonNull Note note);

    /**
     * A method which retrieves an amount of entities from a start position by a title.
     *
     * @param profileId      an id of a profile.
     * @param title          a required name.
     * @param paginationArgs arguments of pagination such as offset and limit.
     * @return a list of entities.
     */
    @NonNull
    List<Note> getByTitle(@NonNull String profileId, @NonNull String title, @NonNull PaginationArgs paginationArgs);

    /**
     * A method which retrieves an amount of trash entities from a start position by a title
     *
     * @param profileId      an id of a profile.
     * @param title          a required name.
     * @param paginationArgs arguments of pagination such as offset and limit.
     * @return a list of entities.
     */
    @NonNull
    List<Note> getTrashByTitle(@NonNull String profileId, @NonNull String title, @NonNull PaginationArgs paginationArgs);

    /**
     * A method which retrieves an amount of entities from a start position by a notebook id.
     *
     * @param notebookId     an id of the notebook.
     * @param paginationArgs arguments of pagination such as offset and limit.
     * @return a list of entities.
     */
    @NonNull
    List<Note> getByNotebook(@NonNull String notebookId, @NonNull PaginationArgs paginationArgs);

    /**
     * A method which retrieves an amount of favourite entities from a start position.
     *
     * @param profileId      an id of a profile.
     * @param paginationArgs arguments of pagination such as offset and limit.
     * @return a list of entities.
     */
    @NonNull
    List<Note> getFavourites(@NonNull String profileId, @NonNull PaginationArgs paginationArgs);

    /**
     * A method which retrieves an amount of favourite entities from a start position by a title.
     *
     * @param profileId      an id of a profile.
     * @param title          a required name.
     * @param paginationArgs arguments of pagination such as offset and limit.
     * @return a list of entities.
     */
    @NonNull
    List<Note> getFavouritesByTitle(@NonNull String profileId, @NonNull String title, @NonNull PaginationArgs paginationArgs);

    /**
     * A method which retrieves an amount of entities from a start position by a tag id.
     *
     * @param tagId          an id of the tag.
     * @param paginationArgs arguments of pagination such as offset and limit.
     * @return a list of entities.
     */
    @NonNull
    List<Note> getByTag(@NonNull String tagId, @NonNull PaginationArgs paginationArgs);

    boolean setNoteFavourite(String entityId);

    boolean setNoteUnFavourite(String entityId);
}
