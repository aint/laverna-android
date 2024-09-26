package com.github.android.lvrn.lvrnproject.service.core

import com.github.android.lvrn.lvrnproject.service.ProfileDependedService
import com.github.android.lvrn.lvrnproject.service.form.TaskForm
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.valhallalabs.laverna.persistent.entity.Task

interface TaskService : ProfileDependedService<Task, TaskForm> {

    /**
     * A method which retrieves an amount of uncompleted tasks from a start position by a profile
     * id.
     * @param profileId an id of a profile.
     * @param paginationArgs arguments of pagination such as offset and limit.
     * @return a list of entities.
     */
    fun getUncompletedByProfile(profileId: String, paginationArgs: PaginationArgs): List<Task>


    /**
     * A method which retrieves an amount of uncompleted tasks from a start position by a profile
     * id and a description
     * @param profileId an id of a profile.
     * @param description a description of the task.
     * @param paginationArgs arguments of pagination such as offset and limit.
     * @return a list of entities.
     */
    fun getUncompletedByProfileAndDescription(
        profileId: String,
        description: String,
        paginationArgs: PaginationArgs,
    ): List<Task>

    /**
     * A method which retrieves entities by a note id.
     * @param noteId an id of note.
     * @return a list of entities.
     */
    fun getByNote(noteId: String): List<Task>
}