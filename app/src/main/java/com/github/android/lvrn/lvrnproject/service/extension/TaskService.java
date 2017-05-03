package com.github.android.lvrn.lvrnproject.service.extension;

import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.entity.Task;
import com.github.android.lvrn.lvrnproject.service.ProfileDependedService;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface TaskService extends ProfileDependedService<Task> {

    /**
     * @param profileId
     * @param noteId
     * @param description
     * @param isCompleted
     * @throws IllegalArgumentException
     */
    void create(String profileId, String noteId, String description, boolean isCompleted);

    List<Task> getUncompletedByProfile(Profile profile, int from, int amount);
}
