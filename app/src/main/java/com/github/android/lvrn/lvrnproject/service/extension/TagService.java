package com.github.android.lvrn.lvrnproject.service.extension;

import android.support.annotation.NonNull;
import android.support.annotation.Size;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.Tag;
import com.github.android.lvrn.lvrnproject.service.ProfileDependedService;
import com.github.android.lvrn.lvrnproject.service.form.TagForm;
import com.github.android.lvrn.lvrnproject.service.form.TaskForm;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface TagService extends ProfileDependedService<Tag, TagForm> {

    @NonNull
    List<Tag> getByName(@NonNull String name, @Size(min = 1) int from, @Size(min = 2) int amount);

    @NonNull
    List<Tag> getByNote(@NonNull String noteId);
}
