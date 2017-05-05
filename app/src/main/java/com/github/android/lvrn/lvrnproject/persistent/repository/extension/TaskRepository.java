package com.github.android.lvrn.lvrnproject.persistent.repository.extension;

import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.entity.Task;
import com.github.android.lvrn.lvrnproject.persistent.repository.ProfileDependedRepository;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface TaskRepository extends ProfileDependedRepository<Task> {

    @NonNull
    List<Task> getUncompletedByProfile(String profileId, int from, int amount);

    @NonNull
    List<Task> getByNote(String noteId);

}
