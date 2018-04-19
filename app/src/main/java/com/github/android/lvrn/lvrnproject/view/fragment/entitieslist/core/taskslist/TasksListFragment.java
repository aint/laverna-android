package com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.core.taskslist;

import com.github.valhallalabs.laverna.persistent.entity.Task;
import com.github.android.lvrn.lvrnproject.view.fragment.entitieslist.EntitiesListWithSearchFragment;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface TasksListFragment extends EntitiesListWithSearchFragment {

    void openRelatedNote(Task task);

}
