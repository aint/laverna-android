package com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.core.taskslist;

import com.github.android.lvrn.lvrnproject.persistent.entity.Task;
import com.github.android.lvrn.lvrnproject.view.fragment.newentitieslist.EntitiesListFragment;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface TasksListFragment extends EntitiesListFragment {

    void openRelatedNote(Task task);

}
