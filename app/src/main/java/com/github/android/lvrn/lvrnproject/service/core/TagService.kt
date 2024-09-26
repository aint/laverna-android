package com.github.android.lvrn.lvrnproject.service.core

import com.github.android.lvrn.lvrnproject.service.ProfileDependedService
import com.github.android.lvrn.lvrnproject.service.form.TagForm
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.valhallalabs.laverna.persistent.entity.Tag

interface TagService : ProfileDependedService<Tag, TagForm> {

    /**
     * A method which retrieves an amount of entities from a start position by a name.
     * @param name a required name.
     * @param paginationArgs arguments of pagination such as offset and limit.
     * @return a list of entities.
     */
    fun getByName(profileId: String, name: String, paginationArgs: PaginationArgs): List<Tag>

    /**
     * A method which retrieves all entities by a note id.
     * @param noteId an id of note.
     * @return a list of entities.
     */
    fun getByNote(noteId: String): List<Tag>

    fun save(tag: Tag)
}