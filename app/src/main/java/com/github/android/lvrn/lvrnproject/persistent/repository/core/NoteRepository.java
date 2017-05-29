package com.github.android.lvrn.lvrnproject.persistent.repository.core;

import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.persistent.repository.TrashDependedRepository;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;

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
     * @param title a required name.
     * @param offset a start position.
     * @param limit a number of entities to retrieve.
     * @return a list of entities.
     */
    @NonNull
    List<Note> getByTitle(@NonNull String profileId, @NonNull String title, boolean isTrash, @NonNull PaginationArgs paginationArgs);

    /**
     * A method which retrieves an amount of entities from a start position by a notebook id.
     * @param notebookId an id of the notebook.
     * @param offset a start position
     * @param limit a number of entities to retrieve.
     * @return a list of entities.
     */
    @NonNull
    List<Note> getByNotebook(@NonNull String notebookId, boolean isTrash, @NonNull PaginationArgs paginationArgs);

    /**
     * A method which retrieves an amount of entities from a start position by a tag id.
     * @param tagId an id of the tag.
     * @param offset a start position
     * @param limit a number of entities to retrieve.
     * @return a list of entities.
     */
    @NonNull
    List<Note> getByTag(@NonNull String tagId, boolean isTrash, @NonNull PaginationArgs paginationArgs);
}