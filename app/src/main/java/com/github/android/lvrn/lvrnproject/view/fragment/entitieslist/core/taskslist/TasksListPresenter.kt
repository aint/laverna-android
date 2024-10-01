package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.taskslist

import com.github.android.lvrn.lvrnproject.service.form.TaskForm
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListWithSearchPresenter
import com.github.valhallalabs.laverna.persistent.entity.Note
import com.github.valhallalabs.laverna.persistent.entity.Task

/**
 * @author Vadim Boitsov <vadimboitsov1></vadimboitsov1>@gmail.com>
 */
interface TasksListPresenter : EntitiesListWithSearchPresenter<Task, TaskForm> {
    fun getNoteByTask(task: Task?): Note?
}
