package com.github.android.lvrn.lvrnproject.service.extension;

import android.support.annotation.NonNull;
import android.support.annotation.Size;

import com.github.android.lvrn.lvrnproject.persistent.entity.Note;
import com.github.android.lvrn.lvrnproject.persistent.entity.Notebook;
import com.github.android.lvrn.lvrnproject.persistent.entity.Tag;
import com.github.android.lvrn.lvrnproject.service.ProfileDependedService;
import com.github.android.lvrn.lvrnproject.service.form.NoteForm;

import java.util.List;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

public interface NoteService extends ProfileDependedService<Note, NoteForm> {

    @NonNull
    List<Note> getByTitle(@NonNull String title, @Size(min = 1) int from, @Size (min = 2) int amount);

    @NonNull
    List<Note> getByNotebook(@NonNull String notebookId, @Size(min = 1) int from, @Size (min = 2) int amount);

    @NonNull
    List<Note> getByTag(@NonNull String tagId, @Size(min = 1) int from, @Size(min = 2) int amount);
}
