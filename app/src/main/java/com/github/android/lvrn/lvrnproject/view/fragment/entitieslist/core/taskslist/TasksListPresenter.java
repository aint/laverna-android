package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.taskslist;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.Task;
import com.github.android.lvrn.lvrnproject.service.form.TaskForm;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListWithSearchPresenter;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface TasksListPresenter extends EntitiesListWithSearchPresenter<Task, TaskForm> {

    Note getNoteByTask(Task task);

}
