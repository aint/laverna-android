package com.github.android.lvrn.lvrnproject.service.extension;

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

    List<Tag> getByName(String name, int from, int amount);

    List<Tag> getByNote(Note note, int from, int amount);
}
