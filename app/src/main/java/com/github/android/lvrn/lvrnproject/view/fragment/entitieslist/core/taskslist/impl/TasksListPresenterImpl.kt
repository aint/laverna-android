package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.taskslist.impl

import com.github.android.lvrn.lvrnproject.service.core.NoteService
import com.github.android.lvrn.lvrnproject.service.core.TaskService
import com.github.android.lvrn.lvrnproject.service.form.TaskForm
import com.github.android.lvrn.lvrnproject.util.CurrentState.Companion.profileId
import com.github.android.lvrn.lvrnproject.util.PaginationArgs
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.taskslist.TasksListPresenter
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.impl.EntitiesListWithSearchPresenterImpl
import com.github.valhallalabs.laverna.persistent.entity.Note
import com.github.valhallalabs.laverna.persistent.entity.Task
import com.orhanobut.logger.Logger
import javax.inject.Inject

/**
 * @author Vadim Boitsov <vadimboitsov1></vadimboitsov1>@gmail.com>
 */
class TasksListPresenterImpl @Inject constructor(
    private val mTaskService: TaskService,
    private val mNoteService: NoteService
) : EntitiesListWithSearchPresenterImpl<Task, TaskForm>(
    mTaskService
), TasksListPresenter {
    override fun loadMoreForPagination(paginationArgs: PaginationArgs): List<Task> {
        return mTaskService.getUncompletedByProfile(profileId!!, paginationArgs)
    }

    override fun loadMoreForSearch(query: String, paginationArgs: PaginationArgs): List<Task> {
        return mTaskService.getUncompletedByProfileAndDescription(
            profileId!!,
            query,
            paginationArgs
        )
    }

    override fun getNoteByTask(task: Task?): Note {
        mNoteService.openConnection()
        val noteOptional = mNoteService.getById(
            task!!.noteId
        )
        mNoteService.closeConnection()
        if (noteOptional.isPresent) {
            return noteOptional.get()
        }
        Logger.wtf("Task exists without note")
        throw RuntimeException()
    }
}
