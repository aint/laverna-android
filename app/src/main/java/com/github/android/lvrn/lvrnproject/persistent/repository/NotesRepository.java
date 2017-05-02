package com.github.android.lvrn.lvrnproject.persistent.repository;

import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Notebook;
import com.github.android.lvrn.lvrnproject.persistent.entity.impl.Tag;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.ProfileDependedRepository;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface NotesRepository extends ProfileDependedRepository<Note> {

    void addTagsToNote(Note note, List<Tag> tags);

    void removeTagsFromNote(Note note, List<Tag> tags);

    List<Note> getByNotebook(Notebook notebook, int from, int amount);

    List<Note> getByTag(Tag tag, int from, int amount);
}