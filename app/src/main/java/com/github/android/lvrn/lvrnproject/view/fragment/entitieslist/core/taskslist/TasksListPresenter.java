package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.taskslist;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.Task;
import com.github.android.lvrn.lvrnproject.service.form.TaskForm;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListPresenter;
import com.github.android.lvrn.lvrnproject.view.viewholder.TaskViewHolder;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface TasksListPresenter extends EntitiesListPresenter<Task, TaskForm, TaskViewHolder> {

    Note getNoteByTask(Task task);

}
