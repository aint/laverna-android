package com.github.android.lvrn.lvrnproject.persistent.repository.extension;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.Tag;
import com.github.android.lvrn.lvrnproject.persistent.repository.ProfileDependedRepository;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface TagRepository extends ProfileDependedRepository<Tag> {

    List<Tag> getByName(String name, int from, int amount);

    List<Tag> getByNote(Note note, int from, int amount);
}
