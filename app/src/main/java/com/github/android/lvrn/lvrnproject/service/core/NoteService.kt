package com.github.android.lvrn.lvrnproject.service.core

import com.github.android.lvrn.lvrnproject.service.TrashDependedService
import com.github.android.lvrn.lvrnproject.service.form.NoteForm
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.valhallalabs.laverna.persistent.entity.Note

interface NoteService : TrashDependedService<Note, NoteForm> {


    fun save(note: Note)

    /**
     * A method which retrieves an amount of entities from a start position by a title.
     *
     * @param profileId      an id of a profile.
     * @param title          a required name.
     * @param paginationArgs arguments of pagination such as offset and limit.
     * @return a list of entities.
     */
    fun getByTitle(profileId: String, title: String, paginationArgs: PaginationArgs): List<Note>

    /**
     * A method which retrieves an amount of trash entities from a start position by a title
     *
     * @param profileId      an id of a profile.
     * @param title          a required name.
     * @param paginationArgs arguments of pagination such as offset and limit.
     * @return a list of entities.
     */
    fun getTrashByTitle(
        profileId: String,
        title: String,
        paginationArgs: PaginationArgs,
    ): List<Note>

    /**
     * A method which retrieves an amount of entities from a start position by a notebook id.
     *
     * @param notebookId     an id of the notebook.
     * @param paginationArgs arguments of pagination such as offset and limit.
     * @return a list of entities.
     */
    fun getByNotebook(notebookId: String, paginationArgs: PaginationArgs): List<Note>

    /**
     * A method which retrieves an amount of favourite entities from a start position.
     *
     * @param profileId      an id of a profile.
     * @param paginationArgs arguments of pagination such as offset and limit.
     * @return a list of entities.
     */
    fun getFavourites(profileId: String, paginationArgs: PaginationArgs): List<Note>

    /**
     * A method which retrieves an amount of favourite entities from a start position by a title.
     *
     * @param profileId      an id of a profile.
     * @param title          a required name.
     * @param paginationArgs arguments of pagination such as offset and limit.
     * @return a list of entities.
     */
    fun getFavouritesByTitle(
        profileId: String,
        title: String,
        paginationArgs: PaginationArgs,
    ): List<Note>

    /**
     * A method which retrieves an amount of entities from a start position by a tag id.
     *
     * @param tagId          an id of the tag.
     * @param paginationArgs arguments of pagination such as offset and limit.
     * @return a list of entities.
     */
    fun getByTag(tagId: String, paginationArgs: PaginationArgs): List<Note>

    fun setNoteFavourite(entityId: String): Boolean

    fun setNoteUnFavourite(entityId: String): Boolean
}