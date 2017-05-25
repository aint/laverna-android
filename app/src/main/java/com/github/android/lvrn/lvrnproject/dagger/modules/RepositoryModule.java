package com.github.android.lvrn.lvrnproject.dagger.modules;

import android.support.annotation.NonNull;

import com.github.android.lvrn.lvrnproject.persistent.repository.core.NoteRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.NotebookRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.ProfileRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.TagRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.TaskRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.impl.NotebookRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.impl.NoteRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.impl.ProfileRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.impl.TagRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.core.impl.TaskRepositoryImpl;

import dagger.Module;
import dagger.Provides;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@Module
public class RepositoryModule {

    @NonNull
    @Provides
    static NotebookRepository provideNotebooksRepository() {
        return new NotebookRepositoryImpl();
    }

    @NonNull
    @Provides
    static NoteRepository provideNotesRepository() {
        return new NoteRepositoryImpl();
    }

    @NonNull
    @Provides
    static ProfileRepository provideProfilesRepository() {
        return new ProfileRepositoryImpl();
    }

    @NonNull
    @Provides
    static TagRepository provideTagsRepository() {
        return new TagRepositoryImpl();
    }

    @NonNull
    @Provides
    static TaskRepository provideTasksRepository() {
        return new TaskRepositoryImpl();
    }
}
