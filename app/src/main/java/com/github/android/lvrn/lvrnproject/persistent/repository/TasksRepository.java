package com.github.android.lvrn.lvrnproject.persistent.repository;

import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Profile;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Task;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.ProfileDependedRepository;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface TasksRepository extends ProfileDependedRepository<Task> {

    List<Task> getUncompletedByProfile(Profile profile, int from, int amount);
}
