package com.github.android.lvrn.lvrnproject.persistent.repository.extension;

import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.entity.Task;
import com.github.android.lvrn.lvrnproject.persistent.repository.ProfileDependedRepository;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface TaskRepository extends ProfileDependedRepository<Task> {

    List<Task> getUncompletedByProfile(Profile profile, int from, int amount);
}
