package com.github.android.lvrn.lvrnproject.service.extension;

import android.support.annotation.NonNull;
import android.support.annotation.Size;

import com.github.android.lvrn.lvrnproject.persistent.entity.Profile;
import com.github.android.lvrn.lvrnproject.persistent.entity.Tag;
import com.github.android.lvrn.lvrnproject.persistent.entity.Task;
import com.github.android.lvrn.lvrnproject.service.ProfileDependedService;
import com.github.android.lvrn.lvrnproject.service.form.TaskForm;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface TaskService extends ProfileDependedService<Task, TaskForm> {

    @NonNull
    List<Task> getUncompletedByProfile(@NonNull String profileId, @Size(min = 1) int from, @Size(min = 2) int amount);

    @NonNull
    List<Task> getByNote(@NonNull String noteId);
}
