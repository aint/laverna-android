package com.github.android.lvrn.lvrnproject.service.extension;

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

    List<Note> getByTitle(String title, int from, int amount);

    List<Note> getByNotebook(Notebook notebook, int from, int amount);

    List<Note> getByTag(Tag tag, int from, int amount);
}
