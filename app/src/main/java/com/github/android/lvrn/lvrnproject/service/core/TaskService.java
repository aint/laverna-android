package com.github.android.lvrn.lvrnproject.service.core;

import android.support.annotation.NonNull;
import android.support.annotation.Size;

import com.github.android.lvrn.lvrnproject.persistent.entity.Task;
import com.github.android.lvrn.lvrnproject.service.ProfileDependedService;
import com.github.android.lvrn.lvrnproject.service.form.TaskForm;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface TaskService extends ProfileDependedService<Task, TaskForm> {

    /**
     * A method which retrieves an amount of uncompleted tasks from a start position by a profile
     * id.
     * @param profileId an id of a profile.
     * @param offset a start position.
     * @param limit a number of entities to retrieve.
     * @return a list of entities.
     */
    @NonNull
    List<Task> getUncompletedByProfile(@NonNull String profileId, @NonNull PaginationArgs paginationArgs);

    /**
     * A method which retrieves entities by a note id.
     * @param noteId an id of note.
     * @return a list of entites.
     */
    @NonNull
    List<Task> getByNote(@NonNull String noteId);
}
