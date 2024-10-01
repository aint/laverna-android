package com.github.android.lvrn.lvrnproject.persistent.repository.core

import androidx.annotation.NonNull
import com.github.android.lvrn.lvrnproject.persistent.repository.TrashDependedRepository
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.valhallalabs.laverna.persistent.entity.Note

interface NoteRepository : TrashDependedRepository<Note> {

    /**
     * A method which creates a relation between a note and an tag.
     * @param noteId an id of the note.
     * @param tagId an id of the tag.
     * @return a result of an insertion.
     */
    fun addTagToNote(noteId: String, tagId: String): Boolean

    /**
     * A method which destroys a relation between a note and an tag.
     * @param noteId an id of the note.
     * @param tagId an id of the tag.
     * @return a result of a removing.
     */
    fun removeTagFromNote(noteId: String, tagId: String): Boolean

    /**
     * A method which retrieves an amount of entities from a start position by a title.
     * @param profileId an id of profile.
     * @param title a required name.
     * @param paginationArgs a limit and a offset of a pagination.
     * @return a list of entities.
     */
    fun getByTitle(profileId: String, title: String, paginationArgs: PaginationArgs): List<Note>

    /**
     * A method which retrieves an amount of trash entities from a start position by a title.
     * @param profileId an id of profile.
     * @param title a required name.
     * @param paginationArgs a limit and a offset of a pagination.
     * @return a list of entities.
     */
    fun getTrashByTitle(profileId: String, title: String, paginationArgs: PaginationArgs): List<Note>

    /**
     * A method which retrieves an amount of favourite notes from a start position.
     * @param profileId an id of profile.
     * @param paginationArgs a limit and a offset of a pagination.
     * @return a list of entities.
     */
    fun getFavourites(profileId: String, paginationArgs: PaginationArgs): List<Note>

    /**
     * A method which retrieves an amount of favourite notes from a start position by a title.
     * @param profileId an id of profile.
     * @param title a required name.
     * @param paginationArgs a limit and a offset of a pagination.
     * @return a list of entities.
     */
    fun getFavouritesByTitle(profileId: String, title: String, paginationArgs: PaginationArgs): List<Note>

    /**
     * A method which retrieves an amount of entities from a start position by a notebook id.
     * @param notebookId an id of the notebook.
     * @param paginationArgs a limit and a offset of a pagination.
     * @return a list of entities.
     */
    fun getByNotebook(notebookId: String, paginationArgs: PaginationArgs): List<Note>

    /**
     * A method which retrieves an amount of entities from a start position by a tag id.
     * @param tagId an id of the tag.
     * @param paginationArgs a limit and a offset of a pagination.
     * @return a list of entities.
     */
    fun getByTag(tagId: String, paginationArgs: PaginationArgs): List<Note>

    fun changeNoteFavouriteStatus(entityId: String, isFavourite: Boolean): Boolean
}