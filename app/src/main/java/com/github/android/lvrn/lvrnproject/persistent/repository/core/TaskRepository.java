package com.github.android.lvrn.lvrnproject.persistent.repository.core;

import androidx.annotation.NonNull;

import com.github.valhallalabs.laverna.persistent.entity.Task;
import com.github.android.lvrn.lvrnproject.persistent.repository.ProfileDependedRepository;
import com.github.android.lvrn.lvrnproject.util.PaginationArgs;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface TaskRepository extends ProfileDependedRepository<Task> {

    /**
     * A method which retrieves an amount of uncompleted tasks from a start position by a profile
     * id.
     * @param profileId an id of a profile.
     * @param paginationArgs a limit and a offset of a pagination.
     * @return a list of entities.
     */
    @NonNull
    List<Task> getUncompletedByProfile(@NonNull String profileId, @NonNull PaginationArgs paginationArgs);

    /**
     * A method which retrieves an amount of uncompleted tasks from a start position by a profile
     * id and a description.
     * @param profileId an id of a profile.
     * @param description a text description of the task.
     * @param paginationArgs a limit and a offset of a pagination.
     * @return a list of entities.
     */
    @NonNull
    List<Task> getUncompletedByProfileAndDescription(@NonNull String profileId, @NonNull String description, @NonNull PaginationArgs paginationArgs);

    /**
     * A method which retrieves entities by a note id.
     * @param noteId an id of note.
     * @return a list of entities.
     */
    @NonNull
    List<Task> getByNote(String noteId);
}
