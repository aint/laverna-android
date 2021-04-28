package com.github.android.lvrn.lvrnproject.persistent.repository.core

import androidx.annotation.NonNull
import com.github.android.lvrn.lvrnproject.persistent.repository.ProfileDependedRepository
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.valhallalabs.laverna.persistent.entity.Tag

interface TagRepository : ProfileDependedRepository<Tag> {

    /**
     * A method which retrieves an amount of entities from a start position by a name.
     * @param profileId an id of profile.
     * @param name a required name.
     * @param paginationArgs a limit and a offset of a pagination.
     * @return a list of entities.
     */
    fun getByName(@NonNull profileId: String, @NonNull name: String, @NonNull paginationArgs: PaginationArgs): MutableList<Tag>

    /**
     * A method which retrieves all entities by a note id.
     * @param noteId an id of note.
     * @return a list of entities.
     */
    fun getByNote(noteId: String): MutableList<Tag>
}