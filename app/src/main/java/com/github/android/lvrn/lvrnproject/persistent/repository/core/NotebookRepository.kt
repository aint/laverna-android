package com.github.android.lvrn.lvrnproject.persistent.repository.core

import androidx.annotation.NonNull
import com.github.android.lvrn.lvrnproject.persistent.repository.ProfileDependedRepository
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.valhallalabs.laverna.persistent.entity.Notebook

interface NotebookRepository : ProfileDependedRepository<Notebook> {

    /**
     * A method which retrieves an amount of entities from a start position by a name.
     * @param profileId an id of profile.
     * @param name a required name.
     * @param paginationArgs a limit and a offset of a pagination.
     * @return a list of entities.
     */
    fun getByName(@NonNull profileId: String, @NonNull name: String, @NonNull paginationArgs: PaginationArgs)
            : MutableList<Notebook>

    /**
     * A method which retrieves an amount of entities from a start position by a parent notebook's id.
     * @param notebookId an id of a parent notebook.
     * @param paginationArgs a limit and a offset of a pagination.
     * @return a list of entities.
     */
    fun getChildren(@NonNull notebookId: String, @NonNull paginationArgs: PaginationArgs): MutableList<Notebook>

    /**
     * A method which retrieves an amount of entities from a start position which are root parents (has no parent).
     * @param profileId an id of profile.
     * @param paginationArgs a limit and a offset of a pagination.
     * @return a list of entities.
     */
    fun getRootParents(@NonNull profileId: String, @NonNull paginationArgs: PaginationArgs): MutableList<Notebook>

}