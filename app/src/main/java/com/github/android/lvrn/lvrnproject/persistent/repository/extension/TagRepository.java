package com.github.android.lvrn.lvrnproject.persistent.repository.extension;

import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.Tag;
import com.github.android.lvrn.lvrnproject.persistent.repository.ProfileDependedRepository;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface TagRepository extends ProfileDependedRepository<Tag> {

    @NonNull
    List<Tag> getByName(String name, int from, int amount);

//    @NonNull
//    List<Tag> getByNote(String noteId, int from, int amount);

    @NonNull
    List<Tag> getByNote(String noteId);
}
