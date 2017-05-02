package com.github.android.lvrn.lvrnproject.service;

import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Tag;
import com.github.android.lvrn.lvrnproject.service.core.ProfileDependedService;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface TagsService extends ProfileDependedService<Tag> {

    void create(String profileId, String name) throws IllegalArgumentException;

    List<Tag> getByNote(Note note, int from, int amount);
}
