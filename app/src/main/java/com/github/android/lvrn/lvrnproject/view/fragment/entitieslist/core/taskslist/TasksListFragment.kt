package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.taskslist

import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListWithSearchFragment
import com.github.valhallalabs.laverna.persistent.entity.Task

/**
 * @author Vadim Boitsov <vadimboitsov1></vadimboitsov1>@gmail.com>
 */
interface TasksListFragment : EntitiesListWithSearchFragment {
    fun openRelatedNote(task: Task?)
}
