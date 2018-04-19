package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.taskslist;

import com.github.valhallalabs.laverna.persistent.entity.Task;
import com.github.android.lvrn.lvrnproject.service.form.TaskForm;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListWithSearchPresenter;
import com.github.valhallalabs.laverna.persistent.entity.Note;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface TasksListPresenter extends EntitiesListWithSearchPresenter<Task, TaskForm> {

    Note getNoteByTask(Task task);

}
