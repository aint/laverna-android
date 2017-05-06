package com.github.android.lvrn.lvrnproject.persistent.repository.extension;

import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.persistent.repository.ProfileDependedRepository;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface NoteRepository extends ProfileDependedRepository<Note> {

    void addTagToNote(@NonNull String noteId, String tagId);

    void removeTagFromNote(@NonNull String noteId, String tagId);

    @NonNull
    List<Note> getByTitle(@NonNull String title, int from, int amount);

    @NonNull
    List<Note> getByNotebook(@NonNull String notebookId, int from, int amount);

    @NonNull
    List<Note> getByTag(@NonNull String tagId, int from, int amount);
}