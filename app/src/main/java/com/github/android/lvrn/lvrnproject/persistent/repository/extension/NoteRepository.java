package com.github.android.lvrn.lvrnproject.persistent.repository.extension;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.persistent.entity.Tag;
import com.github.android.lvrn.lvrnproject.persistent.repository.ProfileDependedRepository;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface NoteRepository extends ProfileDependedRepository<Note> {

    void addTagsToNote(Note note, List<Tag> tags);

    void removeTagsFromNote(Note note, List<Tag> tags);

    List<Note> getByTitle(String title, int from, int amount);

    List<Note> getByNotebook(Notebook notebook, int from, int amount);

    List<Note> getByTag(Tag tag, int from, int amount);
}