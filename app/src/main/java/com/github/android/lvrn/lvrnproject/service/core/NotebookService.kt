package com.github.android.lvrn.lvrnproject.service.core

import com.github.android.lvrn.lvrnproject.service.ProfileDependedService
import com.github.android.lvrn.lvrnproject.service.form.NotebookForm
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.valhallalabs.laverna.persistent.entity.Notebook

interface NotebookService : ProfileDependedService<Notebook, NotebookForm> {

    fun save(notebook: Notebook)

    /**
     * A method which retrieves an amount of entities from a start position by a name.
     * @param profileId an id of a profile.
     * @param name a required name.
     * @param paginationArgs arguments of pagination such as offset and limit.
     * @return a list of entities.
     */
    fun getByName(profileId: String, name: String, paginationArgs: PaginationArgs): List<Notebook>

    /**
     * A method which retrieves an amount of entities from a start position by a parent notebook's id.
     * @param notebookId an id of a parent notebook.
     * @param paginationArgs a limit and a offset of a pagination.
     * @return a list of entities.
     */
    fun getChildren(notebookId: String, paginationArgs: PaginationArgs): List<Notebook>

    /**
     * A method which retrieves an amount of entities from a start position which are root parents (has no parent).
     * @param profileId an id of profile.
     * @param paginationArgs a limit and a offset of a pagination.
     * @return a list of entities.
     */
    fun getRootParents(profileId: String, paginationArgs: PaginationArgs): List<Notebook>
}