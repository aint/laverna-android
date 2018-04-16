package com.github.android.lvrn.lvrnproject.persistent.repository.core;

import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.repository.TrashDependedRepository;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;
import com.github.valhallalabs.laverna.persistent.entity.Note;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface NoteRepository extends TrashDependedRepository<Note> {

    /**
     * A method which creates a relation between a note and an tag.
     * @param noteId an id of the note.
     * @param tagId an id of the tag.
     * @return a result of an insertion.
     */
    boolean addTagToNote(@NonNull String noteId, @NonNull String tagId);

    /**
     * A method which destroys a relation between a note and an tag.
     * @param noteId an id of the note.
     * @param tagId an id of the tag.
     * @return a result of a removing.
     */
    boolean removeTagFromNote(@NonNull String noteId, @NonNull String tagId);

    /**
     * A method which retrieves an amount of entities from a start position by a title.
     * @param profileId an id of profile.
     * @param title a required name.
     * @param paginationArgs a limit and a offset of a pagination.
     * @return a list of entities.
     */
    @NonNull
    List<Note> getByTitle(@NonNull String profileId, @NonNull String title, @NonNull PaginationArgs paginationArgs);

    /**
     * A method which retrieves an amount of trash entities from a start position by a title.
     * @param profileId an id of profile.
     * @param title a required name.
     * @param paginationArgs a limit and a offset of a pagination.
     * @return a list of entities.
     */
    @NonNull
    List<Note> getTrashByTitle(@NonNull String profileId, @NonNull String title, @NonNull PaginationArgs paginationArgs);

    /**
     * A method which retrieves an amount of favourite notes from a start position.
     * @param profileId an id of profile.
     * @param paginationArgs a limit and a offset of a pagination.
     * @return a list of entities.
     */
    @NonNull
    List<Note> getFavourites(@NonNull String profileId, @NonNull PaginationArgs paginationArgs);

    /**
     * A method which retrieves an amount of favourite notes from a start position by a title.
     * @param profileId an id of profile.
     * @param title a required name.
     * @param paginationArgs a limit and a offset of a pagination.
     * @return a list of entities.
     */
    @NonNull
    List<Note> getFavouritesByTitle(@NonNull String profileId, @NonNull String title, @NonNull PaginationArgs paginationArgs);

    /**
     * A method which retrieves an amount of entities from a start position by a notebook id.
     * @param notebookId an id of the notebook.
     * @param paginationArgs a limit and a offset of a pagination.
     * @return a list of entities.
     */
    @NonNull
    List<Note> getByNotebook(@NonNull String notebookId, @NonNull PaginationArgs paginationArgs);

    /**
     * A method which retrieves an amount of entities from a start position by a tag id.
     * @param tagId an id of the tag.
     * @param paginationArgs a limit and a offset of a pagination.
     * @return a list of entities.
     */
    @NonNull
    List<Note> getByTag(@NonNull String tagId, @NonNull PaginationArgs paginationArgs);

    boolean changeNoteFavouriteStatus(@NonNull String entityId, boolean status);
}