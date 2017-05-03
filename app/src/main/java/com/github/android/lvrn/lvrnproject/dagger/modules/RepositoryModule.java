package com.github.android.lvrn.lvrnproject.dagger.modules;

import com.github.android.lvrn.lvrnproject.persistent.repository.extension.NoteRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.NotebookRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.ProfileRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.TagRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.TaskRepository;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.impl.NotebookRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.impl.NoteRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.impl.ProfileRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.impl.TagRepositoryImpl;
import com.github.android.lvrn.lvrnproject.persistent.repository.extension.impl.TaskRepositoryImpl;

import dagger.Module;
import dagger.Provides;

/**
 * @author Vadim Boitsov <vadimboitsov1@gmail.com>
 */

@Module
public class RepositoryModule {

    @Provides
    static NotebookRepository provideNotebooksRepository() {
        return new NotebookRepositoryImpl();
    }

    @Provides
    static NoteRepository provideNotesRepository() {
        return new NoteRepositoryImpl();
    }

    @Provides
    static ProfileRepository provideProfilesRepository() {
        return new ProfileRepositoryImpl();
    }

    @Provides
    static TagRepository provideTagsRepository() {
        return new TagRepositoryImpl();
    }

    @Provides
    static TaskRepository provideTasksRepository() {
        return new TaskRepositoryImpl();
    }
}
